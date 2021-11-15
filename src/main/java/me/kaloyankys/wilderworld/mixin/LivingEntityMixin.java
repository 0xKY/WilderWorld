package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.init.WWParticles;
import me.kaloyankys.wilderworld.init.WWPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public float bodyYaw;

    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("RETURN"), method = "applyClimbingSpeed", cancellable = true)
    private void isClimbing(Vec3d motion, CallbackInfoReturnable<Vec3d> cir) {
        if (((LivingEntity) ((Entity) this)).hasStatusEffect(WWPotions.SHELF_SENSE_EFFECT) && this.horizontalCollision) {
            cir.setReturnValue(new Vec3d(0.0, 0.1, 0.0));
            this.setNoGravity(true);
        } else {
            this.setNoGravity(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    private void tick(CallbackInfo ci) {
        Random random = new Random();
        if (((Entity) this) instanceof PlayerEntity player && !player.isCreative()) {
            if (player.getArmor() < 6) {
                if (this.getEntityWorld().getBiome(this.getBlockPos()).getCategory() == Biome.Category.ICY) {
                    if (random.nextInt(25) == 0) {
                        for (int i = (random.nextInt(8) + 1) - player.getArmor(); i < 12; i++) {
                            world.addParticle(WWParticles.STEAM, true,
                                    this.getX() - (double) (this.getWidth() + 0.5F) * 0.5D * (double) MathHelper.sin(this.bodyYaw * 0.017453292F),
                                    this.getEyeY() - 0.10000000149011612D,
                                    this.getZ() + (double) (this.getWidth() + 0.5F) * 0.5D * (double) MathHelper.cos(
                                            this.bodyYaw * 0.017453292F), this.getVelocity().x, -0.00000001F, this.getVelocity().z);
                            this.setFrozenTicks(this.getFrozenTicks() + i);
                        }
                    }
                }
            }
        }
    }
}

