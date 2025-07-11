package mahiro76.mahiro.registry;

import mahiro76.mahiro.Mahiro;
import mahiro76.mahiro.registry.entity.RottenOrangeEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MahiroEntityType {
    private static final int maxEntityTrackingRange = 4;

    public static final EntityType<RottenOrangeEntity> RottenOrange = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier("mahiro", "rotten_orange"),
            FabricEntityTypeBuilder.<RottenOrangeEntity>create(SpawnGroup.MISC, RottenOrangeEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f,0.25f))
                    .trackRangeBlocks(maxEntityTrackingRange)
                    .trackedUpdateRate(10)
                    .build());

    public static void registerMahiroEntity() {
        Mahiro.LOGGER.debug("Registering mod BLock for" + Mahiro.MOD_ID);
    }
}
