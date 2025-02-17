package mahiro76.mahiro.registry.item;

import mahiro76.mahiro.registry.MahiroBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OrangeSeed extends Item {
    public OrangeSeed(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (state.isOf(Blocks.GRASS_BLOCK)) {
            world.setBlockState(pos.up(), MahiroBlocks.OrangeBlock.getDefaultState());
            context.getStack().decrement(1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }
}
