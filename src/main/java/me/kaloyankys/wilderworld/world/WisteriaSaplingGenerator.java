package me.kaloyankys.wilderworld.world;

import me.kaloyankys.wilderworld.init.WWBiomeModifications;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

public class WisteriaSaplingGenerator extends SaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return RegistryEntry.of(new ConfiguredFeature<>(Feature.TREE, WWBiomeModifications.WISTERIA_TREE));
    }
}
