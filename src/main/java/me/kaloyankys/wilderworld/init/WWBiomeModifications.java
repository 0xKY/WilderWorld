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
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.JungleFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;

import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class WWBiomeModifications {
    private static final Random RANDOM = new Random();
    public static final PlacementModifier NOT_IN_SURFACE_WATER_MODIFIER = SurfaceWaterDepthFilterPlacementModifier.of(0);

    public static final ConfiguredFeature<SimpleRandomFeatureConfig, ?> FF_CUSTOM_FLOWERS = registerFF("ff_custom_flowers", Feature.SIMPLE_RANDOM_SELECTOR.configure(
            new SimpleRandomFeatureConfig(List.of(() ->
                    Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(WWBlocks.BIRD_OF_PARADISE))))).withPlacement(), () ->
                    Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(WWBlocks.CHAMOMILE))))).withPlacement(), () ->
                    Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(WWBlocks.RAGING_VIOLET))))).withPlacement(), () ->
                    Feature.NO_BONEMEAL_FLOWER.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(WWBlocks.PHOSPHOSHOOTS))))).withPlacement()))));

    public static final ConfiguredFeature<TreeFeatureConfig, ?> ASPEN_BIRCH_TREE = registerFFTree("aspen_birch_tree", Feature.TREE.configure(new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.BIRCH_LOG), new LargeForkingTrunkPlacer(11, 6, 12), BlockStateProvider.of(WWBlocks.ASPEN_LEAVES),
            new JungleFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2),
            new TwoLayersFeatureSize(1, 1, 2))
            .decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f), new ShelfshroomTreeDecorator()))
            .ignoreVines()
            .build()));

    public static final ConfiguredFeature<TreeFeatureConfig, ?> WISTERIA_TREE = registerFFTree("wisteria_tree", Feature.TREE.configure(new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WWBlocks.WISTERIA.LOG), new LargeOakTrunkPlacer(4, 14, 2), BlockStateProvider.of(WWBlocks.WISTERIA.LEAVES),
            new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), RANDOM.nextInt(50) + 30),
            new TwoLayersFeatureSize(1, 1, 2))
            .decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f), new TrunkVineTreeDecorator()))
            .forceDirt()
            .build()));

    public static final ConfiguredFeature<SimpleRandomFeatureConfig, ?> EBONY_BUSHES = registerIcy("ebony_bushes", Feature.SIMPLE_RANDOM_SELECTOR.configure(
            new SimpleRandomFeatureConfig(List.of(() ->
                    Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(WWBlocks.EBONY_BUSH))))).withPlacement(), () ->
                    Feature.RANDOM_PATCH.configure(ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(
                            new SimpleBlockFeatureConfig(BlockStateProvider.of(WWBlocks.EBONY_BUSH_TALL))))).withPlacement()))));

    public WWBiomeModifications() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), SpawnGroup.AMBIENT, WWEntities.BUTTERFLY,
                30, 3, 6);
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> registerFF(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("wilderworld", id)));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier("wilderworld", id)).getValue(), configuredFeature.withPlacement(RarityFilterPlacementModifier.of(7),
                SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, CountPlacementModifier
                        .of(ClampedIntProvider.create(UniformIntProvider.create(-1, 5), 0, 5)), BiomePlacementModifier.of()));
        return configuredFeature;
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> registerFFTree(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.TOP_LAYER_MODIFICATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("wilderworld", id)));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier("wilderworld", id)).getValue(), configuredFeature.withPlacement(VegetationPlacedFeatures
                .modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.1f, 3), Blocks.OAK_SAPLING)));
        return configuredFeature;
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> registerIcy(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("wilderworld", id)));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier("wilderworld", id)).getValue(), configuredFeature.withPlacement(RarityFilterPlacementModifier.of(7),
                SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, CountPlacementModifier
                        .of(ClampedIntProvider.create(UniformIntProvider.create(-1, 5), 0, 5)), BiomePlacementModifier.of()));
        return configuredFeature;
    }

    private static DataPool.Builder<BlockState> pool() {
        return DataPool.builder();
    }
}
