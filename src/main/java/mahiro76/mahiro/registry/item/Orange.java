package mahiro76.mahiro.registry.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class Orange extends Item{
    public Orange(Settings settings) {
        super(settings.food(new FoodComponent.Builder()
                .hunger(5)
                .saturationModifier(1.0f)
                .snack()
                .alwaysEdible()
                .build()));
    }

    //覆写方法，添加物品提示文本
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.mahiro.orange.tips"));
    }
}
