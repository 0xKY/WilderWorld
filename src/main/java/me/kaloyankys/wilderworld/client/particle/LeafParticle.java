package me.kaloyankys.wilderworld.client.particle;

import me.kaloyankys.wilderworld.init.WWBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class LeafParticle extends SpriteBillboardParticle {
    private final float rotationSpeed;
    private final SpriteProvider spriteProvider;

    public LeafParticle(ClientWorld world, double x, double y, double z, float colorRed, float colorGreen, float colorBlue, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.spriteProvider = spriteProvider;
        this.colorRed = colorRed;
        this.colorGreen = colorGreen;
        this.colorBlue = colorBlue;
        this.maxAge = 800;
        this.setSpriteForAge(spriteProvider);
        this.rotationSpeed = ((float) Math.random() - 0.5f) * 0.1f;
        this.angle = (float) Math.random() * ((float) Math.PI * 2);
        this.scale *= 0.2f + random.nextFloat() / 1.5f;
        this.gravityStrength /= 6;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float) this.age + tickDelta) / (float) this.maxAge * 32.0f, 0.0f, 1.0f);
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
            return;
        }
        this.setSpriteForAge(this.spriteProvider);
        this.prevAngle = this.angle;
        this.angle += (float) Math.PI * this.rotationSpeed * 2.0f;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityY -= 0.0003f;
        this.velocityY = Math.max(this.velocityY, -0.14f);
        this.velocityX += 0.001f;
        this.velocityZ += 0.001f;
        float upperAngle = this.angle + 0.1f;
        float lowerAngle = this.angle - 0.1f;
        if (!this.onGround) {
            if (this.angle < upperAngle) {
                this.angle += 0.0002f;
            } else if (this.angle > lowerAngle) {
                this.angle -= 0.0002f;
            }
        } else {
            this.angle = this.prevAngle;
        }
        this.gravityStrength /= 3 + random.nextFloat() * 1.5f;
    }

    @Environment(value = EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        @Nullable
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            int j = world.getBiome(new BlockPos(x, y, z)).getFoliageColor();
            float k = (float) (j >> 16 & 0xFF) / 255.0f;
            float l = (float) (j >> 8 & 0xFF) / 255.0f;
            float m = (float) (j & 0xFF) / 255.0f;
            if (world.getBlockState(new BlockPos(x, y, z)).isOf(WWBlocks.ASPEN_LEAVES)) {
                return new LeafParticle(world, x, y, z, 1.0f, 0.6f, 0.0f, spriteProvider);
            } else {
                return new LeafParticle(world, x, y, z, k, l, m, spriteProvider);
            }
        }
    }
}

