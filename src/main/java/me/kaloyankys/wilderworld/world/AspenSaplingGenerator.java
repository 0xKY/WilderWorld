package me.kaloyankys.wilderworld.world;

import me.kaloyankys.wilderworld.init.WWBiomeModifications;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class AspenSaplingGenerator extends LargeTreeSaplingGenerator {
    @Nullable
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getLargeTreeFeature(net.minecraft.util.math.random.Random random) {
        return TreeConfiguredFeatures.BIRCH;
    }

    @Nullable
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(net.minecraft.util.math.random.Random random, boolean bl) {
        return RegistryEntry.of(new ConfiguredFeature<>(Feature.TREE, WWBiomeModifications.ASPEN_BIRCH_TREE));
    }
}
