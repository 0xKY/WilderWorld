package me.kaloyankys.wilderworld.util.interfaces;

import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.List;

@FunctionalInterface
public interface FlavourSet {
    List<StatusEffectInstance> getEffects();
}
