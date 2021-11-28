package me.kaloyankys.wilderworld.init;

import com.google.common.collect.ImmutableList;
import me.kaloyankys.wilderworld.world.ShelfshroomTreeDecorator;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.MegaJungleTrunkPlacer;

import java.util.List;
import java.util.OptionalInt;
import java.util.Random;

@SuppressWarnings("unused")
public class WWBiomeModifications {
    private static final Random RANDOM = new Random();
    public static final PlacementModifier NOT_IN_SURFACE_WATER_MODIFIER = SurfaceWaterDepthFilterPlacementModifier.of(0);

    public static final ConfiguredFeature<?, ?> BIRD_OF_PARADISE_PATCH = registerFF("birds_of_paradise", Feature.RANDOM_PATCH.configure(
            ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    BlockStateProvider.of(WWBlocks.BIRD_OF_PARADISE))), List.of(Blocks.GRASS_BLOCK))));

    public static final ConfiguredFeature<?, ?> CHAMOMILE_PATCH = registerFF("chamomiles", Feature.RANDOM_PATCH.configure(
            ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    BlockStateProvider.of(WWBlocks.CHAMOMILE))), List.of(Blocks.GRASS_BLOCK))));

    public static final ConfiguredFeature<?, ?> RAGING_VIOLET_PATCH = registerFF("raging_violets", Feature.RANDOM_PATCH.configure(
            ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    BlockStateProvider.of(WWBlocks.RAGING_VIOLET))), List.of(Blocks.GRASS_BLOCK))));

    public static final ConfiguredFeature<?, ?> PHOSPHOSHOOT_PATCH = registerFF("phosphoshoots", Feature.RANDOM_PATCH.configure(
            ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    BlockStateProvider.of(WWBlocks.PHOSPHOSHOOTS))), List.of(Blocks.GRASS_BLOCK))));

    public static final ConfiguredFeature<?, ?> SHELFSHROOMS = registerFF("shelfshrooms", Feature.RANDOM_PATCH.configure(
            ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(
                    BlockStateProvider.of(WWBlocks.SHELFSHROOM))), List.of(Blocks.GRASS_BLOCK))));

    public static final ConfiguredFeature<TreeFeatureConfig, ?> ASPEN_BIRCH_TREE = registerFFTree("aspen_birch_tree", Feature.TREE.configure(new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.BIRCH_LOG), new MegaJungleTrunkPlacer(11, 3, 12), BlockStateProvider.of(WWBlocks.ASPEN_LEAVES),
            new BlobFoliagePlacer(
                    UniformIntProvider.create(3, 4),
                    UniformIntProvider.create(3, 4),
                    6),
            new ThreeLayersFeatureSize(12, 12, 4, 9, 12, OptionalInt.empty()))
            .decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f), new ShelfshroomTreeDecorator()))
            .ignoreVines()
            .build()));

    public static final ConfiguredFeature<TreeFeatureConfig, ?> WISTERIA_TREE = registerFFTree("wisteria_tree", Feature.TREE.configure(new TreeFeatureConfig.Builder(
            BlockStateProvider.of(WWBlocks.WISTERIA.LOG), new LargeOakTrunkPlacer(3, 14, 0), BlockStateProvider.of(WWBlocks.WISTERIA.LEAVES),
            new RandomSpreadFoliagePlacer(
                    ConstantIntProvider.create(3),
                    ConstantIntProvider.create(0),
                    ConstantIntProvider.create(2), (50 + RANDOM.nextInt(5))),
            new ThreeLayersFeatureSize(6, 6, 3, 4, 5, OptionalInt.empty()))
            .decorators(ImmutableList.of(new BeehiveTreeDecorator(0.5f), new TrunkVineTreeDecorator()))
            .ignoreVines()
            .build()));

    public WWBiomeModifications() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), SpawnGroup.AMBIENT, WWEntities.BUTTERFLY,
                20, 1, 4);
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> registerFF(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("wilderworld", id)));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier("wilderworld", id)).getValue(), configuredFeature.withPlacement(NoiseThresholdCountPlacementModifier
                        .of(-0.1, 0, 1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP,
                BiomePlacementModifier.of()));
        return null;
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> registerFFTree(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("wilderworld", id)));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier("wilderworld", id)).getValue(), configuredFeature.withPlacement(SquarePlacementModifier
                        .of(), NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP,
                BlockFilterPlacementModifier
                        .of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN)), BiomePlacementModifier.of()));
        return null;
    }

    private static DataPool.Builder<BlockState> pool() {
        return DataPool.builder();
    }
}
