package me.kaloyankys.wilderworld.statuseffect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CoffeeStatusEffect extends StatusEffect {
    protected CoffeeStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        entity.sidewaysSpeed += amplifier / 10.0F;
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        Random random = new Random();
        if (!target.getEntityWorld().isClient) {
            target.getStatusEffects().forEach((effect) -> {
                if (random.nextInt(5 - amplifier) == 0) {
                    for (int i = 0; i < amplifier; i++) {
                        target.removeStatusEffect(effect.getEffectType());
                        if (attacker instanceof LivingEntity le && random.nextInt(5 - amplifier) == 0) {
                            le.addStatusEffect(effect);
                        }
                    }
                }
            });
        }
    }
}
