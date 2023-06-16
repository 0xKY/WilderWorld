package me.kaloyankys.wilderworld.client.particle;

import me.kaloyankys.wilderworld.init.WWNetwork;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

@Environment(value = EnvType.CLIENT)
public class GlowSeedParticle extends SplashParticle {
    private final SpriteProvider spriteProvider;
    private final boolean ambient;

    public GlowSeedParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider, boolean ambient) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        this.spriteProvider = spriteProvider;
        this.ambient = ambient;
        this.maxAge = 300;
        this.scale *= 0.25f + random.nextFloat();
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    @Override
    public void tick() {
        this.velocityY *= this.gravityStrength;

        if (this.onGround) {
            if (!ambient) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(new BlockPos((int) this.x, (int) this.y, (int) this.z));
                ClientPlayNetworking.send(WWNetwork.GLOWBRUSH_SEED_PLANT_C2S, buf);
            }
            this.markDead();
        }

        super.tick();
    }

    @Environment(value = EnvType.CLIENT)
    public static class GlowbrushSeedFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public GlowbrushSeedFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            GlowSeedParticle seed = new GlowSeedParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider, false);
            seed.gravityStrength = 1.0f;
            return seed;
        }
    }
    @Environment(value = EnvType.CLIENT)
    public static class AmbientGlowbrushSeedFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public AmbientGlowbrushSeedFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            GlowSeedParticle seed = new GlowSeedParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider, true);
            seed.gravityStrength = 0;
            return seed;
        }
    }
}
