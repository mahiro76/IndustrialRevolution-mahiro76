package mahiro76.mahiro.world.field;

import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 供电场工具类。
 * <p>
 * 基于欧几里得距离的球形场，从源点向所有方向（上/下/左/右/前/后）均匀衰减，
 * 不受方块阻隔（可穿墙），与方块表面和朝向无关。
 * <p>
 * 场强公式：{@code strength = max(0, baseStrength - floor(欧几里得距离))}
 * <p>
 * 例如 {@code baseStrength = 15} 时：
 * <ul>
 *   <li>距离 0 → 强度 15（源点自身）</li>
 *   <li>距离 1 → 强度 14</li>
 *   <li>距离 7 → 强度 8</li>
 *   <li>距离 ≥ 15 → 强度 0（自然衰减完毕）</li>
 * </ul>
 *
 * <h2>使用示例（在 BlockEntity 的 tick 中调用）</h2>
 * <pre>{@code
 * private final Map<BlockPos, Integer> previousState = new HashMap<>();
 * private int tickCounter = 0;
 *
 * public static void tick(World world, BlockPos pos, BlockState state, MyBlockEntity be) {
 *     if (world.isClient) return;
 *     be.tickCounter++;
 *     if (be.tickCounter % 10 != 0) return;
 *
 *     PowerField.scanAndDiff(
 *         pos, 15,
 *         targetPos -> world.getBlockState(targetPos).getBlock() instanceof MyMachineBlock,
 *         be.previousState,
 *         (targetPos, strength) -> {
 *             BlockState s = world.getBlockState(targetPos);
 *             world.setBlockState(targetPos, s.with(MyBlock.ENERGY, Math.min(strength, 8)));
 *         },
 *         targetPos -> {
 *             BlockState s = world.getBlockState(targetPos);
 *             world.setBlockState(targetPos, s.with(MyBlock.ENERGY, 0).with(MyBlock.POWERED, false));
 *         }
 *     );
 * }
 * }</pre>
 */
public final class PowerField {

    private PowerField() {
    }

    /**
     * 扫描球形范围并返回每个受电方块的位置到场强的映射。
     *
     * @param center        场源位置
     * @param baseStrength  场源基础强度（如同光源亮度），决定最大覆盖距离
     * @param isTarget      判断某位置的方块是否为受电方块
     * @return 位置 → 场强 的映射（强度 1 ~ baseStrength，衰减为 0 的不包含）
     */
    public static Map<BlockPos, Integer> scan(BlockPos center, int baseStrength, Predicate<BlockPos> isTarget) {
        Map<BlockPos, Integer> result = new HashMap<>();
        int rangeSquared = baseStrength * baseStrength;

        for (int x = -baseStrength; x <= baseStrength; x++) {
            for (int y = -baseStrength; y <= baseStrength; y++) {
                int xySquared = x * x + y * y;
                if (xySquared > rangeSquared) continue;

                for (int z = -baseStrength; z <= baseStrength; z++) {
                    int distSquared = xySquared + z * z;
                    if (distSquared > rangeSquared || distSquared == 0) continue;

                    int strength = baseStrength - (int) Math.floor(Math.sqrt(distSquared));
                    if (strength <= 0) continue;

                    BlockPos targetPos = center.add(x, y, z);
                    if (isTarget.test(targetPos)) {
                        result.put(targetPos, strength);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 扫描并对比上一次结果，仅对发生变化的方块执行回调。
     * <p>
     * 相比 {@link #scan(BlockPos, int, Predicate)}，此方法会追踪前后两次扫描的差异，
     * 减少不必要的方块状态写入。
     *
     * @param center           场源位置
     * @param baseStrength     场源基础强度（如同光源亮度）
     * @param isTarget         判断某位置的方块是否为受电方块
     * @param previousState    上一次扫描的结果映射（会被更新为本次结果）
     * @param onStrengthChange 场强发生变化时回调（位置、新场强）
     * @param onExit           方块移出场范围时回调（位置）
     */
    public static void scanAndDiff(
            BlockPos center,
            int baseStrength,
            Predicate<BlockPos> isTarget,
            Map<BlockPos, Integer> previousState,
            BiConsumer<BlockPos, Integer> onStrengthChange,
            Consumer<BlockPos> onExit
    ) {
        Map<BlockPos, Integer> current = scan(center, baseStrength, isTarget);

        // 更新场强发生变化的方块
        for (Map.Entry<BlockPos, Integer> entry : current.entrySet()) {
            BlockPos targetPos = entry.getKey();
            int newStrength = entry.getValue();
            Integer oldStrength = previousState.get(targetPos);
            if (oldStrength == null || oldStrength != newStrength) {
                onStrengthChange.accept(targetPos, newStrength);
            }
        }

        // 清除已离开范围的方块
        for (Map.Entry<BlockPos, Integer> entry : previousState.entrySet()) {
            BlockPos targetPos = entry.getKey();
            if (!current.containsKey(targetPos)) {
                onExit.accept(targetPos);
            }
        }

        previousState.clear();
        previousState.putAll(current);
    }
}
