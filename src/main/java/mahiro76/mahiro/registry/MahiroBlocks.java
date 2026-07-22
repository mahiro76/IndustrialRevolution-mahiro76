package mahiro76.mahiro.registry;

import mahiro76.mahiro.Mahiro;
import mahiro76.mahiro.registry.block.GlowingGlassBlock;
import mahiro76.mahiro.registry.block.OrangeBushBlock;
import mahiro76.mahiro.registry.block.StainedGlowingGlassBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class MahiroBlocks {
    //在下方添加物品
    public static Block LIMESTONE = register("limestone", new Block(AbstractBlock.Settings.copy(net.minecraft.block.Blocks.STONE)));
    public static final Block ORANGE_BUSH_BLOCK = register(
            "orange_block",
            new OrangeBushBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.DARK_GREEN)
                            .ticksRandomly()
                            .noCollision()
                            .sounds(BlockSoundGroup.SWEET_BERRY_BUSH)
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );

    //无色发光玻璃
    public static final Block GLOWING_GLASS = register(
            "glowing_glass",
            new GlowingGlassBlock(
                    AbstractBlock.Settings.create()
                            .instrument(Instrument.HAT)
                            .strength(0.3F)
                            .luminance(state -> 10)//指定发光亮度
                            .sounds(BlockSoundGroup.GLASS)
                            .nonOpaque()
                            .allowsSpawning(Blocks::never)
                            .solidBlock(Blocks::never)
                            .suffocates(Blocks::never)
                            .blockVision(Blocks::never)
            )
    );

    //染色发光玻璃注册
    public static final Block WHITE_STAINED_GLOWING_GLASS = register("white_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.WHITE));
    public static final Block ORANGE_STAINED_GLOWING_GLASS = register("orange_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.ORANGE));
    public static final Block MAGENTA_STAINED_GLOWING_GLASS = register("magenta_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.MAGENTA));
    public static final Block LIGHT_BLUE_STAINED_GLOWING_GLASS = register("light_blue_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.LIGHT_BLUE));
    public static final Block YELLOW_STAINED_GLOWING_GLASS = register("yellow_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.YELLOW));
    public static final Block LIME_STAINED_GLOWING_GLASS = register("lime_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.LIME));
    public static final Block PINK_STAINED_GLOWING_GLASS = register("pink_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.PINK));
    public static final Block GRAY_STAINED_GLOWING_GLASS = register("gray_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.GRAY));
    public static final Block LIGHT_GRAY_STAINED_GLOWING_GLASS = register("light_gray_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.LIGHT_GRAY));
    public static final Block CYAN_STAINED_GLOWING_GLASS = register("cyan_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.CYAN));
    public static final Block PURPLE_STAINED_GLOWING_GLASS = register("purple_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.PURPLE));
    public static final Block BLUE_STAINED_GLOWING_GLASS = register("blue_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.BLUE));
    public static final Block BROWN_STAINED_GLOWING_GLASS = register("brown_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.BROWN));
    public static final Block GREEN_STAINED_GLOWING_GLASS = register("green_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.GREEN));
    public static final Block RED_STAINED_GLOWING_GLASS = register("red_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.RED));
    public static final Block BLACK_STAINED_GLOWING_GLASS = register("black_stained_glowing_glass", createGlowingStainedGlassBlock(DyeColor.BLACK));

    //染色发光玻璃辅助注册方法
    public static StainedGlowingGlassBlock createGlowingStainedGlassBlock(DyeColor color) {
        return new StainedGlowingGlassBlock(
                color,
                AbstractBlock.Settings.create()
                        .mapColor(color)
                        .instrument(Instrument.HAT)
                        .strength(0.3F)
                        .luminance(state -> 10)//指定发光亮度
                        .sounds(BlockSoundGroup.GLASS)
                        .nonOpaque()
                        .allowsSpawning(Blocks::never)
                        .solidBlock(Blocks::never)
                        .suffocates(Blocks::never)
                        .blockVision(Blocks::never)
        );
    }

    /**
     *{@link #registerBlockItems(String, Block)}与{@link #register(String, Block)}方法的作用是注册方块和方块物品。
     */
    //方块物品注册方法（在注册方块的同时注册方块物品）
    public static void registerBlockItems(String id,Block block){
        Item item = Registry.register(Registries.ITEM, Identifier.of(Mahiro.MOD_ID, id), new BlockItem(block, new Item.Settings()));
        if (item instanceof BlockItem) {
            ((BlockItem)item).appendBlocks(Item.BLOCK_ITEMS, item);
        }
    }
    //方块注册方法
    private static <T extends Block> T register(String id, T block) {
        registerBlockItems(id, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Mahiro.MOD_ID, id), block);
    }




    //添加启动初始化方法
    public static void registerMahiroBlocks() {
        Mahiro.LOGGER.debug("Registering mod BLock for" + Mahiro.MOD_ID);
    }
}
