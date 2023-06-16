package me.kaloyankys.wilderworld.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class WWSounds {
    public static final SoundEvent BUTTERFLY_AMBIENT = register("butterfly_ambient");
    public static final SoundEvent BUTTERFLY_HURT = register("butterfly_hurt");
    public static final SoundEvent BUTTERFLY_DEATH = register("butterfly_death");
    public static final SoundEvent TAMBURA_SPRUCE = register("tambura_spruce");
    public static final SoundEvent TAMBURA_WISTERIA = register("tambura_wisteria");
    public static final SoundEvent TAMBURA_EBONY = register("tambura_ebony");


    private static SoundEvent register(String id) {
        SoundEvent soundEvent = SoundEvent.of(new Identifier("wilderworld", id));
        Registry.register(Registries.SOUND_EVENT, new Identifier("wilderworld", id), soundEvent);
        return soundEvent;
    }
}
