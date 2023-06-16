package me.kaloyankys.wilderworld.block;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.util.SignType;

import java.util.Set;

public class WWSignType extends SignType {
    private static final Set<SignType> VALUES = new ObjectArraySet();
    public WWSignType(String name) {
        super(name);
    }

    public static SignType register(SignType signType) {
        VALUES.add(signType);
        return signType;
    }
}
