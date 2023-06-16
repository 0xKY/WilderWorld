package me.kaloyankys.wilderworld.init;

import me.kaloyankys.wilderworld.world.WWFeature;
import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class WWFeatures {
    public static final List<WWFeature> FEATURES = new ArrayList<>();

    public static final WWFeature ASPEN_TREE = createFeature("aspen_tree",
            new ProbabilityConfig(0.1f),
            GenerationStep.Feature.VEGETAL_DECORATION,
            BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST),
            Feature.BAMBOO,
            List.of(
                    NoiseBasedCountPlacementModifier.of(40, 1, 0.1),
                    HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(90), YOffset.aboveBottom(180)),
                    SquarePlacementModifier.of(),
                    PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING),
                    BiomePlacementModifier.of()));

    public static final WWFeature ASPEN_TREE_LARGE = createFeature("aspen_tree_large",
            new ProbabilityConfig(0.1f),
            GenerationStep.Feature.VEGETAL_DECORATION,
            BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST),
            Feature.BAMBOO,
            List.of(
                    NoiseBasedCountPlacementModifier.of(40, 1, 0.1),
                    HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(90), YOffset.aboveBottom(180)),
                    SquarePlacementModifier.of(),
                    PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING),
                    BiomePlacementModifier.of()));

    public static final WWFeature WISTERIA_TREE = createFeature("wisteria_tree",
            new ProbabilityConfig(0.1f),
            GenerationStep.Feature.VEGETAL_DECORATION,
            BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST),
            Feature.BAMBOO,
            List.of(
                    NoiseBasedCountPlacementModifier.of(40, 1, 0.1),
                    HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(90), YOffset.aboveBottom(180)),
                    SquarePlacementModifier.of(),
                    PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING),
                    BiomePlacementModifier.of()));

    public static final WWFeature FF_FLOWERS = createFeature("ff_flowers",
            new ProbabilityConfig(0.1f),
            GenerationStep.Feature.VEGETAL_DECORATION,
            BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST),
            Feature.BAMBOO,
            List.of(
                    NoiseBasedCountPlacementModifier.of(40, 1, 0.1),
                    HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom(90), YOffset.aboveBottom(180)),
                    SquarePlacementModifier.of(),
                    PlacedFeatures.wouldSurvive(Blocks.OAK_SAPLING),
                    BiomePlacementModifier.of()));



    public static void addFeatures() {
        BiomeModification biomeModification = BiomeModifications.create(new Identifier("wilderworld", "biome_additions"));
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), SpawnGroup.AMBIENT, WWEntities.BUTTERFLY,
                10, 1, 3);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS), SpawnGroup.AMBIENT, WWEntities.DOOD,
                10, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS), SpawnGroup.WATER_CREATURE, WWEntities.GEYSER_STREAM,
                10, 1, 10);

        FEATURES.forEach((wwFeature -> biomeModification.add(ModificationPhase.ADDITIONS, wwFeature.biomes(), modifier(Placed.FEATURES.get(wwFeature.id()), wwFeature.step(), true))));
    }

    private static WWFeature createFeature(String id, FeatureConfig featureConfig, GenerationStep.Feature step, Predicate<BiomeSelectionContext> biomes, Feature<? extends FeatureConfig> feature, List<PlacementModifier> placementModifiers) {
        WWFeature cfeature = new WWFeature(id, featureConfig, step, biomes,
                Configured.add(id, (Feature<FeatureConfig>) feature, featureConfig),
                Placed.add(id, (Feature<FeatureConfig>) feature, featureConfig, placementModifiers));
        WWFeatures.FEATURES.add(cfeature);
        return cfeature;
    }

    private static WWFeature createFeatureNoGen(String id, FeatureConfig featureConfig, GenerationStep.Feature step, Predicate<BiomeSelectionContext> biomes, Feature<? extends FeatureConfig> feature, List<PlacementModifier> placementModifiers) {
        WWFeature cfeature = new WWFeature(id, featureConfig, step, biomes,
                Configured.add(id, (Feature<FeatureConfig>) feature, featureConfig),
                Placed.add(id, (Feature<FeatureConfig>) feature, featureConfig, placementModifiers));
        return cfeature;
    }


    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> modifier(RegistryKey<PlacedFeature> feature, GenerationStep.Feature step, boolean shouldAdd) {
        return (biomeSelectionContext, biomeModificationContext) -> {
            if (shouldAdd) {
                biomeModificationContext.getGenerationSettings().addFeature(step, feature);
            }
        };
    }

    public static class Placed {
        public static final HashMap<String, RegistryKey<PlacedFeature>> FEATURES = new HashMap<>();

        public static PlacedFeature add(String id, Feature<FeatureConfig> feature, FeatureConfig featureConfig, List<PlacementModifier> placementModifiers) {
            FEATURES.put(id, RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("wilderworld", id)));
            return new PlacedFeature(RegistryEntry.of(Configured.create(feature,featureConfig)), placementModifiers);
        }
    }

    public static class Configured {
        public static final HashMap<RegistryKey<ConfiguredFeature<?, ?>>, String> FEATURES = new HashMap<>();

        public static ConfiguredFeature<FeatureConfig, Feature<FeatureConfig>> add(String id, Feature<FeatureConfig> feature, FeatureConfig featureConfig) {
            FEATURES.put(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier("wilderworld", id)), id);
            return new ConfiguredFeature<>(feature, featureConfig);
        }

        public static ConfiguredFeature<FeatureConfig, Feature<FeatureConfig>> create(Feature<FeatureConfig> feature, FeatureConfig featureConfig) {
            return new ConfiguredFeature<>(feature, featureConfig);
        }
    }
}
