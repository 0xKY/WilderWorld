package me.kaloyankys.wilderworld.init;

import net.minecraft.util.Identifier;

public final class IdBuilder {

    public static Identifier mod(String path) {
        return new Identifier("wilderworld", path);
    }

}

