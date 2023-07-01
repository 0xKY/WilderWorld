package me.kaloyankys.wilderworld.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(value = EnvType.CLIENT)
public class CustomGlowParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    public CustomGlowParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.velocityMultiplier = 0.96f;
        this.spriteProvider = spriteProvider;
        this.scale *= 0.75f;
        this.collidesWithWorld = false;
        this.setSpriteForAge(spriteProvider);
    }

    public float getSize(float tickDelta) {
        return this.scale * (this.getMaxAge() - this.age) / 70;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.setSpriteForAge(this.spriteProvider);
        super.tick();
    }

    @Environment(value = EnvType.CLIENT)
    public static class SpelunkingGlowFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public SpelunkingGlowFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            CustomGlowParticle glowParticle = new CustomGlowParticle(clientWorld, d, e, f, this.spriteProvider);
            glowParticle.setVelocity(g * 0.05, h * 0.05, i * 0.05);
            glowParticle.setMaxAge(clientWorld.random.nextInt(200) + 20);
            return glowParticle;
        }
    }
}
