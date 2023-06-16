package me.kaloyankys.wilderworld.entity;

import me.kaloyankys.wilderworld.entity.render.DoodEntityRenderer;
import me.kaloyankys.wilderworld.init.WWEntities;
import me.kaloyankys.wilderworld.init.WWItems;
import me.kaloyankys.wilderworld.recipe.ChewingRecipe;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DoodEntity extends AnimalEntity implements InventoryOwner {
    private final SimpleInventory inventory = new SimpleInventory(1);
    public static final TrackedData<Integer> CHEWING_TICKS = DataTracker.registerData(DoodEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public DoodEntity(World world) {
        super(WWEntities.DOOD, world);
        this.moveControl = new MoveControl(this);
        this.lookControl = new YawAdjustingLookControl(this, 10);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.2));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(1, new TemptGoal(this, 1.1, Ingredient.ofItems(WWItems.MINT), false));
        this.goalSelector.add(3, new WanderAroundGoal(this, 2.0));
        this.goalSelector.add(1, new LookAtEntityGoal(this, LivingEntity.class, 8.0f));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CHEWING_TICKS, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("Inventory", this.inventory.toNbtList());
    }

    @Override
    public SimpleInventory getInventory() {
        return this.inventory;
    }

    protected void addItem(ItemStack stack) {
        this.inventory.addStack(stack);
    }

    protected boolean canInsertIntoInventory(ItemStack stack) {
        return this.inventory.canInsert(stack);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return WWEntities.DOOD.create(world);
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
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(WWItems.MINT);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.canMoveVoluntarily() && this.isTouchingWater()) {
            this.updateVelocity(0.3f, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
            if (this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    protected void mobTick() {
        super.mobTick();

        ItemStack stack = this.getInventory().getStack(0);

        if (this.getChewTicks() > 0) {
            this.decrementChewTicks();
        } else {
            ItemScatterer.spawn(world, this, inventory);
            stack.decrement(stack.getCount());
        }
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ChewingRecipe.RECIPES.forEach((input, output) -> {
            if (player.getStackInHand(hand).getItem() == input) {
                if (this.canInsertIntoInventory(output.getDefaultStack())) this.addItem(output.getDefaultStack());
                this.dataTracker.set(CHEWING_TICKS, 80 + random.nextInt(20));
            }
        });
        return super.interactMob(player, hand);
    }

    public int getChewTicks() {
        return this.dataTracker.get(CHEWING_TICKS);
    }

    private void decrementChewTicks() {
        this.dataTracker.set(CHEWING_TICKS, this.getChewTicks() - 1);
    }
}
