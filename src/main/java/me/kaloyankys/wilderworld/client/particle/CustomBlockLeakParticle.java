package me.kaloyankys.wilderworld.client.particle;

import me.kaloyankys.wilderworld.init.WWParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;

public abstract class CustomBlockLeakParticle extends SpriteBillboardParticle {
    private final Fluid fluid;

    CustomBlockLeakParticle(ClientWorld world, double x, double y, double z, Fluid fluid) {
        super(world, x, y, z);
        this.setBoundingBoxSpacing(0.01f, 0.01f);
        this.gravityStrength = 0.06f;
        this.fluid = fluid;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getBrightness(float tint) {
        return 100;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.updateAge();
        if (!this.dead) {
            this.velocityY -= this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.updateVelocity();
            this.velocityX *= 0.98f;
            this.velocityY *= 0.98f;
            this.velocityZ *= 0.98f;
            BlockPos blockPos = new BlockPos(this.x, this.y, this.z);
            FluidState fluidState = this.world.getFluidState(blockPos);
            if (fluidState.getFluid() == this.fluid && this.y < (double) ((float) blockPos.getY() + fluidState.getHeight(this.world, blockPos))) {
                this.markDead();
            }
        }
    }

    protected void updateAge() {
        if (this.maxAge-- <= 0) {
            this.markDead();
        }
    }

    protected void updateVelocity() {
    }

    @Environment(value = EnvType.CLIENT)
    public static class ChocolateDripFactory extends BlockLeakParticle.DrippingObsidianTearFactory {
        public ChocolateDripFactory(SpriteProvider spriteProvider) {
            super(spriteProvider);
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            CustomBlockLeakParticle.Dripping dripping = new CustomBlockLeakParticle.Dripping(clientWorld, d, e, f, Fluids.EMPTY, WWParticles.CHOCOLATE_FALL);
            dripping.gravityStrength *= 0.01f;
            dripping.maxAge = 100;
            dripping.setColor(0.8f, 0.6f, 0.4f);
            dripping.setSprite(this.spriteProvider);
            return dripping;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class ChocolateFallFactory extends BlockLeakParticle.FallingObsidianTearFactory {
        public ChocolateFallFactory(SpriteProvider spriteProvider) {
            super(spriteProvider);
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            CustomBlockLeakParticle.ContinuousFalling continuousFalling = new CustomBlockLeakParticle.ContinuousFalling(clientWorld, d, e, f, Fluids.EMPTY, WWParticles.CHOCOLATE_LAND);
            continuousFalling.gravityStrength = 0.01f;
            continuousFalling.setColor(0.8f, 0.6f, 0.4f);
            continuousFalling.setSprite(this.spriteProvider);
            return continuousFalling;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class ChocolateLandFactory extends BlockLeakParticle.LandingObsidianTearFactory {
        public ChocolateLandFactory(SpriteProvider spriteProvider) {
            super(spriteProvider);
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            CustomBlockLeakParticle.Landing landing = new CustomBlockLeakParticle.Landing(clientWorld, d, e, f, Fluids.EMPTY);
            landing.maxAge = (int) (28.0 / (Math.random() * 0.8 + 0.2));
            landing.setColor(0.8f, 0.6f, 0.4f);
            landing.setSprite(this.spriteProvider);
            return landing;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class SweetBerryDripFactory extends BlockLeakParticle.DrippingObsidianTearFactory {
        public SweetBerryDripFactory(SpriteProvider spriteProvider) {
            super(spriteProvider);
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            CustomBlockLeakParticle.Dripping dripping = new CustomBlockLeakParticle.Dripping(clientWorld, d, e, f, Fluids.EMPTY, WWParticles.SWEETBERRY_FALL);
            dripping.gravityStrength *= 0.01f;
            dripping.maxAge = 100;
            dripping.setColor(0.8f, 0.4f, 0.4f);
            dripping.setSprite(this.spriteProvider);
            return dripping;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class SweetBerryFallFactory extends BlockLeakParticle.FallingObsidianTearFactory {
        public SweetBerryFallFactory(SpriteProvider spriteProvider) {
            super(spriteProvider);
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            CustomBlockLeakParticle.ContinuousFalling continuousFalling = new CustomBlockLeakParticle.ContinuousFalling(clientWorld, d, e, f, Fluids.EMPTY, WWParticles.SWEETBERRY_LAND);
            continuousFalling.gravityStrength = 0.01f;
            continuousFalling.setColor(0.8f, 0.4f, 0.4f);
            continuousFalling.setSprite(this.spriteProvider);
            return continuousFalling;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class SweetBerryLandFactory extends BlockLeakParticle.LandingObsidianTearFactory {
        public SweetBerryLandFactory(SpriteProvider spriteProvider) {
            super(spriteProvider);
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            CustomBlockLeakParticle.Landing landing = new CustomBlockLeakParticle.Landing(clientWorld, d, e, f, Fluids.EMPTY);
            landing.maxAge = (int) (28.0 / (Math.random() * 0.8 + 0.2));
            landing.setColor(0.8f, 0.4f, 0.4f);
            landing.setSprite(this.spriteProvider);
            return landing;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class MintDripFactory extends BlockLeakParticle.DrippingObsidianTearFactory {
        public MintDripFactory(SpriteProvider spriteProvider) {
            super(spriteProvider);
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            CustomBlockLeakParticle.Dripping dripping = new CustomBlockLeakParticle.Dripping(clientWorld, d, e, f, Fluids.EMPTY, WWParticles.MINT_FALL);
            dripping.gravityStrength *= 0.01f;
            dripping.maxAge = 100;
            dripping.setColor(0.2f, 0.8f, 0.5f);
            dripping.setSprite(this.spriteProvider);
            return dripping;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class MintFallFactory extends BlockLeakParticle.FallingObsidianTearFactory {
        public MintFallFactory(SpriteProvider spriteProvider) {
            super(spriteProvider);
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            CustomBlockLeakParticle.ContinuousFalling continuousFalling = new CustomBlockLeakParticle.ContinuousFalling(clientWorld, d, e, f, Fluids.EMPTY, WWParticles.MINT_LAND);
            continuousFalling.gravityStrength = 0.01f;
            continuousFalling.setColor(0.2f, 0.8f, 0.5f);
            continuousFalling.setSprite(this.spriteProvider);
            return continuousFalling;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static class MintLandFactory extends BlockLeakParticle.LandingObsidianTearFactory {
        public MintLandFactory(SpriteProvider spriteProvider) {
            super(spriteProvider);
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            CustomBlockLeakParticle.Landing landing = new CustomBlockLeakParticle.Landing(clientWorld, d, e, f, Fluids.EMPTY);
            landing.maxAge = (int) (28.0 / (Math.random() * 0.8 + 0.2));
            landing.setColor(0.2f, 0.8f, 0.5f);
            landing.setSprite(this.spriteProvider);
            return landing;
        }
    }

    @Environment(value = EnvType.CLIENT)
    static class Dripping extends CustomBlockLeakParticle {
        private final ParticleEffect nextParticle;

        Dripping(ClientWorld world, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
            super(world, x, y, z, fluid);
            this.nextParticle = nextParticle;
            this.gravityStrength *= 0.02f;
            this.maxAge = 40;
        }

        @Override
        protected void updateAge() {
            if (this.maxAge-- <= 0) {
                this.markDead();
                this.world.addParticle(this.nextParticle, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
            }
        }

        @Override
        protected void updateVelocity() {
            this.velocityX *= 0.02;
            this.velocityY *= 0.02;
            this.velocityZ *= 0.02;
        }
    }

    @Environment(value = EnvType.CLIENT)
    static class ContinuousFalling extends Falling {
        protected final ParticleEffect nextParticle;

        ContinuousFalling(ClientWorld world, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
            super(world, x, y, z, fluid);
            this.nextParticle = nextParticle;
        }

        @Override
        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
                this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
            }
        }
    }

    @Environment(value = EnvType.CLIENT)
    static class Falling extends CustomBlockLeakParticle {
        Falling(ClientWorld clientWorld, double d, double e, double f, Fluid fluid) {
            this(clientWorld, d, e, f, fluid, (int) (64.0 / (Math.random() * 0.8 + 0.2)));
        }

        Falling(ClientWorld world, double x, double y, double z, Fluid fluid, int maxAge) {
            super(world, x, y, z, fluid);
            this.maxAge = maxAge;
        }

        @Override
        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
            }
        }
    }

    @Environment(value = EnvType.CLIENT)
    static class Landing extends CustomBlockLeakParticle {
        Landing(ClientWorld clientWorld, double d, double e, double f, Fluid fluid) {
            super(clientWorld, d, e, f, fluid);
            this.maxAge = (int) (16.0 / (Math.random() * 0.8 + 0.2));
        }
    }
}
