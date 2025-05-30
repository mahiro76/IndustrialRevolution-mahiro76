package mahiro76.mahiro.world.features;

import mahiro76.mahiro.registry.MahiroBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class MahiroConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> ORANGE_BUSH_BLOCK_KEY = of("orange_bush_block");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable){
        register(featureRegisterable, ORANGE_BUSH_BLOCK_KEY, Feature.FLOWER, new RandomPatchFeatureConfig(32,3,4,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(MahiroBlocks.OrangeBushBlock)))));

    }

    public static RegistryKey<ConfiguredFeature<?, ?>> of(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of("industrial-revolution-mahiro76", id));//Identifier 的第一个参数必须是你的 mod id（比如 mahiro76），这样生成的 json 文件才会放在 data/mahiro76/worldgen/... 目录下。
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> void register(
            Registerable<ConfiguredFeature<?, ?>> registerable, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC config
    ) {
        registerable.register(key, new ConfiguredFeature<FC, F>(feature, config));
    }


}
