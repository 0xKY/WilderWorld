package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.init.WWParticles;
import me.kaloyankys.wilderworld.init.WWPotions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public float bodyYaw;

    @Shadow
    public abstract Collection<StatusEffectInstance> getStatusEffects();

    @Shadow
    public @Nullable
    abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("RETURN"), method = "isClimbing", cancellable = true)
    private void isClimbing(CallbackInfoReturnable<Boolean> cir) {
        if (this.getStatusEffect(WWPotions.SHELF_SENSE_EFFECT) != null && this.horizontalCollision) {
            cir.setReturnValue(true);
        }
    }

    /* @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo ci) {
        Random random = new Random();
        if (((LivingEntity) (Entity) this) instanceof PlayerEntity player) {
            if (!player.isCreative()) {
                if (player.getArmor() < 6 && this.getEntityWorld().getBiome(this.getBlockPos()).getCategory() == Biome.Category.ICY) {
                    if (random.nextInt(25) == 0) {
                        for (int i = (random.nextInt(8) + 1) - player.getArmor(); i < 12; i++) {
                            this.freezeParticles();
                            this.setFrozenTicks(this.getFrozenTicks() + i);
                        }
                    }
                }
            }
        }
    } */

    private void freezeParticles() {
        world.addParticle(WWParticles.STEAM, true,
                this.getX() - (double) (this.getWidth() + 0.5F) * 0.5D * (double) MathHelper.sin(this.bodyYaw * 0.017453292F),
                this.getEyeY() - 0.10000000149011612D,
                this.getZ() + (double) (this.getWidth() + 0.5F) * 0.5D * (double) MathHelper.cos(
                        this.bodyYaw * 0.017453292F), this.getVelocity().x, -0.00000001F, this.getVelocity().z);
    }
}

