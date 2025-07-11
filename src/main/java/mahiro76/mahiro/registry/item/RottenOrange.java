package mahiro76.mahiro.registry.item;

import mahiro76.mahiro.registry.entity.RottenOrangeEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class RottenOrange extends Item{

    public RottenOrange(Settings settings) {
        super(settings.food(new FoodComponent.Builder()
                .hunger(5)
                .saturationModifier(1.0f)
                .snack()
                .alwaysEdible()
                .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 3), 1.0f)
                .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 400, 1), 1.0f)
                .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 400, 2), 1.0f)
                .build()));
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand); // creates a new ItemStack instance of the user's itemStack in-hand
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 1F); // plays a globalSoundEvent
        user.getItemCooldownManager().set(this, 10);
        if (!world.isClient) {
            RottenOrangeEntity rottenorangeentity = new RottenOrangeEntity(world, user);
            rottenorangeentity.setItem(itemStack);
            rottenorangeentity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
            world.spawnEntity(rottenorangeentity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1); // decrements itemStack if user is not in creative mode
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

    //覆写方法，添加物品提示文本
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.mahiro.rotten_orange.tips"));
    }

}
