package me.kaloyankys.wilderworld.client;

import me.kaloyankys.wilderworld.client.particle.*;
import me.kaloyankys.wilderworld.entity.render.*;
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
    public static final EntityModelLayer DOOD_RENDER_LAYER = new EntityModelLayer(
            new Identifier("wilderworld", "dood"), "dood_render_layer");
    public static final EntityModelLayer GEYSER_STREAM_RENDER_LAYER = new EntityModelLayer(
            new Identifier("wilderworld", "geyser_stream"), "geyser_stream_render_layer");

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                WWBlocks.BIRD_OF_PARADISE, WWBlocks.CHAMOMILE, WWBlocks.RAGING_VIOLET, WWBlocks.SHELFSHROOM, WWBlocks.GLOWBRUSH,
                WWBlocks.WISTERIA.POTTED_SAPLING, WWBlocks.WISTERIA.SAPLING, WWBlocks.ASPEN_SAPLING, WWBlocks.BUTTERFLY_SPAWN,
                WWBlocks.EBONY_BUSH, WWBlocks.EBONY_BUSH_TALL, WWBlocks.GLOWGI, WWBlocks.SCENTED_CANDLE, WWBlocks.DROOPBLOOM, WWBlocks.WISTERIA_BLOSSOM_LEAVES);

        ParticleFactoryRegistry.getInstance().register(WWParticles.STEAM, SteamParticle.SteamFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.CHOCOLATE_DRIP, CustomBlockLeakParticle.ChocolateDripFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.CHOCOLATE_FALL, CustomBlockLeakParticle.ChocolateFallFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.CHOCOLATE_LAND, CustomBlockLeakParticle.ChocolateLandFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.SWEETBERRY_DRIP, CustomBlockLeakParticle.SweetBerryDripFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.SWEETBERRY_FALL, CustomBlockLeakParticle.SweetBerryFallFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.SWEETBERRY_LAND, CustomBlockLeakParticle.SweetBerryLandFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.MINT_DRIP, CustomBlockLeakParticle.MintDripFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.MINT_FALL, CustomBlockLeakParticle.MintFallFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.MINT_LAND, CustomBlockLeakParticle.MintLandFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.SPELUNKING_GLOW, CustomGlowParticle.SpelunkingGlowFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.SPLASH, SplashParticle.DefaultFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.GLOW_SEEDS, GlowSeedParticle.GlowbrushSeedFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.AMBIENT_GLOW_SEEDS, GlowSeedParticle.AmbientGlowbrushSeedFactory::new);

        EntityRendererRegistry.register(WWEntities.BUTTERFLY, ButterflyEntityRenderer::new);
        EntityRendererRegistry.register(WWEntities.DOOD, DoodEntityRenderer::new);
        EntityRendererRegistry.register(WWEntities.GEYSER_STREAM, GeyserStreamRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BUTTERFLY_RENDER_LAYER, ButterflyModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DOOD_RENDER_LAYER, DoodModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GEYSER_STREAM_RENDER_LAYER, GeyserStreamModel::getTexturedModelData);

        EntityRendererRegistry.register(WWBlocks.WISTERIA.BOAT_ENTITY, (context -> new BoatEntityRenderer(context, true)));
        EntityRendererRegistry.register(WWBlocks.ASPEN.BOAT_ENTITY, (context -> new BoatEntityRenderer(context, true)));

        TexturedRenderLayers.addDefaultTextures((spriteIdentifier -> new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE,
                new Identifier("wilderworld", "wisteria_sign"))));
        TexturedRenderLayers.addDefaultTextures((spriteIdentifier -> new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE,
                new Identifier("wilderworld", "aspen_sign"))));
    }
}