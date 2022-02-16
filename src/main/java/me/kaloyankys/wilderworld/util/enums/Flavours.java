package me.kaloyankys.wilderworld.util.enums;

import me.kaloyankys.wilderworld.init.WWParticles;
import me.kaloyankys.wilderworld.util.interfaces.FlavourSet;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.DefaultParticleType;

import java.util.List;

public enum Flavours implements FlavourSet {
    /**
     * Custom enums for flavours must also implement {@link me.kaloyankys.wilderworld.util.interfaces.FlavourSet}
     */
    CHOCOLATE(List.of(new StatusEffectInstance(StatusEffects.SPEED, 400, 0)), WWParticles.CHOCOLATE_DRIP),
    SWEET_BERRY(List.of(new StatusEffectInstance(StatusEffects.SPEED, 400, 0)), WWParticles.SWEETBERRY_DRIP),
    MINT(List.of(new StatusEffectInstance(StatusEffects.SPEED, 400, 0)), WWParticles.MINT_DRIP);

    public List<StatusEffectInstance> statusEffects;
    public DefaultParticleType particleType;

    Flavours(List<StatusEffectInstance> effect, DefaultParticleType particleType) {
        this.statusEffects = effect;
        this.particleType = particleType;
    }


    @Override
    public List<StatusEffectInstance> getEffects() {
        return statusEffects;
    }

    @Override
    public DefaultParticleType getParticle() {
        return particleType;
    }
}
