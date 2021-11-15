package me.kaloyankys.wilderworld.init;

import me.kaloyankys.wilderworld.client.PublicDefaultParticleType;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WWParticles {

    public static final DefaultParticleType STEAM = register("steam", new PublicDefaultParticleType(true));

    private static DefaultParticleType register(String id, DefaultParticleType particle) {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier("wilderworld", id), particle);
        return particle;
    }
}
