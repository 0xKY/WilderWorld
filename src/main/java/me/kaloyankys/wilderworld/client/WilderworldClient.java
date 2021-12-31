package me.kaloyankys.wilderworld.client;

import me.kaloyankys.wilderworld.client.particle.SteamParticle;
import me.kaloyankys.wilderworld.entity.render.ButterflyEntityRenderer;
import me.kaloyankys.wilderworld.entity.render.ButterflyModel;
import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.init.WWEntities;
import me.kaloyankys.wilderworld.init.WWParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WilderworldClient implements ClientModInitializer {

    public static final EntityModelLayer BUTTERFLY_RENDER_LAYER = new EntityModelLayer(
            new Identifier("wilderworld", "butterfly"), "butterfly_render_layer");

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                WWBlocks.BIRD_OF_PARADISE, WWBlocks.CHAMOMILE, WWBlocks.RAGING_VIOLET, WWBlocks.SHELFSHROOM, WWBlocks.PHOSPHOSHOOTS,
                WWBlocks.WISTERIA.POTTED_SAPLING, WWBlocks.WISTERIA.SAPLING, WWBlocks.MOSS_COVER, WWBlocks.ASPEN_SAPLING, WWBlocks.BUTTERFLY_SPAWN);

        ParticleFactoryRegistry.getInstance().register(WWParticles.STEAM, SteamParticle.SteamFactory::new);

        EntityRendererRegistry.register(WWEntities.BUTTERFLY, ButterflyEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BUTTERFLY_RENDER_LAYER, ButterflyModel::getTexturedModelData);

        ColorProviderRegistryImpl.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ?
                        BiomeColors.getGrassColor(world, pos) + 2000 : GrassColors.getColor(0.5D, 1.0D),
                WWBlocks.MOSS_COVER);

        ColorProviderRegistryImpl.ITEM.register((stack, tintIndex) -> GrassColors.getColor(0.5D, 1.0D),
                WWBlocks.MOSS_COVER);

        EntityRendererRegistry.register(WWBlocks.WISTERIA.BOAT_ENTITY, BoatEntityRenderer::new);

        TexturedRenderLayers.addDefaultTextures((spriteIdentifier -> new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE,
                new Identifier("wilderworld", "wisteria_sign"))));
    }
}