package me.kaloyankys.wilderworld.init;

import com.mojang.serialization.Codec;
import me.kaloyankys.wilderworld.mixin.FoliagePlacerTypeInvoker;
import me.kaloyankys.wilderworld.world.AspenFoliagePlacer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class WWFoliagePlacers {
    //public static final FoliagePlacerType<AspenFoliagePlacer> ASPEN = register("aspen_foliage_placer", AspenFoliagePlacer.CODEC);

    private static <P extends FoliagePlacer> FoliagePlacerType<P> register(String string, Codec<P> codec) {
        return Registry.register(Registries.FOLIAGE_PLACER_TYPE, string, FoliagePlacerTypeInvoker.register(codec));
    }
}
