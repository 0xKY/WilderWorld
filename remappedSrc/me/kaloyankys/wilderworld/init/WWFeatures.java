package me.kaloyankys.wilderworld.init;

import me.kaloyankys.wilderworld.util.records.WWFeature;
import net.fabricmc.fabric.api.biome.v1.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public class WWFeatures {
    public static final List<WWFeature> FEATURES = new ArrayList<>();

    public static void registerFeatures() {
        BiomeModification biomeModification = BiomeModifications.create(new Identifier("wilderworld", "biome_additions"));
        FEATURES.forEach((feature -> biomeModification.add(ModificationPhase.ADDITIONS, feature.biomes(), modifier(Placed.lookup(feature.id()), feature.step(), true))));
    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> modifier(RegistryKey<PlacedFeature> feature, GenerationStep.Feature step, boolean shouldAdd) {
        return (biomeSelectionContext, biomeModificationContext) -> {
            if (shouldAdd) {
                biomeModificationContext.getGenerationSettings().addFeature(step, feature);
            }
        };
    }



    public static class Placed {
        public static final HashMap<RegistryKey<PlacedFeature>, String> FEATURES = new HashMap<>();

        public static PlacedFeature add(String id, Feature<FeatureConfig> feature, FeatureConfig featureConfig, List<PlacementModifier> placementModifiers) {
            FEATURES.put(RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("wilderworld", id)), id);
            return new PlacedFeature(RegistryEntry.of(Configured.create(feature,featureConfig)), placementModifiers);
        }

        public static RegistryKey<PlacedFeature> lookup(String name) {
            for (RegistryKey<PlacedFeature> key : Placed.FEATURES.keySet()) {
                for (String id : Placed.FEATURES.values()) {
                    if (Objects.equals(id, name)) {
                        return key;
                    }
                }
            } return null;
        }
    }

    public static class Configured {
        public static final HashMap<RegistryKey<ConfiguredFeature<?, ?>>, String> FEATURES = new HashMap<>();

        public static ConfiguredFeature<FeatureConfig, Feature<FeatureConfig>> add(String id, Feature<FeatureConfig> feature, FeatureConfig featureConfig) {
            FEATURES.put(RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier("wilderworld", id)), id);
            return new ConfiguredFeature<>(feature, featureConfig);
        }

        public static RegistryKey<ConfiguredFeature<?, ?>> lookup(String name) {
            for (RegistryKey<ConfiguredFeature<?, ?>> key : Configured.FEATURES.keySet()) {
                for (String id : Configured.FEATURES.values()) {
                    if (Objects.equals(id, name)) {
                        return key;
                    }
                }
            } return null;
        }

        public static ConfiguredFeature<FeatureConfig, Feature<FeatureConfig>> create(Feature<FeatureConfig> feature, FeatureConfig featureConfig) {
            return new ConfiguredFeature<>(feature, featureConfig);
        }
    }
}
