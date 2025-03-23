package mahiro76.mahiro.advancements;

import mahiro76.mahiro.registry.MahiroItems;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.PlayerInteractedWithEntityCriterion;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;


public class Advancements implements Consumer<Consumer<Advancement>> {

    @Override
    public void accept(Consumer<Advancement> consumer) {
        // 拐杖！
        Advancement got_crutchAdvancement = Advancement.Builder.create()
                .display(
                        MahiroItems.CRUTCH,
                        Text.translatable("advancement.mahiro.crutch"),
                        Text.translatable("advancement.mahiro.crutch.description"),
                        new Identifier("mahiro", "textures/gui/advancements/backgrounds/adventure.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_crutch", InventoryChangedCriterion.Conditions.items(MahiroItems.CRUTCH))
                .build(consumer, "mahiro/got_crutch");

        // 巧克力！
        Advancement got_chocolateAdvancement = Advancement.Builder.create()
                .display(
                        MahiroItems.CHOCOLATE,
                        Text.translatable("advancement.mahiro.chocolate"),
                        Text.translatable("advancement.mahiro.chocolate.description"),
                        new Identifier("mahiro", "textures/gui/advancements/backgrounds/adventure.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_chocolate", InventoryChangedCriterion.Conditions.items(MahiroItems.CHOCOLATE))
                .build(consumer, "mahiro/got_chocolate");

        // 有毒！（对狼使用巧克力）
        Advancement poisonous_wolfAdvancement = Advancement.Builder.create()
                .display(
                        MahiroItems.CHOCOLATE,
                        Text.translatable("advancement.mahiro.poisonous_wolf"),
                        Text.translatable("advancement.mahiro.poisonous_wolf.description"),
                        new Identifier("mahiro", "textures/gui/advancements/backgrounds/adventure.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        true
                )
                .criterion("poisonous_wolf", PlayerInteractedWithEntityCriterion.Conditions
                                .create(
                                        ItemPredicate.Builder.create().items(MahiroItems.CHOCOLATE),
                                        LootContextPredicate.create(
                                                EntityPropertiesLootCondition.builder(
                                                        LootContext.EntityTarget.THIS, EntityPredicate.Builder.create()
                                                                .type(EntityType.WOLF)
                                                                .build())
                                                        .build()
                                        )
                                )
                )
                .build(consumer, "mahiro/poisonous_wolf");


    }


}
 