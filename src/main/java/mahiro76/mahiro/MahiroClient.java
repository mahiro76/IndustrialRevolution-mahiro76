package mahiro76.mahiro;

import mahiro76.mahiro.registry.MahiroBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class MahiroClient implements ClientModInitializer {

    @Override
    public void onInitializeClient(){
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), MahiroBlocks.OrangeBlock);
    }
    public static void registerMahiroClient() {
        Mahiro.LOGGER.debug("Registering mod client for" + Mahiro.MOD_ID);
    }
}
