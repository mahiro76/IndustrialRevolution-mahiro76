package mahiro76.mahiro.registry.block;

import net.minecraft.block.Block;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

/**
 * 模组自定义方块状态属性集合。
 * <p>
 * 本类集中定义了能量系统所需的方块状态属性，所有需要与电力交互的方块
 * 都应使用这里定义的属性，以确保整个模组的属性命名和语义保持一致。
 *
 * <h2>快速开始</h2>
 * <pre>{@code
 * public class MyMachineBlock extends Block {
 *
 *     // 第 0 步：在方块类中声明自己的 ENERGY 属性并设定最大等级
 *     private static final IntProperty ENERGY = MahiroBlockProperties.energyProperty(7);
 *
 *     public MyMachineBlock(Settings settings) {
 *         super(settings);
 *         // 第 1 步：在构造函数中设置默认状态
 *         setDefaultState(getStateManager().getDefaultState()
 *             .with(MahiroBlockProperties.POWERED, false)
 *             .with(ENERGY, 0));
 *     }
 *
 *     // 第 2 步：在 appendProperties 中注册属性
 *     @Override
 *     protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
 *         builder.add(MahiroBlockProperties.POWERED, ENERGY);
 *     }
 *
 *     // 第 3 步：在逻辑中读写属性
 *     public void someTickMethod(BlockState state, World world, BlockPos pos) {
 *         boolean isPowered = state.get(MahiroBlockProperties.POWERED);
 *         int energy = state.get(ENERGY);
 *
 *         int max = ((IntProperty) ENERGY).getValues().size() - 1;  // 或直接在类里存个常量
 *         if (energy < max) {
 *             world.setBlockState(pos, state.with(ENERGY, energy + 1));
 *         }
 *     }
 * }
 * }</pre>
 *
 * <h2>属性值范围说明</h2>
 * <ul>
 *   <li><b>{@link #POWERED}</b> — {@code true} 表示方块在供电范围内，{@code false} 表示不在。</li>
 *   <li><b>{@link #energyProperty(int)}</b> — 方块使用此方法创建自己的 ENERGY 属性，并设定最大等级。
 *       方块状态是有限枚举，等级数过多会指数膨胀 BlockState 实例。
 *       精细电量应存储在 BlockEntity 的 NBT 中，ENERGY 仅作为渲染和快速逻辑判断的等级。</li>
 * </ul>
 *
 * <h2>配合 BlockEntity 的典型模式</h2>
 * <pre>{@code
 * public class MyMachineBlockEntity extends BlockEntity {
 *     private int storedEnergy;        // 精细电量（0~10000），存 NBT
 *     private final int energyMaxLevel; // 从对应方块类获取最大等级
 *
 *     // 每 tick 将精细电量同步到方块状态（仅当等级改变时）
 *     public void syncEnergyToState(IntProperty energyProp) {
 *         int max = energyProp.getValues().size() - 1;
 *         int level = (int) Math.round(storedEnergy / 10000.0 * max);
 *         BlockState state = world.getBlockState(pos);
 *         if (state.get(energyProp) != level) {
 *             world.setBlockState(pos, state.with(energyProp, level));
 *         }
 *     }
 * }
 * }</pre>
 *
 * <h2>资源文件（blockstates JSON）注意事项</h2>
 * <p>
 * 如果方块同时使用了 {@code POWERED}（2 种值）和一个 {@code energy} 属性（N 种值），
 * 则 blockstates JSON 需要覆盖 2 × N 种组合。建议以下几种策略：
 * </p>
 * <ol>
 *   <li><b>完整枚举</b> — blockstate JSON 中列出所有组合的 variants，各指向不同模型。
 *       适合模型数不多的情况。</li>
 *   <li><b>用 {@code multipart} 或 BlockEntityModel</b> — 通过 BlockEntity 的
 *       {@code getModelData()} 返回自定义模型数据，配合 {@code BlockEntityModel} 实现
 *       动态纹理绑定，避免写大量 variants。</li>
 *   <li><b>先降低 ENERGY 范围</b> — 如果觉得 16 级太多，可以在最初只使用
 *       {@code IntProperty.of("energy", 0, 7)} 共 8 级，后续再扩展。</li>
 * </ol>
 *
 * <h2>在游戏内调试</h2>
 * <p>
 * 按 F3 打开调试屏幕，将准星对准方块，右侧面板会显示该方块的所有 BlockState 属性及其当前值。
 * 这可以帮助你验证 POWERED 和 ENERGY 的实时变化是否符合预期。
 * </p>
 *
 * @see BooleanProperty
 * @see IntProperty
 * @see Block#appendProperties
 */
public class MahiroBlockProperties {

    /**
     * 方块是否在供电范围内。
     * <p>
     * 当方块处于发电或输电结构的有效覆盖范围内时，此属性为 {@code true}；
     * 超出范围后为 {@code false}。
     * 可以通过此属性控制方块的渲染（如亮灯/熄灯）和行为（如激活/休眠）。
     * <p>
     * 读取示例：
     * <pre>{@code boolean powered = state.get(MahiroBlockProperties.POWERED);}</pre>
     */
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");

    /**
     * 创建一个名为 {@code "energy"}、范围 0 ~ {@code max}（含）的电能等级属性。
     * <p>
     * 每个方块可设定自己的最大能量等级，而不影响其他方块：
     * <pre>{@code
     * // 青铜储电箱：最大 7 级
     * private static final IntProperty ENERGY =
     *     MahiroBlockProperties.energyProperty(7);
     *
     * // 高级储电箱：最大 31 级
     * private static final IntProperty ENERGY =
     *     MahiroBlockProperties.energyProperty(31);
     * }</pre>
     * <p>
     * <b>注意：</b>方块状态是有限枚举，每增加一级都会指数膨胀 BlockState 实例数。
     * 精细电量应存储在 BlockEntity NBT 中，ENERGY 仅用作渲染和快速判断的等级刻度。
     *
     * @param max 最大等级值（含），必须 &ge; 0
     * @return 以 {@code "energy"} 命名的 IntProperty
     */
    public static IntProperty energyProperty(int max) {
        return IntProperty.of("energy", 0, max);
    }

    /**
     * 私有构造器，防止实例化。
     * <p>
     * 本类为纯静态工具类，所有属性和常量都应通过类名直接访问。
     */
    private MahiroBlockProperties() {
    }
}
