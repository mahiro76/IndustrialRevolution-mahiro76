package mahiro76.mahiro.registry;

import mahiro76.mahiro.Mahiro;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MahiroItemGroup {
    // 武器物品类
    public static ItemGroup FIGHT = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Mahiro.MOD_ID, "fight"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.mahiro.fight"))
                    .icon(() -> new ItemStack(MahiroItems.CRUTCH))
                    .entries((displayContext, entries) -> {
                        entries.add(MahiroItems.CRUTCH);

                    }).build());

    // 材料物品类
    public static ItemGroup MATERIAL = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Mahiro.MOD_ID, "material"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.mahiro.material"))
                    .icon(() -> new ItemStack(MahiroItems.HARDENER))
                    .entries((displayContext, entries) -> {
                        entries.add(MahiroItems.HARDENER);

                    }).build());

    // 自然方块物品类
    public static ItemGroup NATURAL_BLOCK = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Mahiro.MOD_ID, "natural_block"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.mahiro.natural_block"))
                    .icon(() -> new ItemStack(MahiroBlocks.LIMESTONE))
                    .entries((displayContext, entries) -> {
                        entries.add(MahiroBlocks.LIMESTONE);

                    }).build());

    // 工具物品类
    public static ItemGroup TOOL = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Mahiro.MOD_ID, "tool"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.mahiro.tool"))
                    .icon(() -> new ItemStack(MahiroItems.CRUTCH))//暂时使用拐杖作为图标，后续替换
                    .entries((displayContext, entries) -> {
                        //在此处添加物品

                    }).build());

    // 食物物品类
    public static ItemGroup FOOD = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Mahiro.MOD_ID, "food"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.mahiro.food"))
                    .icon(() -> new ItemStack(MahiroItems.CHOCOLATE))//使用巧克力作为图标
                    .entries((displayContext, entries) -> {
                        //在此处添加物品
                        entries.add(MahiroItems.CHOCOLATE);
                        entries.add(MahiroItems.ORANGE);
                        entries.add(MahiroItems.ROTTEN_ORANGE);

                    }).build());

    // 作物物品类
    public static ItemGroup CROP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Mahiro.MOD_ID, "crop"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.mahiro.crop"))
                    .icon(() -> new ItemStack(MahiroItems.ORANGE_SEEDS))//使用橘子作为图标
                    .entries((displayContext, entries) -> {
                        //在此处添加物品
                        entries.add(MahiroItems.ORANGE_SEEDS);

                    }).build());

    // 启动初始化方法
    public static void registerModItemGroup() {
        Mahiro.LOGGER.debug("registerModItemGroup " + Mahiro.MOD_ID);
    }
}