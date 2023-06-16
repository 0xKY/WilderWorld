package me.kaloyankys.wilderworld.entity;

import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.init.WWEntities;
import me.kaloyankys.wilderworld.init.WWParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class GeyserStreamEntity extends MobEntity {
    private int size;
    private int age;
    public static final TrackedData<Integer> SPRAY_TICKS = DataTracker.registerData(GeyserStreamEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> WAIT_TICKS = DataTracker.registerData(GeyserStreamEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Float> HEIGHT = DataTracker.registerData(GeyserStreamEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> TARGET_HEIGHT = DataTracker.registerData(GeyserStreamEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final int INFLATION_TICKS = 800;
    private static final int MAX_AGE = 1600;

    public GeyserStreamEntity(World world) {
        super(WWEntities.GEYSER_STREAM, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        size = (random.nextInt(3) + 1);
        age = 0;

        this.dataTracker.startTracking(SPRAY_TICKS, INFLATION_TICKS);
        this.dataTracker.startTracking(WAIT_TICKS, 0);
        this.dataTracker.startTracking(HEIGHT, 0.0f);
        this.dataTracker.startTracking(TARGET_HEIGHT, 0.0f);
    }

    @Override
    public void tick() {
        super.tick();

        if (random.nextInt(10) == 0) {
            Direction.stream().forEach((direction -> {
                BlockPos pos = this.getBlockPos().offset(direction);
                if (getWorld().getBlockState(pos).isOf(Blocks.WATER)) {
                    GeyserStreamEntity newGeyser = new GeyserStreamEntity(getWorld());
                    getWorld().spawnEntity(newGeyser);
                    newGeyser.setPersistent();
                }
            }));
        }

        if (this.getSprayTicks() < INFLATION_TICKS) {
            this.increment(SPRAY_TICKS, 1 + random.nextInt(10));
        } else if (this.getSprayTicks() >= INFLATION_TICKS) {
            this.increment(WAIT_TICKS, 20 + random.nextInt(10));
        }

        if (this.getWaitTicks() >= INFLATION_TICKS) {
            this.set(WAIT_TICKS, 0);
            this.set(SPRAY_TICKS, 0);
        }

        if (this.getSprayTicks() > 0 && this.getSprayTicks() < INFLATION_TICKS && this.getWaitTicks() == 0) {
            this.increment(HEIGHT, 0.01f + (0.1f - this.getSprayTicks() / 800.0f));
            if (random.nextInt(3) == 0 && this.getExtent() > 0) {
                ParticleUtil.spawnParticle(getWorld(), new BlockPos((int) this.getX(), (int) ((this.getY() + (this.getExtent() * this.getSize() * 3)) - 1), (int) this.getZ()), WWParticles.SPLASH, UniformIntProvider.create(0, 2));
            }
        }
        if (this.getWaitTicks() > 0 && this.getExtent() > 0) {
            this.decrement(HEIGHT, 0.01f);
        }

        if (this.getExtent() < 0) {
            this.set(HEIGHT, 0.0f);
        }

        if (!getWorld().getBlockState(this.getBlockPos().down()).isOf(WWBlocks.TRAVERTINE) && !getWorld().getBlockState(this.getBlockPos().down()).isOf(WWBlocks.TRAVERTINE)) {
            this.kill();
        }

        if (age++ == MAX_AGE) {
            this.kill();
        }
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbtCompound) {
        if (nbtCompound.contains("Size")) {
            size = nbtCompound.getInt("Size");
        }
        if (nbtCompound.contains("Age")) {
            age = nbtCompound.getInt("Age");
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbtCompound) {
        nbtCompound.putInt("Size", size);
    }

    @Override
    public EntitySpawnS2CPacket createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    public int getSprayTicks() { return this.dataTracker.get(SPRAY_TICKS); }

    public int getWaitTicks() { return this.dataTracker.get(WAIT_TICKS); }

    public float getExtent() { return this.dataTracker.get(HEIGHT); }

    public float getTargetHeight() { return this.dataTracker.get(TARGET_HEIGHT); }

    public void increment(TrackedData<Integer> data, int amount) {
        this.dataTracker.set(data, this.dataTracker.get(data) + amount);
    }

    public void decrement(TrackedData<Integer> data, int amount) {
        this.dataTracker.set(data, this.dataTracker.get(data) - amount);
    }

    public void increment(TrackedData<Float> data, float amount) {
        this.dataTracker.set(data, this.dataTracker.get(data) + amount);
    }

    public void decrement(TrackedData<Float> data, float amount) {
        this.dataTracker.set(data, this.dataTracker.get(data) - amount);
    }

    public <T> void set(TrackedData<T> data, T value) {
        this.dataTracker.set(data, value);
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public boolean canSpawn(WorldView worldView) {
        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = worldView.getBlockState(blockPos.down());
        return (blockState.isOf(WWBlocks.TRAVERTINE) || blockState.isOf(WWBlocks.TRAVERTINE_PEACH)) && worldView.getBlockState(blockPos).getFluidState().isOf(Fluids.WATER);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean doesNotCollide(double d, double e, double f) {
        return true;
    }

    @Override
    public void onPlayerCollision(PlayerEntity playerEntity) {
        super.onPlayerCollision(playerEntity);

        if (this.getExtent() > 0) {
            playerEntity.addVelocity(0, 1.0 + this.getExtent(), 0);
        }
    }


}
