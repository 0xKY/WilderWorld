package me.kaloyankys.wilderworld.world;

import me.kaloyankys.wilderworld.init.WWBiomeModifications;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

import java.util.Random;

public class AspenSaplingGenerator extends LargeTreeSaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
        return RegistryEntry.of(new ConfiguredFeature<>(Feature.TREE, WWBiomeModifications.ASPEN_BIRCH_TREE));
    }

    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return TreeConfiguredFeatures.BIRCH;
    }
}
