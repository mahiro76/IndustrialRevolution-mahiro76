package mahiro76.mahiro.registry.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class Chocolate extends Item {
    //食品属性
    public Chocolate(Settings settings) {
        super(settings.food(new FoodComponent.Builder()
                .hunger(10)
                .saturationModifier(2.0f)
                .meat()
                .snack()
                .alwaysEdible()
                .build()));
    }
    //覆写方法，添加物品提示文本
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.mahiro.chocolate.tips"));
    }

}
