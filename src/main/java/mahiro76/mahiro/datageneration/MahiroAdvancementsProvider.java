package mahiro76.mahiro.datageneration;

import mahiro76.mahiro.advancements.Advancements;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.registry.RegistryWrapper;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


public class MahiroAdvancementsProvider extends FabricAdvancementProvider {

    protected MahiroAdvancementsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
        new Advancements().accept(consumer);
    }
}
