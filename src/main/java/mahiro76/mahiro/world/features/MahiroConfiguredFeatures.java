package mahiro76.mahiro.world.features;

import mahiro76.mahiro.Mahiro;
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
    public static final RegistryKey<ConfiguredFeature<?, ?>> LIMESTONE_KEY = of("limestone");


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

        //石灰石生成——仅替换纯石头（不替换花岗岩、深板岩等），概率低于铅矿
        RuleTest stoneOnlyReplaceables = new BlockMatchRuleTest(Blocks.STONE);
        register(featureRegisterable, LIMESTONE_KEY, Feature.ORE,
                new OreFeatureConfig(
                        java.util.List.of(
                                OreFeatureConfig.createTarget(stoneOnlyReplaceables, MahiroBlocks.LIMESTONE.getDefaultState())
                        ),
                        4  //矿脉大小（小于铅矿的9）
                ));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> of(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Mahiro.MOD_ID, id));
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> void register(
            Registerable<ConfiguredFeature<?, ?>> registerable, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC config
    ) {
        registerable.register(key, new ConfiguredFeature<FC, F>(feature, config));
    }


}
