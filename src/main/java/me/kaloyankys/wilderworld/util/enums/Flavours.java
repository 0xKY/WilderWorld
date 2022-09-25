package me.kaloyankys.wilderworld.util.enums;

import me.kaloyankys.wilderworld.init.WWParticles;
import me.kaloyankys.wilderworld.init.WWPotions;
import me.kaloyankys.wilderworld.util.interfaces.FlavourSet;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.DefaultParticleType;

public enum Flavours implements FlavourSet {
    /**
     * Other enums for flavours must also implement {@link me.kaloyankys.wilderworld.util.interfaces.FlavourSet}
     */
    CHOCOLATE(WWPotions.CHOCOLATE_EFFECT, WWParticles.CHOCOLATE_DRIP),
    SWEET_BERRY(StatusEffects.INSTANT_HEALTH, WWParticles.SWEETBERRY_DRIP),
    MINT(WWPotions.MINT_EFFECT, WWParticles.MINT_DRIP);

    public StatusEffect statusEffect;
    public DefaultParticleType particleType;

    Flavours(StatusEffect effect, DefaultParticleType particleType) {
        this.statusEffect = effect;
        this.particleType = particleType;
    }


    @Override
    public StatusEffect getEffect() {
        return statusEffect;
    }

    @Override
    public DefaultParticleType getParticle() {
        return particleType;
    }
}
