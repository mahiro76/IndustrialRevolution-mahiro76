package mahiro76.mahiro.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
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
 *     public MyMachineBlock(Settings settings) {
 *         super(settings);
 *         // 第 1 步：在构造函数中设置默认状态
 *         setDefaultState(getStateManager().getDefaultState()
 *             .with(MahiroBlockProperties.POWERED, false)
 *             .with(MahiroBlockProperties.ENERGY, 0));
 *     }
 *
 *     // 第 2 步：在 appendProperties 中注册属性
 *     @Override
 *     protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
 *         builder.add(MahiroBlockProperties.POWERED, MahiroBlockProperties.ENERGY);
 *     }
 *
 *     // 第 3 步：在逻辑中读写属性
 *     public void someTickMethod(BlockState state, World world, BlockPos pos) {
 *         boolean isPowered = state.get(MahiroBlockProperties.POWERED);
 *         int energy = state.get(MahiroBlockProperties.ENERGY);
 *
 *         if (energy < MahiroBlockProperties.MAX_ENERGY) {
 *             world.setBlockState(pos, state.with(MahiroBlockProperties.ENERGY, energy + 1));
 *         }
 *     }
 * }
 * }</pre>
 *
 * <h2>属性值范围说明</h2>
 * <ul>
 *   <li><b>{@link #POWERED}</b> — {@code true} 表示方块在供电范围内，{@code false} 表示不在。</li>
 *   <li><b>{@link #ENERGY}</b> — 取值范围 0（无电能）到 {@value #MAX_ENERGY}（满电能），共 16 级。
 *       方块状态是 {@link Minecraft#STATE_COUNT 有限枚举}，
 *       每个 unique 值组合会生成独立的 BlockState 实例。因此 ENERGY 不宜设太大范围。
 *       如需精细到个位数的电量数值，应存储在 BlockEntity 的 NBT 中，ENERGY 仅作为渲染和快速逻辑判断的等级。</li>
 * </ul>
 *
 * <h2>配合 BlockEntity 的典型模式</h2>
 * <pre>{@code
 * public class MyMachineBlockEntity extends BlockEntity {
 *     private int storedEnergy;     // 精细电量（0~10000），存 NBT
 *
 *     // 每 tick 将精细电量同步到方块状态（仅当等级改变时）
 *     public void syncEnergyToState() {
 *         int level = (int) Math.round(storedEnergy / 10000.0 * MahiroBlockProperties.MAX_ENERGY);
 *         BlockState state = world.getBlockState(pos);
 *         if (state.get(MahiroBlockProperties.ENERGY) != level) {
 *             world.setBlockState(pos, state.with(MahiroBlockProperties.ENERGY, level));
 *         }
 *     }
 * }
 * }</pre>
 *
 * <h2>资源文件（blockstates JSON）注意事项</h2>
 * <p>
 * 如果方块同时使用了 {@code POWERED}（2 种值）和 {@code ENERGY}（{@value #MAX_ENERGY} + 1 = 16 种值），
 * 则 blockstates JSON 需要覆盖 2 × 16 = 32 种组合。建议以下几种策略：
 * </p>
 * <ol>
 *   <li><b>完整枚举</b> — blockstate JSON 中列出所有 32 个 variants，各指向不同模型。
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
 * @see Block#appendProperties(StateManager.Builder)
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
     * 已存储电能的最大等级（含）。
     * <p>
     * 当前值为 15，即 ENERGY 的取值范围为 0 ~ 15，共 16 级。
     * 如需调整等级数量，只需修改此常量即可——ENERGY 属性会自动使用新值。
     */
    public static final int MAX_ENERGY = 15;

    /**
     * 已存储电能的等级（0 ~ {@value #MAX_ENERGY}）。
     * <p>
     * 表示方块内部存储的电能等级，值越大代表电量越足。
     * 属性名在 blockstate JSON 和调试屏幕中显示为 {@code "energy"}。
     * <p>
     * 读写示例：
     * <pre>{@code
     * // 读取当前电量等级
     * int level = state.get(MahiroBlockProperties.ENERGY);
     *
     * // 设置电量等级（生成新的 BlockState，不会改变原对象）
     * BlockState newState = state.with(MahiroBlockProperties.ENERGY, 10);
     * }</pre>
     *
     * @see #MAX_ENERGY
     */
    public static final IntProperty ENERGY = IntProperty.of("energy", 0, MAX_ENERGY);

    /**
     * 私有构造器，防止实例化。
     * <p>
     * 本类为纯静态工具类，所有属性和常量都应通过类名直接访问。
     */
    private MahiroBlockProperties() {
    }
}
