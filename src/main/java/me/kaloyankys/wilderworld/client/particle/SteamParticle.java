package me.kaloyankys.wilderworld.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class SteamParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    SteamParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, SpriteProvider spriteProvider) {
        super(clientWorld, d, e, f, 0.0D, 0.0D, 0.0D);
        this.velocityMultiplier = 0.96F;
        this.spriteProvider = spriteProvider;
        this.velocityX *= 0.10000000149011612D;
        this.velocityY *= 0.10000000149011612D;
        this.velocityZ *= 0.10000000149011612D;
        this.velocityX += g;
        this.velocityY += h;
        this.velocityZ += i;
        float k = 1.0F - (float) (Math.random() * 0.30000001192092896D);
        this.colorRed = k;
        this.colorGreen = k;
        this.colorBlue = k;
        this.scale *= 1.875F;
        int l = (int) (8.0D / (Math.random() * 0.8D + 0.3D));
        this.maxAge = (int) Math.max((float) l * 2.5F, 1.0F);
        this.collidesWithWorld = true;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public void setColorAlpha(float alpha) {
        super.setColorAlpha(alpha);
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float) this.age + tickDelta) / (float) this.maxAge * 32.0F, 0.0F, 1.0F);
    }

    public void tick() {
        super.tick();
        if (!this.dead) {
            this.setSpriteForAge(this.spriteProvider);
            PlayerEntity playerEntity = this.world.getClosestPlayer(this.x, this.y, this.z, 2.0D, false);
            if (playerEntity != null) {
                double d = playerEntity.getY();
                if (this.y > d) {
                    this.y += (d - this.y) * 0.2D;
                    this.velocityY += (playerEntity.getVelocity().y - this.velocityY) * 0.2D;
                    this.setPos(this.x, this.y, this.z);
                }
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static class SteamFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public SteamFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            Random random = new Random();
            SteamParticle particle = new SteamParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
            particle.setColorAlpha(random.nextFloat() - 0.8F);
            particle.setColor(0.72F, 0.782F, 0.92F);
            return particle;
        }
    }
}