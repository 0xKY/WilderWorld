package me.kaloyankys.wilderworld.util.enums;

import me.kaloyankys.wilderworld.util.interfaces.FlavourSet;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.List;

public enum Flavours implements FlavourSet {
    /**
     * Custom enums for flavours must also implement {@link me.kaloyankys.wilderworld.util.interfaces.FlavourSet}
     */
    CHOCOLATE(List.of(new StatusEffectInstance(StatusEffects.SPEED, 400, 0))),
    SWEET_BERRY(List.of(new StatusEffectInstance(StatusEffects.SPEED, 400, 0))),
    MINT(List.of(new StatusEffectInstance(StatusEffects.SPEED, 400, 0)));

    public List<StatusEffectInstance> statusEffects;

    Flavours(List<StatusEffectInstance> effect) {
        this.statusEffects = effect;
    }


    @Override
    public List<StatusEffectInstance> getEffects() {
        return statusEffects;
    }
}
