package me.kaloyankys.wilderworld.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CustomBlockLeakParticle extends SpriteBillboardParticle {
    private final Fluid fluid;
    protected boolean obsidianTear;

    CustomBlockLeakParticle(ClientWorld clientWorld, double d, double e, double f, Fluid fluid) {
        super(clientWorld, d, e, f);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.gravityStrength = 0.06F;
        this.fluid = fluid;
    }

    protected Fluid getFluid() {
        return this.fluid;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public int getBrightness(float f) {
        return this.obsidianTear ? 240 : super.getBrightness(f);
    }

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.updateAge();
        if (!this.dead) {
            this.velocityY -= (double)this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.updateVelocity();
            if (!this.dead) {
                this.velocityX *= 0.9800000190734863;
                this.velocityY *= 0.9800000190734863;
                this.velocityZ *= 0.9800000190734863;
                if (this.fluid != Fluids.EMPTY) {
                    BlockPos blockPos = BlockPos.ofFloored(this.x, this.y, this.z);
                    FluidState fluidState = this.world.getFluidState(blockPos);
                    if (fluidState.getFluid() == this.fluid && this.y < (double)((float)blockPos.getY() + fluidState.getHeight(this.world, blockPos))) {
                        this.markDead();
                    }

                }
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

    public static SpriteBillboardParticle createDrippingWater(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.Dripping(clientWorld, d, e, f, Fluids.WATER, ParticleTypes.FALLING_WATER);
        blockLeakParticle.setColor(0.2F, 0.3F, 1.0F);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createFallingWater(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.ContinuousFalling(clientWorld, d, e, f, Fluids.WATER, ParticleTypes.SPLASH);
        blockLeakParticle.setColor(0.2F, 0.3F, 1.0F);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createDrippingLava(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        return new CustomBlockLeakParticle.DrippingLava(clientWorld, d, e, f, Fluids.LAVA, ParticleTypes.FALLING_LAVA);
    }

    public static SpriteBillboardParticle createFallingLava(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.ContinuousFalling(clientWorld, d, e, f, Fluids.LAVA, ParticleTypes.LANDING_LAVA);
        blockLeakParticle.setColor(1.0F, 0.2857143F, 0.083333336F);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createLandingLava(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.Landing(clientWorld, d, e, f, Fluids.LAVA);
        blockLeakParticle.setColor(1.0F, 0.2857143F, 0.083333336F);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createDrippingHoney(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle.Dripping dripping = new CustomBlockLeakParticle.Dripping(clientWorld, d, e, f, Fluids.EMPTY, ParticleTypes.FALLING_HONEY);
        dripping.gravityStrength *= 0.01F;
        dripping.maxAge = 100;
        dripping.setColor(0.622F, 0.508F, 0.082F);
        return dripping;
    }

    public static SpriteBillboardParticle createFallingHoney(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.FallingHoney(clientWorld, d, e, f, Fluids.EMPTY, ParticleTypes.LANDING_HONEY);
        blockLeakParticle.gravityStrength = 0.01F;
        blockLeakParticle.setColor(0.582F, 0.448F, 0.082F);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createLandingHoney(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.Landing(clientWorld, d, e, f, Fluids.EMPTY);
        blockLeakParticle.maxAge = (int)(128.0 / (Math.random() * 0.8 + 0.2));
        blockLeakParticle.setColor(0.522F, 0.408F, 0.082F);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createDrippingDripstoneWater(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.Dripping(clientWorld, d, e, f, Fluids.WATER, ParticleTypes.FALLING_DRIPSTONE_WATER);
        blockLeakParticle.setColor(0.2F, 0.3F, 1.0F);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createFallingDripstoneWater(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.DripstoneLavaDrip(clientWorld, d, e, f, Fluids.WATER, ParticleTypes.SPLASH);
        blockLeakParticle.setColor(0.2F, 0.3F, 1.0F);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createDrippingDripstoneLava(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        return new CustomBlockLeakParticle.DrippingLava(clientWorld, d, e, f, Fluids.LAVA, ParticleTypes.FALLING_DRIPSTONE_LAVA);
    }

    public static SpriteBillboardParticle createFallingDripstoneLava(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.DripstoneLavaDrip(clientWorld, d, e, f, Fluids.LAVA, ParticleTypes.LANDING_LAVA);
        blockLeakParticle.setColor(1.0F, 0.2857143F, 0.083333336F);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createFallingNectar(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.Falling(clientWorld, d, e, f, Fluids.EMPTY);
        blockLeakParticle.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        blockLeakParticle.gravityStrength = 0.007F;
        blockLeakParticle.setColor(0.92F, 0.782F, 0.72F);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createFallingSporeBlossom(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        int j = (int)(64.0F / MathHelper.nextBetween(clientWorld.getRandom(), 0.1F, 0.9F));
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.Falling(clientWorld, d, e, f, Fluids.EMPTY, j);
        blockLeakParticle.gravityStrength = 0.005F;
        blockLeakParticle.setColor(0.32F, 0.5F, 0.22F);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createDrippingObsidianTear(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle.Dripping dripping = new CustomBlockLeakParticle.Dripping(clientWorld, d, e, f, Fluids.EMPTY, ParticleTypes.FALLING_OBSIDIAN_TEAR);
        dripping.obsidianTear = true;
        dripping.gravityStrength *= 0.01F;
        dripping.maxAge = 100;
        dripping.setColor(0.51171875F, 0.03125F, 0.890625F);
        return dripping;
    }

    public static SpriteBillboardParticle createFallingObsidianTear(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.ContinuousFalling(clientWorld, d, e, f, Fluids.EMPTY, ParticleTypes.LANDING_OBSIDIAN_TEAR);
        blockLeakParticle.obsidianTear = true;
        blockLeakParticle.gravityStrength = 0.01F;
        blockLeakParticle.setColor(0.51171875F, 0.03125F, 0.890625F);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createLandingObsidianTear(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.Landing(clientWorld, d, e, f, Fluids.EMPTY);
        blockLeakParticle.obsidianTear = true;
        blockLeakParticle.maxAge = (int)(28.0 / (Math.random() * 0.8 + 0.2));
        blockLeakParticle.setColor(0.51171875F, 0.03125F, 0.890625F);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createDrippingIcecream(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, float a, float b, float c) {
        CustomBlockLeakParticle.Dripping dripping = new CustomBlockLeakParticle.Dripping(clientWorld, d, e, f, Fluids.EMPTY, ParticleTypes.FALLING_OBSIDIAN_TEAR);
        dripping.obsidianTear = true;
        dripping.gravityStrength *= 0.01F;
        dripping.maxAge = 100;
        dripping.setColor(a, b, c);
        return dripping;
    }

    public static SpriteBillboardParticle createFallingIcecream(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, float a, float b, float c) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.ContinuousFalling(clientWorld, d, e, f, Fluids.EMPTY, ParticleTypes.LANDING_OBSIDIAN_TEAR);
        blockLeakParticle.obsidianTear = true;
        blockLeakParticle.gravityStrength = 0.01F;
        blockLeakParticle.setColor(a, b, c);
        return blockLeakParticle;
    }

    public static SpriteBillboardParticle createLandingIcecream(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, float a, float b, float c) {
        CustomBlockLeakParticle blockLeakParticle = new CustomBlockLeakParticle.Landing(clientWorld, d, e, f, Fluids.EMPTY);
        blockLeakParticle.obsidianTear = true;
        blockLeakParticle.maxAge = (int)(28.0 / (Math.random() * 0.8 + 0.2));
        blockLeakParticle.setColor(a, b, c);
        return blockLeakParticle;
    }

    @Environment(EnvType.CLIENT)
    private static class Dripping extends CustomBlockLeakParticle {
        private final ParticleEffect nextParticle;

        Dripping(ClientWorld clientWorld, double d, double e, double f, Fluid fluid, ParticleEffect particleEffect) {
            super(clientWorld, d, e, f, fluid);
            this.nextParticle = particleEffect;
            this.gravityStrength *= 0.02F;
            this.maxAge = 40;
        }

        protected void updateAge() {
            if (this.maxAge-- <= 0) {
                this.markDead();
                this.world.addParticle(this.nextParticle, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
            }

        }

        protected void updateVelocity() {
            this.velocityX *= 0.02;
            this.velocityY *= 0.02;
            this.velocityZ *= 0.02;
        }
    }

    @Environment(EnvType.CLIENT)
    private static class ContinuousFalling extends CustomBlockLeakParticle.Falling {
        protected final ParticleEffect nextParticle;

        ContinuousFalling(ClientWorld clientWorld, double d, double e, double f, Fluid fluid, ParticleEffect particleEffect) {
            super(clientWorld, d, e, f, fluid);
            this.nextParticle = particleEffect;
        }

        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
                this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
            }

        }
    }

    @Environment(EnvType.CLIENT)
    static class DrippingLava extends CustomBlockLeakParticle.Dripping {
        DrippingLava(ClientWorld clientWorld, double d, double e, double f, Fluid fluid, ParticleEffect particleEffect) {
            super(clientWorld, d, e, f, fluid, particleEffect);
        }

        protected void updateAge() {
            this.red = 1.0F;
            this.green = 16.0F / (float)(40 - this.maxAge + 16);
            this.blue = 4.0F / (float)(40 - this.maxAge + 8);
            super.updateAge();
        }
    }

    @Environment(EnvType.CLIENT)
    static class Landing extends CustomBlockLeakParticle {
        Landing(ClientWorld clientWorld, double d, double e, double f, Fluid fluid) {
            super(clientWorld, d, e, f, fluid);
            this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        }
    }

    @Environment(EnvType.CLIENT)
    static class FallingHoney extends CustomBlockLeakParticle.ContinuousFalling {
        FallingHoney(ClientWorld clientWorld, double d, double e, double f, Fluid fluid, ParticleEffect particleEffect) {
            super(clientWorld, d, e, f, fluid, particleEffect);
        }

        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
                this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
                float f = MathHelper.nextBetween(this.random, 0.3F, 1.0F);
                this.world.playSound(this.x, this.y, this.z, SoundEvents.BLOCK_BEEHIVE_DRIP, SoundCategory.BLOCKS, f, 1.0F, false);
            }

        }
    }

    @Environment(EnvType.CLIENT)
    static class DripstoneLavaDrip extends CustomBlockLeakParticle.ContinuousFalling {
        DripstoneLavaDrip(ClientWorld clientWorld, double d, double e, double f, Fluid fluid, ParticleEffect particleEffect) {
            super(clientWorld, d, e, f, fluid, particleEffect);
        }

        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
                this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
                SoundEvent soundEvent = this.getFluid() == Fluids.LAVA ? SoundEvents.BLOCK_POINTED_DRIPSTONE_DRIP_LAVA : SoundEvents.BLOCK_POINTED_DRIPSTONE_DRIP_WATER;
                float f = MathHelper.nextBetween(this.random, 0.3F, 1.0F);
                this.world.playSound(this.x, this.y, this.z, soundEvent, SoundCategory.BLOCKS, f, 1.0F, false);
            }

        }
    }

    @Environment(EnvType.CLIENT)
    static class Falling extends CustomBlockLeakParticle {
        Falling(ClientWorld clientWorld, double d, double e, double f, Fluid fluid) {
            this(clientWorld, d, e, f, fluid, (int)(64.0 / (Math.random() * 0.8 + 0.2)));
        }

        Falling(ClientWorld clientWorld, double d, double e, double f, Fluid fluid, int i) {
            super(clientWorld, d, e, f, fluid);
            this.maxAge = i;
        }

        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
            }

        }
    }
}
