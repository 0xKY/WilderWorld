package me.kaloyankys.wilderworld.util.interfaces;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.particle.DefaultParticleType;

public interface FlavourSet {
    StatusEffect getEffect();

    DefaultParticleType getParticle();
}
