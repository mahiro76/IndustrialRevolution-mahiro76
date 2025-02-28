package mahiro76.mahiro.registry.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Orange extends Item{

    private static final double CHANCE_OF_GETTING_SEEDS = 0.2;
    private final Item returnItem;

    public Orange(Settings settings, Item returnItem) {
        super(settings.food(new FoodComponent.Builder()
                .hunger(5)
                .saturationModifier(1.0f)
                .alwaysEdible()
                .build()));
        this.returnItem = returnItem;
    }

    @Override
    public @NotNull ItemStack finishUsing(ItemStack stack, World world, LivingEntity entityLiving) {
        if (!world.isClient() && entityLiving instanceof PlayerEntity player) {
            if (stack.getItem() == this) {
                if (world.getRandom().nextFloat() < CHANCE_OF_GETTING_SEEDS) {
                    ItemStack returnStack = new ItemStack(returnItem);
                    if (!player.getInventory().insertStack(returnStack)) {
                        player.dropItem(returnStack, false);
                    }
                }
            }
        }
        return super.finishUsing(stack, world, entityLiving);
    }


    //覆写方法，添加物品提示文本
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.mahiro.orange.tips"));
    }
}
