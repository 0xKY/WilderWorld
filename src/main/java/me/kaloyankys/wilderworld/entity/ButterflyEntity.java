package me.kaloyankys.wilderworld.entity;

import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.init.WWEntities;
import me.kaloyankys.wilderworld.init.WWItems;
import me.kaloyankys.wilderworld.init.WWSounds;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoWaterTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class ButterflyEntity extends AnimalEntity implements Flutterer {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(ButterflyEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public ButterflyEntity(World world) {
        super(WWEntities.BUTTERFLY, world);
        this.moveControl = new FlightMoveControl(this, 5, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0F);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0F);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, random.nextInt(4));
    }

    @Override
    public void growUp(int i, boolean bl) {
        super.growUp(i, bl);
        for (int wingCount = 0; wingCount < random.nextInt(3); i++) {
            ItemScatterer.spawn(getWorld(), this.getX(), this.getY(), this.getZ(), new ItemStack(WWItems.BUTTERFLY_WING));
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new FlyRandomlyGoal(this));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.getStackInHand(hand).isOf(Items.SUGAR) && player.getStackInHand(hand).getCount() >= 16) {
            if (!this.getWorld().isClient) {
                getWorld().setBlockState(this.getBlockPos(), WWBlocks.BUTTERFLY_SPAWN.getDefaultState());
                if (!player.isCreative()) player.getStackInHand(hand).decrement(16);
            } else {
                ParticleUtil.spawnParticle(getWorld(), this.getBlockPos(), ParticleTypes.HEART, UniformIntProvider.create(1, 4));
            }
            return ActionResult.CONSUME;
        }
        return super.interactMob(player, hand);
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
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int variant = random.nextInt(7);
        dataTracker.set(VARIANT, variant);

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected float getSoundVolume() {
        return 0.7F;
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
    protected SoundEvent getAmbientSound() {
        return WWSounds.BUTTERFLY_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return WWSounds.BUTTERFLY_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return WWSounds.BUTTERFLY_DEATH;
    }


    static class FlyRandomlyGoal extends Goal {
        private final PathAwareEntity entity;

        public FlyRandomlyGoal(PathAwareEntity pathAwareEntity) {
            this.entity = pathAwareEntity;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        public boolean canStart() {
            return entity.getNavigation().isIdle();
        }

        public boolean shouldContinue() {
            return entity.getNavigation().isFollowingPath();
        }

        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                entity.getNavigation().startMovingAlong(entity.getNavigation().findPathTo(new BlockPos((int) vec3d.x, (int) vec3d.y, (int) vec3d.z), 1), 1.0D);
            }

        }

        private Vec3d getRandomLocation() {
            Vec3d vec3d3 = entity.getRotationVec(0.0F);
            Vec3d vec3d4 = AboveGroundTargeting.find(entity, 8, 7,
                    vec3d3.getX(), vec3d3.getZ(), 1.5707964F, 2, 1);
            return vec3d4 != null ? vec3d4 : NoWaterTargeting.find(entity, 8, 4, -2, vec3d3, 1.5707963705062866D);
        }
    }
}
