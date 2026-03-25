package mahiro76.mahiro;

import mahiro76.mahiro.registry.MahiroBlocks;
import mahiro76.mahiro.registry.MahiroEntityType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

@Environment(EnvType.CLIENT)
public class MahiroClient implements ClientModInitializer {

    @Override
    public void onInitializeClient(){
        //注册方块渲染层
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), MahiroBlocks.ORANGE_BUSH_BLOCK);
        glowingGlassRender(MahiroBlocks.GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.WHITE_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.ORANGE_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.MAGENTA_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.LIGHT_BLUE_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.YELLOW_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.LIME_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.PINK_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.GRAY_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.LIGHT_GRAY_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.CYAN_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.PURPLE_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.BLUE_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.BROWN_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.GREEN_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.RED_STAINED_GLOWING_GLASS);
        glowingGlassRender(MahiroBlocks.BLACK_STAINED_GLOWING_GLASS);


        //注册实体渲染器
        EntityRendererRegistry.register(MahiroEntityType.RottenOrange, (context) ->
                new FlyingItemEntityRenderer(context));
    }
    //发光玻璃渲染辅助方法
    public static void glowingGlassRender(Block blocks){
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), blocks);
    }
}
