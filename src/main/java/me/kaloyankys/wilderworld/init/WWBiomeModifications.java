package me.kaloyankys.wilderworld.init;

import com.google.common.collect.ImmutableList;
import me.kaloyankys.wilderworld.world.LargeForkingTrunkPlacer;
import me.kaloyankys.wilderworld.world.ShelfshroomTreeDecorator;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.JungleFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.NoiseBlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;

import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class WWBiomeModifications {
    private static final Random RANDOM = new Random();
    public static final PlacementModifier NOT_IN_SURFACE_WATER_MODIFIER = SurfaceWaterDepthFilterPlacementModifier.of(0);

    public static final RandomPatchFeatureConfig FF_CUSTOM_FLOWERS = registerFFVegetation("ff_custom_flowers", Feature.FLOWER,
            new RandomPatchFeatureConfig(96, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(new NoiseBlockStateProvider(2345L,
                            new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0D), 0.020833334F,
                            List.of(WWBlocks.PHOSPHOSHOOTS.getDefaultState(), WWBlocks.RAGING_VIOLET.getDefaultState(), WWBlocks.BIRD_OF_PARADISE.getDefaultState(),
                                    WWBlocks.CHAMOMILE.getDefaultState()))))));

    public static final RandomPatchFeatureConfig EBONY_BUSHES_SMALL = registerIcyVegetation("ebony_bushes_small", Feature.FLOWER,
            new RandomPatchFeatureConfig(96, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(new NoiseBlockStateProvider(2345L,
                            new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0D), 0.020833334F,
                            List.of(WWBlocks.EBONY_BUSH.getDefaultState()))))));

    public static final RandomPatchFeatureConfig EBONY_BUSHES = registerIcyVegetation("ebony_bushes", Feature.FLOWER,
            new RandomPatchFeatureConfig(96, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(BlockStateProvider.of(WWBlocks.EBONY_BUSH_TALL)))));

    public static final TreeFeatureConfig ASPEN_BIRCH_TREE = registerFFTree("aspen_birch_tree", Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.BIRCH_LOG), new LargeForkingTrunkPlacer(11, 6, 12), BlockStateProvider.of(WWBlocks.ASPEN_LEAVES),
            new JungleFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2),
            new TwoLayersFeatureSize(1, 1, 2))
            .decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f), new ShelfshroomTreeDecorator()))
            .ignoreVines()
            .build());

    public static final TreeFeatureConfig WISTERIA_TREE = registerFFTree("wisteria_tree", Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WWBlocks.WISTERIA.LOG), new LargeOakTrunkPlacer(4, 14, 2), BlockStateProvider.of(WWBlocks.WISTERIA.LEAVES),
            new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), RANDOM.nextInt(50) + 30),
            new TwoLayersFeatureSize(1, 1, 2))
            .decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f), new TrunkVineTreeDecorator()))
            .forceDirt()
            .build());

    public WWBiomeModifications() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), SpawnGroup.AMBIENT, WWEntities.BUTTERFLY,
                30, 3, 6);
    }

    private static <FC extends FeatureConfig> FC registerFFVegetation(String id, Feature<FC> feature, FC config) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("wilderworld", id)));
        registerVegetation(id, new ConfiguredFeature<>(feature, config));
        return config;
    }

    private static <FC extends FeatureConfig> FC registerFFTree(String id, Feature<FC> feature, FC config) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.TOP_LAYER_MODIFICATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("wilderworld", id)));
        registerTree(id, new ConfiguredFeature<>(feature, config));
        return config;
    }

    private static <FC extends FeatureConfig> FC registerIcyVegetation(String id, Feature<FC> feature, FC config) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("wilderworld", id)));
        registerVegetation(id, new ConfiguredFeature<>(feature, config));
        return config;
    }

    private static <FC extends FeatureConfig> void registerVegetation(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier("wilderworld", id)).getValue(), new PlacedFeature(RegistryEntry.of(configuredFeature), List.of(RarityFilterPlacementModifier.of(7),
                SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, CountPlacementModifier
                        .of(ClampedIntProvider.create(UniformIntProvider.create(-1, 5), 0, 5)), BiomePlacementModifier.of())));
    }

    private static <FC extends FeatureConfig> void registerTree(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier("wilderworld", id)).getValue(), new PlacedFeature(RegistryEntry.of(configuredFeature), VegetationPlacedFeatures
                .modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.1f, 3), Blocks.OAK_SAPLING)));
    }

    private static DataPool.Builder<BlockState> pool() {
        return DataPool.builder();
    }
}
