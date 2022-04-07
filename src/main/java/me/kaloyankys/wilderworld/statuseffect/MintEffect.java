package me.kaloyankys.wilderworld.statuseffect;

import me.kaloyankys.wilderworld.init.WWPotions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class MintEffect extends StatusEffect {
    public MintEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        List<StatusEffectInstance> temp = new ArrayList<>(entity.getStatusEffects());
        temp.forEach((sei) -> {
            if (sei.getEffectType() != WWPotions.MINT_EFFECT) {
                entity.removeStatusEffect(sei.getEffectType());
                entity.addStatusEffect(new StatusEffectInstance(
                        sei.getEffectType(), sei.getDuration() * (amplifier + 2), sei.getAmplifier() + (amplifier + 1)));
            }
        });
        super.onApplied(entity, attributes, amplifier);
    }
}
