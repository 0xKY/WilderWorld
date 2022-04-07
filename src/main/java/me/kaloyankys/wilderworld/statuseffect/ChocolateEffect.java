package me.kaloyankys.wilderworld.statuseffect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ChocolateEffect extends StatusEffect {
    public ChocolateEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean isInstant() {
        return false;
    }
}
