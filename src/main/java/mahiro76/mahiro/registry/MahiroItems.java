package mahiro76.mahiro.registry;

import mahiro76.mahiro.Mahiro;
import mahiro76.mahiro.registry.item.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MahiroItems {

    //拐杖
    public static final Crutch CRUTCH = registerItem("crutch", new Crutch(new Item.Settings()));
    //固化剂
    public static final Hardener HARDENER = registerItem("hardener", new Hardener(new Item.Settings()));
    //巧克力
    public static final Chocolate CHOCOLATE = registerItem("chocolate", new Chocolate(new Item.Settings()));
    //橙子种子
    public static final Item ORANGE_SEEDS = registerItem("orange_seeds", new OrangeSeed(new Item.Settings()));
    //橙子
    public static final Item ORANGE = registerItem("orange",new Orange(new Item.Settings(),ORANGE_SEEDS));
    //坏橙子
    public static final Item ROTTEN_ORANGE = registerItem("rotten_orange", new RottenOrange(new Item.Settings()));
    //荧光粉
    public static final Item GLOWPOWDER = registerItem("glow_powder", new GlowPowder(new Item.Settings()));
    //粗铅
    public static final Item RAW_LEAD = registerItem("raw_lead", new RawLead(new Item.Settings()));
    //铅锭
    public static final Item LEAD_INGOT = registerItem("lead_ingot", new LeadIngot(new Item.Settings()));

    //添加粗铅
    public static final Item RAW_LEAD = registerItem("raw_lead", new Item(new Item.Settings()));
    //添加铅锭
    public static final Item LEAD_INGOT = registerItem("lead_ingot", new Item(new Item.Settings()));


    //添加物品辅助方法
    public static <T extends Item> T registerItem(String name, T item) {
        return Registry.register(Registries.ITEM, Identifier.of(Mahiro.MOD_ID, name), item);
    }

    //启动初始化方法
    public static void registerMahiroItems() {
        Mahiro.LOGGER.debug("Registering mod item for" + Mahiro.MOD_ID);
    }
}
