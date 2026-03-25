package mahiro76.mahiro.registry.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Stainable;
import net.minecraft.util.DyeColor;

public class StainedGlowingGlassBlock extends AbstractGlassBlock implements Stainable {
    private final DyeColor color;
    public StainedGlowingGlassBlock(DyeColor color, AbstractBlock.Settings settings) {
        super(settings);
        this.color = color;
    }

    @Override
    public DyeColor getColor() {
        return null;
    }
}
