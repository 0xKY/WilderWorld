package me.kaloyankys.wilderworld.world;

import me.kaloyankys.wilderworld.init.WWBiomeModifications;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;

public class WisteriaSaplingGenerator extends SaplingGenerator {

    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
        return (ConfiguredFeature<TreeFeatureConfig, ?>) WWBiomeModifications.WISTERIA_TREE;
    }
}
