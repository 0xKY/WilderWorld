package me.kaloyankys.wilderworld.entity;

import me.kaloyankys.wilderworld.entity.render.DoodEntityRenderer;
import me.kaloyankys.wilderworld.init.WWEntities;
import me.kaloyankys.wilderworld.init.WWItems;
import me.kaloyankys.wilderworld.recipe.ChewingRecipe;
import net.minecraft.entity.InventoryOwner;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.control.YawAdjustingLookControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DoodEntity extends AnimalEntity implements InventoryOwner {
    private final SimpleInventory inventory = new SimpleInventory(1);
    public int chewingTicks = 0;
    public boolean chewing = false;
    public boolean spitting = false;
    public Identifier currentTexture = DoodEntityRenderer.TEXTURES[0];

    public DoodEntity(World world) {
        super(WWEntities.DOOD, world);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 0.02f, 0.1f, true);
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
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.put("Inventory", this.inventory.toNbtList());
    }

    @Override
    public Inventory getInventory() {
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
        System.out.println(this.currentTexture);
        System.out.println(this.chewingTicks);
        System.out.println(this.chewing);
        System.out.println(this.spitting);

        ItemStack stack = this.getInventory().getStack(0);

        if (this.chewingTicks > 0) {
            this.chewingTicks--;
        } else {
            ItemScatterer.spawn(world, this, inventory);
            stack.decrement(stack.getCount());
        }

        if (this.chewingTicks > 15) {
            chewing = true;
        } else if (this.chewingTicks > 0) {
            spitting = true;
        } else {
            chewing = false;
            spitting = false;
        }

        if (this.spitting) {
            this.currentTexture = DoodEntityRenderer.TEXTURES[2];
        } else if (!this.chewing) {
            this.currentTexture = DoodEntityRenderer.TEXTURES[0];
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
                player.getStackInHand(hand).decrement(1);
                if (this.canInsertIntoInventory(output.getDefaultStack())) this.addItem(output.getDefaultStack());
                this.chewingTicks = 100 + random.nextInt(10);
                this.currentTexture = DoodEntityRenderer.TEXTURES[1];
            }
        });
        return super.interactMob(player, hand);
    }
}
