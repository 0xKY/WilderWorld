package me.kaloyankys.wilderworld.util.interfaces;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.DefaultParticleType;

import java.util.List;

public interface FlavourSet {
    List<StatusEffectInstance> getEffects();

    DefaultParticleType getParticle();
}
