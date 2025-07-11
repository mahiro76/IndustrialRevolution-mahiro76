package mahiro76.mahiro;

import mahiro76.mahiro.registry.MahiroBlocks;
import mahiro76.mahiro.registry.MahiroEntityType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

@Environment(EnvType.CLIENT)
public class MahiroClient implements ClientModInitializer {

    @Override
    public void onInitializeClient(){
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), MahiroBlocks.OrangeBushBlock);

        EntityRendererRegistry.register(MahiroEntityType.RottenOrange, (context) ->
                new FlyingItemEntityRenderer(context));
    }
}
