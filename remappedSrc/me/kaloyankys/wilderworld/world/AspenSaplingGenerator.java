package me.kaloyankys.wilderworld.world;

import me.kaloyankys.wilderworld.init.WWBiomeModifications;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import org.jetbrains.annotations.Nullable;

public class AspenSaplingGenerator extends LargeTreeSaplingGenerator {
    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
        return null;
    }

    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bl) {
        return null;
    }
   /* @Nullable
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getLargeTreeFeature(net.minecraft.util.math.random.Random random) {
        return RegistryEntry.of(new ConfiguredFeature<>(Feature.TREE, WWBiomeModifications.ASPEN_TREE_LARGE));
    }

    @Nullable
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(net.minecraft.util.math.random.Random random, boolean bl) {
        return RegistryEntry.of(new ConfiguredFeature<>(Feature.TREE, WWBiomeModifications.ASPEN_TREE.featureConfig()));
    }*/
}
