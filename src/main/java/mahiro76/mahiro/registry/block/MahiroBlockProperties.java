package mahiro76.mahiro.registry.block;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

/**
 * Custom block state properties for the Mahiro mod.
 * <p>
 * These properties are used by energy-related blocks to track
 * their power status and stored energy level.
 */
public class MahiroBlockProperties {

    /**
     * Whether the block is within power supply range.
     */
    public static final BooleanProperty POWERED = BooleanProperty.of("powered");

    /**
     * Stored electrical energy level (0 ~ {@value #MAX_ENERGY}).
     * <p>
     * This is a block state property with a limited range.
     * For more granular energy tracking, use BlockEntity NBT data.
     */
    public static final int MAX_ENERGY = 15;
    public static final IntProperty ENERGY = IntProperty.of("energy", 0, MAX_ENERGY);

    private MahiroBlockProperties() {
        // Utility class — no instantiation
    }
}
