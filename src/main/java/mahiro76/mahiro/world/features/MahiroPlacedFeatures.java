package mahiro76.mahiro.world.features;

import mahiro76.mahiro.Mahiro;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class MahiroPlacedFeatures {

    public static final RegistryKey<PlacedFeature> ORANGE_BUSH_BLOCK_PLACED_KEY = of("orange_bush_block_placed");
    public static final RegistryKey<PlacedFeature> LEAD_ORE_PLACED_KEY = of("lead_ore_placed");
    public static final RegistryKey<PlacedFeature> LIMESTONE_PLACED_KEY = of("limestone_placed");

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable){
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        register(featureRegisterable, ORANGE_BUSH_BLOCK_PLACED_KEY, registryEntryLookup.getOrThrow(MahiroConfiguredFeatures.ORANGE_BUSH_BLOCK_KEY),
                RarityFilterPlacementModifier.of(2), SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

        //铅矿放置——仿照铁矿石的高度分布（Y=-64 ~ Y=72）
        register(featureRegisterable, LEAD_ORE_PLACED_KEY, registryEntryLookup.getOrThrow(MahiroConfiguredFeatures.LEAD_ORE_KEY),
                CountPlacementModifier.of(20),  //每个区块尝试 20 次
                SquarePlacementModifier.of(),   //水平扩散
                HeightRangePlacementModifier.trapezoid(
                        YOffset.aboveBottom(0),    //Y=-64（底部以上 0 格）
                        YOffset.fixed(72)          //最高 Y=72
                ),
                BiomePlacementModifier.of());

        //石灰石放置——概率低于铅矿，分布在较高 Y 层（沉积岩特征）
        register(featureRegisterable, LIMESTONE_PLACED_KEY, registryEntryLookup.getOrThrow(MahiroConfiguredFeatures.LIMESTONE_KEY),
                CountPlacementModifier.of(10),  //每个区块尝试 10 次（铅矿为 20）
                SquarePlacementModifier.of(),   //水平扩散
                HeightRangePlacementModifier.trapezoid(
                        YOffset.fixed(20),         //最低 Y=20
                        YOffset.fixed(80)          //最高 Y=80
                ),
                BiomePlacementModifier.of());
    }

    public static RegistryKey<PlacedFeature> of(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Mahiro.MOD_ID, id));
    }

    public static void register(
            Registerable<PlacedFeature> featureRegisterable,
            RegistryKey<PlacedFeature> key,
            RegistryEntry<ConfiguredFeature<?, ?>> feature,
            List<PlacementModifier> modifiers
    ) {
        featureRegisterable.register(key, new PlacedFeature(feature, List.copyOf(modifiers)));
    }

    public static void register(
            Registerable<PlacedFeature> featureRegisterable,
            RegistryKey<PlacedFeature> key,
            RegistryEntry<ConfiguredFeature<?, ?>> feature,
            PlacementModifier... modifiers
    ) {
        register(featureRegisterable, key, feature, List.of(modifiers));
    }

}
