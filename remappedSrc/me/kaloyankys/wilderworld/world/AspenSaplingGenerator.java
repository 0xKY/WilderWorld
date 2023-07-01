package me.kaloyankys.wilderworld.world;

import me.kaloyankys.wilderworld.init.WWFeatures;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class AspenSaplingGenerator extends LargeTreeSaplingGenerator {
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier("wilderworld", WWFeatures.ASPEN_TREE_LARGE.id()));
    }

    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bl) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier("wilderworld", WWFeatures.ASPEN_TREE.id()));
    }
}
