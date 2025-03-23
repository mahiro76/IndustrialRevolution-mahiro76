package mahiro76.mahiro.registry;

import mahiro76.mahiro.Mahiro;
import mahiro76.mahiro.registry.block.OrangeBushBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class MahiroBlocks {
    //在下方添加物品
    public static Block Limestone = register("limestone", new Block(AbstractBlock.Settings.copy(net.minecraft.block.Blocks.STONE)));
    public static final Block OrangeBushBlock = register(
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
