package mahiro76.mahiro.world.features;

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
    }

    public static RegistryKey<PlacedFeature> of(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("industrial-revolution-mahiro76", id));//Identifier 的第一个参数必须是你的 mod id（比如 mahiro76），这样生成的 json 文件才会放在 data/mahiro76/worldgen/... 目录下。
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
