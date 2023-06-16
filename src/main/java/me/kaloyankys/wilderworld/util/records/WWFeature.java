package me.kaloyankys.wilderworld.util.records;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.function.Predicate;

public record WWFeature(String id, FeatureConfig featureConfig, GenerationStep.Feature step, Predicate<BiomeSelectionContext> biomes, ConfiguredFeature<FeatureConfig, Feature<FeatureConfig>> configuredFeature, PlacedFeature placedFeature) {
}
