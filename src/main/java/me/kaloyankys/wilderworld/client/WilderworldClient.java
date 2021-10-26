package me.kaloyankys.wilderworld.client;

import me.kaloyankys.wilderworld.init.WWBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class WilderworldClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                WWBlocks.BIRD_OF_PARADISE, WWBlocks.EDELWEISS, WWBlocks.RAGING_VIOLET, WWBlocks.SHELFSHROOM);
    }
}
