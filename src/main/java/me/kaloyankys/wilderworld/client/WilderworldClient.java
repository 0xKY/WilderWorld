package me.kaloyankys.wilderworld.client;

import me.kaloyankys.wilderworld.block.MembraneRenderer;
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
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.client.rendering.BlockEntityRendererRegistryImpl;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.particle.DefaultParticleType;
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
                WWBlocks.findBlock("aspen_sapling"), WWBlocks.findBlock("wisteria_sapling"), WWBlocks.BUTTERFLY_SPAWN,
                WWBlocks.EBONY_BUSH, WWBlocks.EBONY_BUSH_TALL, WWBlocks.GLOWGI, WWBlocks.SCENTED_CANDLE, WWBlocks.DROOPBLOOM, WWBlocks.WISTERIA_BLOSSOM_LEAVES,
                WWBlocks.COCONUT_FRUIT, WWBlocks.COCONUT);

        float chocolateRed = 0.5f; float chocolateBlue = 0.5f; float chocolateGreen = 0.5f;
        float sweetBerryRed = 0.5f; float sweetBerryBlue = 0.5f; float sweetBerryGreen = 0.5f;
        float mintRed = 0.5f; float mintBlue = 0.5f; float mintGreen = 0.5f;
        ParticleFactoryRegistry.getInstance().register(WWParticles.STEAM, SteamParticle.SteamFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.CHOCOLATE_DRIP, dripping(chocolateRed, chocolateBlue, chocolateGreen));
        ParticleFactoryRegistry.getInstance().register(WWParticles.CHOCOLATE_FALL, falling(chocolateRed, chocolateBlue, chocolateGreen));
        ParticleFactoryRegistry.getInstance().register(WWParticles.CHOCOLATE_LAND, landing(chocolateRed, chocolateBlue, chocolateGreen));
        ParticleFactoryRegistry.getInstance().register(WWParticles.SWEETBERRY_DRIP, dripping(sweetBerryRed, sweetBerryBlue, sweetBerryGreen));
        ParticleFactoryRegistry.getInstance().register(WWParticles.SWEETBERRY_FALL, falling(sweetBerryRed, sweetBerryBlue, sweetBerryGreen));
        ParticleFactoryRegistry.getInstance().register(WWParticles.SWEETBERRY_LAND, landing(sweetBerryRed, sweetBerryBlue, sweetBerryGreen));
        ParticleFactoryRegistry.getInstance().register(WWParticles.MINT_DRIP, dripping(mintRed, mintBlue, mintGreen));
        ParticleFactoryRegistry.getInstance().register(WWParticles.MINT_FALL, falling(mintRed, mintBlue, mintGreen));
        ParticleFactoryRegistry.getInstance().register(WWParticles.MINT_LAND, landing(mintRed, mintBlue, mintGreen));
        ParticleFactoryRegistry.getInstance().register(WWParticles.SPELUNKING_GLOW, CustomGlowParticle.SpelunkingGlowFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.SPLASH, SplashParticle.DefaultFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.GLOW_SEEDS, GlowSeedParticle.GlowbrushSeedFactory::new);
        ParticleFactoryRegistry.getInstance().register(WWParticles.AMBIENT_GLOW_SEEDS, GlowSeedParticle.AmbientGlowbrushSeedFactory::new);

        EntityRendererRegistry.register(WWEntities.BUTTERFLY, ButterflyEntityRenderer::new);
        EntityRendererRegistry.register(WWEntities.DOOD, DoodEntityRenderer::new);
        EntityRendererRegistry.register(WWEntities.GEYSER_STREAM, GeyserStreamRenderer::new);
        EntityRendererRegistry.register(WWEntities.GEYSER_STREAM, GeyserStreamRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BUTTERFLY_RENDER_LAYER, ButterflyModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DOOD_RENDER_LAYER, DoodModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GEYSER_STREAM_RENDER_LAYER, GeyserStreamModel::getTexturedModelData);

        EntityRendererRegistry.register(WWEntities.ASPEN_BOAT, (context -> new BoatEntityRenderer(context, false)));
        EntityRendererRegistry.register(WWEntities.WISTERIA_BOAT, (context -> new BoatEntityRenderer(context, false)));
        EntityRendererRegistry.register(WWEntities.ASPEN_CHEST_BOAT, (context -> new BoatEntityRenderer(context, true)));
        EntityRendererRegistry.register(WWEntities.WISTERIA_CHEST_BOAT, (context -> new BoatEntityRenderer(context, true)));

        BlockEntityRendererRegistryImpl.register(WWEntities.SINGLE_ITEM_HOLDER, MembraneRenderer::new);

        TexturedRenderLayers.addDefaultTextures((spriteIdentifier -> new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE,
                new Identifier("wilderworld", "wisteria"))));
        TexturedRenderLayers.addDefaultTextures((spriteIdentifier -> new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE,
                new Identifier("wilderworld", "aspen"))));
    }

    private static ParticleFactory<DefaultParticleType> dripping(float red, float blue, float green) {
      return ((particleEffect, clientWorld, d, e, f, g, h, i) -> CustomBlockLeakParticle.createDrippingIcecream(particleEffect, clientWorld, d, e, f, g, h, i, red, blue, green));
    }
    private static ParticleFactory<DefaultParticleType> falling(float red, float blue, float green) {
        return ((particleEffect, clientWorld, d, e, f, g, h, i) -> CustomBlockLeakParticle.createFallingIcecream(particleEffect, clientWorld, d, e, f, g, h, i, red, blue, green));
    }
    private static ParticleFactory<DefaultParticleType> landing(float red, float blue, float green) {
        return ((particleEffect, clientWorld, d, e, f, g, h, i) -> CustomBlockLeakParticle.createLandingIcecream(particleEffect, clientWorld, d, e, f, g, h, i, red, blue, green));
    }
}