package me.kaloyankys.wilderworld.entity;

import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.init.WWEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.NumberRange;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class ButterflyEntity extends FlyingEntity implements Flutterer {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(ButterflyEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final NumberRange.IntRange HORIZONTAL_RANGE = NumberRange.IntRange.between(-8, 12);
    private static final NumberRange.IntRange VERTICAL_RANGE = NumberRange.IntRange.between(-2, 2);

    public ButterflyEntity(World world) {
        super(WWEntities.BUTTERFLY, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0F);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
    }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 2);
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(8, new FlyRandomlyGoal());
        this.goalSelector.add(10, new SwimGoal(this));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", getVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dataTracker.set(VARIANT, nbt.getInt("Variant"));
    }

    public int getVariant() {
        return dataTracker.get(VARIANT);
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
        this.fallDistance = 0.0F;
    }

    @Override
    protected boolean hasWings() {
        return true;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int variant = random.nextInt(7);
        dataTracker.set(VARIANT, variant);

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {
            @Override
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }};
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public boolean isInAir() {
        return !this.isOnGround();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {}

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS;
    }

    private class FlyRandomlyGoal extends Goal {

        private FlyRandomlyGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        public boolean canStart() {
            return ButterflyEntity.this.random.nextInt(2) == 0;
        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void tick() {
            BlockPos pos = getBlockPos();

            for (int i = 0; i < 3; ++i) {
                BlockPos randomTarget = pos.add(HORIZONTAL_RANGE.getMax() - random.nextInt(19),
                        VERTICAL_RANGE.getMax() - random.nextInt(5), HORIZONTAL_RANGE.getMax() - random.nextInt(15));

                if (ButterflyEntity.this.world.isAir(randomTarget) && ButterflyEntity.this.world.getBiomeKey(randomTarget).isPresent()) {
                    if (ButterflyEntity.this.world.getBiomeKey(randomTarget).get().equals(BiomeKeys.FLOWER_FOREST)) {
                        ButterflyEntity.this.moveControl.moveTo(randomTarget.getX() + 0.5D,
                                randomTarget.getY() + 0.5D, randomTarget.getZ() + 0.5D, 0.75D);
                    } else {
                        ButterflyEntity.this.moveControl.moveTo(randomTarget.getX() + 0.2D,
                                randomTarget.getY() + 0.2D, randomTarget.getZ() + 0.2D, 0.05D);
                    }
                }
            }
        }
    }
}
