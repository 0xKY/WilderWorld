package me.kaloyankys.wilderworld.init;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WWSounds {
    public static final SoundEvent BUTTERFLY_AMBIENT = register("butterfly_ambient");
    public static final SoundEvent BUTTERFLY_HURT = register("butterfly_hurt");
    public static final SoundEvent BUTTERFLY_DEATH = register("butterfly_death");
    public static final SoundEvent TAMBURA_SPRUCE = register("tambura_spruce");
    public static final SoundEvent TAMBURA_WISTERIA = register("tambura_wisteria");
    public static final SoundEvent TAMBURA_EBONY = register("tambura_ebony");


    private static SoundEvent register(String id) {
        SoundEvent soundEvent = new SoundEvent(new Identifier("wilderworld", id));
        Registry.register(Registry.SOUND_EVENT, new Identifier("wilderworld", id), new SoundEvent(new Identifier("wilderworld", id)));
        return soundEvent;
    }
}
