package me.kaloyankys.wilderworld.init;

import me.kaloyankys.wilderworld.util.records.WWFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class WWBiomeModifications {
    private static final Random RANDOM = new Random();
    public static final PlacementModifier NOT_IN_SURFACE_WATER_MODIFIER = SurfaceWaterDepthFilterPlacementModifier.of(0);
/*
    public static final RandomPatchFeatureConfig FF_CUSTOM_FLOWERS = registerVegetation("ff_custom_flowers", Feature.FLOWER,
            new RandomPatchFeatureConfig(96, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(new NoiseBlockStateProvider(2345L,
                            new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0D), 0.020833334F,
                            List.of(WWBlocks.GLOWBRUSH.getDefaultState(), WWBlocks.RAGING_VIOLET.getDefaultState(), WWBlocks.BIRD_OF_PARADISE.getDefaultState(),
                                    WWBlocks.CHAMOMILE.getDefaultState()))))));

    public static final RandomPatchFeatureConfig EBONY_BUSHES_SMALL = registerVegetation("ebony_bushes_small", Feature.FLOWER,
            new RandomPatchFeatureConfig(96, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(new NoiseBlockStateProvider(2345L,
                            new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0D), 0.020833334F,
                            List.of(WWBlocks.EBONY_BUSH.getDefaultState()))))));

    public static final RandomPatchFeatureConfig EBONY_BUSHES = registerVegetation("ebony_bushes_tall", Feature.FLOWER,
            new RandomPatchFeatureConfig(96, 6, 2, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(BlockStateProvider.of(WWBlocks.EBONY_BUSH_TALL)))));

    public static final RandomPatchFeatureConfig GLOWGI_PATCH = registerVegetationNoBiome("glowgi_patch", Feature.FLOWER,
            new RandomPatchFeatureConfig(30, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                    new SimpleBlockFeatureConfig(new NoiseBlockStateProvider(2345L,
                            new DoublePerlinNoiseSampler.NoiseParameters(0, 1.0D), 0.020833334F,
                            List.of(WWBlocks.GLOWGI.getDefaultState()))))));

    public static final TreeFeatureConfig ASPEN_TREE_LARGE = registerTree("aspen_tree_large", Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.BIRCH_LOG), new LargeForkingTrunkPlacer(11, 6, 12), BlockStateProvider.of(Blocks.ORANGE_WOOL),
            new JungleFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2),
            new TwoLayersFeatureSize(1, 1, 2))
            .decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f), new ShelfshroomTreeDecorator()))
            .ignoreVines()
            .build());

    public static final TreeFeatureConfig ASPEN_TREE = registerTree("aspen_tree", Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.BIRCH_LOG),
            new UpwardsBranchingTrunkPlacer(4, 1, 9, UniformIntProvider.create(1, 6), 0.5F, UniformIntProvider.create(0, 1),
                    Registry.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)), BlockStateProvider.of(Blocks.YELLOW_WOOL),
            new AspenFoliagePlacer(UniformIntProvider.create(4, 6), UniformIntProvider.create(0, 4), UniformIntProvider.create(1, 2)),
            new TwoLayersFeatureSize(1, 1, 2))
            .decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f), new ShelfshroomTreeDecorator()))
            .ignoreVines()
            .build());


    public static final TreeFeatureConfig WISTERIA_TREE = registerTree("wisteria_tree", Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG), new LargeOakTrunkPlacer(3, 11, 0),
            BlockStateProvider.of(Blocks.AZALEA_LEAVES),
            new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))
            .decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f), new TrunkVineTreeDecorator(), new WisteriaDroopleavesTreeDecorator()))
            .forceDirt()
            .build());

    public static final VegetationPatchFeatureConfig TRAVERTINE_SPRING = registerSpring("travertine_spring", Feature.WATERLOGGED_VEGETATION_PATCH,
            new VegetationPatchFeatureConfig(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.of(WWBlocks.TRAVERTINE),
                    PlacedFeatures.createEntry(RegistryEntry.of(new ConfiguredFeature<>(Feature.RANDOM_PATCH, GLOWGI_PATCH))), VerticalSurfaceType.FLOOR, ConstantIntProvider.create(3),
                    0.8f, 5, 0.1f, UniformIntProvider.create(6, 9), 0.8f));


    public static final VegetationPatchFeatureConfig TRAVERTINE_PEACH_SPRING = registerSpring("travertine_peach_spring", Feature.WATERLOGGED_VEGETATION_PATCH,
            new VegetationPatchFeatureConfig(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.of(WWBlocks.TRAVERTINE_PEACH),
                    PlacedFeatures.createEntry(RegistryEntry.of(new ConfiguredFeature<>(Feature.RANDOM_PATCH, GLOWGI_PATCH))), VerticalSurfaceType.FLOOR, ConstantIntProvider.create(3),
                    0.6f, 3, 0.1f, UniformIntProvider.create(5, 8), 0.7f)); */

    /*public static final WWFeature ASPEN_TREE = createFeature("aspen_tree", new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WWBlocks.ASPEN.LOG),
            new UpwardsBranchingTrunkPlacer(4, 1, 9, UniformIntProvider.create(1, 6), 0.5F, UniformIntProvider.create(0, 1),
                    Registries.BLOCK.getOrCreateEntryList(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)), BlockStateProvider.of(WWBlocks.ASPEN.LEAVES),
            new AspenFoliagePlacer(UniformIntProvider.create(4, 6), UniformIntProvider.create(0, 4), UniformIntProvider.create(1, 2)),
            new TwoLayersFeatureSize(1, 1, 2))
            .decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f), new ShelfshroomTreeDecorator()))
            .ignoreVines()
            .build(), GenerationStep.Feature.VEGETAL_DECORATION, BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), Feature.TREE, VegetationPlacedFeatures
            .modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.1f, 3), Blocks.OAK_SAPLING));*/

    /*public WWBiomeModifications() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), SpawnGroup.AMBIENT, WWEntities.BUTTERFLY,
                30, 3, 6);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS), SpawnGroup.WATER_CREATURE, WWEntities.GEYSER_STREAM,
                10, 1, 10);
    }*/


    /*
    private static <FC extends FeatureConfig> FC registerVegetation(String id, Feature<FC> feature, FC config) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("wilderworld", id)));
        registerVegetation(id, new ConfiguredFeature<>(feature, config));
        return config;
    }

    private static <FC extends FeatureConfig> FC registerTree(String id, Feature<FC> feature, FC config) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.TOP_LAYER_MODIFICATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("wilderworld", id)));
        registerTree(id, new ConfiguredFeature<>(feature, config));
        return config;
    }

    private static <FC extends FeatureConfig> FC registerTree(String id, Feature<FC> feature, FC config) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("wilderworld", id)));
        registerSpring(id, new ConfiguredFeature<>(feature, config), List.of(WWBlocks.TRAVERTINE, WWBlocks.TRAVERTINE_PEACH));
        return config;
    }

    private static <FC extends FeatureConfig> FC registerVegetation(String id, Feature<FC> feature, FC config) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("wilderworld", id)));
        registerVegetation(id, new ConfiguredFeature<>(feature, config));
        return config;
    }

    private static <FC extends FeatureConfig> FC registerSpring(String id, Feature<FC> feature, FC config) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("wilderworld", id)));
        registerSpring(id, new ConfiguredFeature<>(feature, config), List.of(Blocks.DIRT));
        return config;
    }

    private static <FC extends FeatureConfig> void registerVegetation(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        Registry.register(BuiltinRegistries., RegistryKey.of(RegistryKeys.PLACED_FEATURE,
                new Identifier("wilderworld", id)).getValue(), new PlacedFeature(RegistryEntry.of(configuredFeature), List.of(RarityFilterPlacementModifier.of(7),
                SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, CountPlacementModifier
                        .of(ClampedIntProvider.create(UniformIntProvider.create(-1, 5), 0, 5)), BiomePlacementModifier.of())));
    }

    private static <FC extends FeatureConfig> FC registerVegetationNoBiome(String id, Feature<FC> feature, FC config) {
        registerVegetation(id, new ConfiguredFeature<>(feature, config));
        return config;
    }

    private static <FC extends FeatureConfig> void registerTree(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier("wilderworld", id)).getValue(), new PlacedFeature(RegistryEntry.of(configuredFeature), VegetationPlacedFeatures
                .modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.1f, 3), Blocks.OAK_SAPLING)));
    }

    private static <FC extends FeatureConfig> void registerSpring(String id, ConfiguredFeature<FC, ?> configuredFeature, List<Block> baseBlocks) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier("wilderworld", id)).getValue(), new PlacedFeature(RegistryEntry.of(configuredFeature), List.of(
                        CountPlacementModifier.of(62), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE,
                EnvironmentScanPlacementModifier.of(Direction.DOWN,
                        BlockPredicate.matchingBlocks(new Vec3i(2, 2, 2), baseBlocks), BlockPredicate.IS_AIR, 32),
                RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)), BiomePlacementModifier.of())));
    }

    private static <FC extends FeatureConfig> void registerOre(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        Registry.register(BuiltinRegistries.PLACED_FEATURE, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier("wilderworld", id)).getValue(), new PlacedFeature(RegistryEntry.of(configuredFeature), modifiersWithCount(
                        16, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-16), YOffset.fixed(112)))));
    }

    private static DataPool.Builder<BlockState> pool() {
        return DataPool.builder();
    }

    private static List<PlacementModifier> modifiersWithCount(int i, PlacementModifier placementModifier) {
        return modifiers(CountPlacementModifier.of(i), placementModifier);
    }

    private static List<PlacementModifier> modifiers(PlacementModifier placementModifier, PlacementModifier placementModifier2) {
        return List.of(placementModifier, SquarePlacementModifier.of(), placementModifier2, BiomePlacementModifier.of());
    } */
}
