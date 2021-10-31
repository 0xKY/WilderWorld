package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.init.WWPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("RETURN"), method = "applyClimbingSpeed", cancellable = true)
    private void isClimbing(Vec3d motion, CallbackInfoReturnable<Vec3d> cir) {
        if (((LivingEntity) ((Entity) this)).hasStatusEffect(WWPotions.SHELF_SENSE_EFFECT) && this.horizontalCollision) {
            cir.setReturnValue(new Vec3d(0.0, 0.2, 0.0));
            this.setNoGravity(true);
        } else {
            this.setNoGravity(false);
        }
    }
}

