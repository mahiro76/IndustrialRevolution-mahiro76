package mahiro76.mahiro.world.gen;

import mahiro76.mahiro.world.features.MahiroPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class MahiroFlowerGen {
    public static void generateFlowers() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST, BiomeKeys.FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, MahiroPlacedFeatures.ORANGE_BUSH_BLOCK_PLACED_KEY);
    }
}
