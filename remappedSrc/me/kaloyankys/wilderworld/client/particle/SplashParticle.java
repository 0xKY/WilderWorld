package me.kaloyankys.wilderworld.client.particle;

import me.kaloyankys.wilderworld.init.WWParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.*;

public class SplashParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    public SplashParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.spriteProvider = spriteProvider;
        this.maxAge = 300;
        this.scale *= 2.5f + random.nextFloat();
        this.setSpriteForAge(spriteProvider);
        this.alpha = 0f;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.alpha = 0.3f + random.nextFloat() / 2;
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.velocityX *= 0.95f;
        this.velocityY -= 0.02f;
        this.velocityZ *= 0.95f;

        if (this.age++ >= this.maxAge) {
            this.velocityX /= 2;
            this.velocityY /= 2;
            this.velocityZ /= 2;
        }

        if (this.onGround) {
            this.markDead();
        }

        this.move(velocityX, velocityY, velocityZ);
    }


    @Environment(EnvType.CLIENT)
    public static class DefaultFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public DefaultFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SplashParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
        }
    }
}