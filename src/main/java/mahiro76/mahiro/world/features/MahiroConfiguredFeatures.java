package mahiro76.mahiro.world.features;

import mahiro76.mahiro.registry.MahiroBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.registry.tag.BlockTags;

public class MahiroConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> ORANGE_BUSH_BLOCK_KEY = of("orange_bush_block");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LEAD_ORE_KEY = of("lead_ore");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable){
        register(featureRegisterable, ORANGE_BUSH_BLOCK_KEY, Feature.FLOWER, new RandomPatchFeatureConfig(32,3,4,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(MahiroBlocks.ORANGE_BUSH_BLOCK)))));

        //铅矿生成——仿照铁矿石的矿脉生成行为
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        register(featureRegisterable, LEAD_ORE_KEY, Feature.ORE,
                new OreFeatureConfig(
                        //同时替换石头和深板岩
                        java.util.List.of(
                                OreFeatureConfig.createTarget(stoneReplaceables, MahiroBlocks.LEAD_ORE.getDefaultState()),
                                OreFeatureConfig.createTarget(deepslateReplaceables, MahiroBlocks.DEEPSLATE_LEAD_ORE.getDefaultState())
                        ),
                        9  //矿脉大小（与铁矿石一致）
                ));
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
