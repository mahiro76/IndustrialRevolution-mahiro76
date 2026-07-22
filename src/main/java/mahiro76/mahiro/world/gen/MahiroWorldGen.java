package mahiro76.mahiro.world.gen;

import mahiro76.mahiro.world.features.MahiroPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;

public class MahiroWorldGen {

    public static void initialization(){
        MahiroFlowerGen.generateFlowers();
        MahiOreGen.generateOres();
    }

    /**
     * 铅矿生成——仿照铁矿石在所有主世界生物群系中生成
     */
    public static class MahiOreGen {
        public static void generateOres() {
            BiomeModifications.addFeature(
                    BiomeSelectors.foundInOverworld(),
                    GenerationStep.Feature.UNDERGROUND_ORES,
                    MahiroPlacedFeatures.LEAD_ORE_PLACED_KEY
            );
        }
    }
}
