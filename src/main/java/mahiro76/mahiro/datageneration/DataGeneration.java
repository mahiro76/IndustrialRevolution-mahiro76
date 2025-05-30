package mahiro76.mahiro.datageneration;

import mahiro76.mahiro.world.features.MahiroConfiguredFeatures;
import mahiro76.mahiro.world.features.MahiroPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class DataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        //进度生成器
        generator.createPack().addProvider(MahiroAdvancementsProvider::new);
        //配方生成器
        pack.addProvider(MahiroRecipeGenerator::new);
        //世界生成器
        pack.addProvider(MahiroWorldGenerator::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        //注册生成器
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, MahiroConfiguredFeatures :: bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, MahiroPlacedFeatures:: bootstrap);
    }

}
