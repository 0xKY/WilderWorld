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
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.HeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.placer.DoublePlantPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.MegaJungleTrunkPlacer;

import java.util.Random;

@SuppressWarnings({"deprecation", "unused"})
public class WWBiomeModifications {
    private static final Random RANDOM = new Random();

    public static final ConfiguredFeature<?, ?> BIRD_OF_PARADISE_PATCH = registerFF("birds_of_paradise", Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder
            (new SimpleBlockStateProvider(WWBlocks.BIRD_OF_PARADISE.getDefaultState()), DoublePlantPlacer.INSTANCE)).tries(32).build()));

    public static final ConfiguredFeature<?, ?> CHAMOMILE_PATCH = registerFF("chamomiles", Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder
            (new SimpleBlockStateProvider(WWBlocks.CHAMOMILE.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(32).build()));

    public static final ConfiguredFeature<?, ?> RAGING_VIOLET_PATCH = registerFF("violets", Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder
            (new SimpleBlockStateProvider(WWBlocks.RAGING_VIOLET.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(32).build()));

    public static final ConfiguredFeature<?, ?> PHOSPHOSHOOTS_PATCH = registerFF("phosphoshoots", Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder
            (new SimpleBlockStateProvider(WWBlocks.PHOSPHOSHOOTS.getDefaultState()), SimpleBlockPlacer.INSTANCE)).
            spreadY(4).spreadZ(6).spreadX(8).tries(32).build()));

    public static final ConfiguredFeature<?, ?> SHELFSHROOMS = registerFF("shelfshrooms", Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder
            (new SimpleBlockStateProvider(WWBlocks.SHELFSHROOM.getDefaultState()), SimpleBlockPlacer.INSTANCE))
            .spreadY(1).spreadZ(2).spreadX(2).tries(32).build()));

    public static final ConfiguredFeature<?, ?> ASPEN_BIRCH_TREE = registerFF("aspen_birch_tree", Feature.TREE.configure(new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()),
                    new MegaJungleTrunkPlacer(11, 3, 12),
                    new SimpleBlockStateProvider(WWBlocks.ASPEN_LEAVES.getDefaultState()),
                    new SimpleBlockStateProvider(Blocks.BIRCH_SAPLING.getDefaultState()),
                    new BlobFoliagePlacer(
                            UniformIntProvider.create(3, 4),
                            UniformIntProvider.create(3, 4),
                            6),
                    new TwoLayersFeatureSize(12, 0, 10)).decorators(ImmutableList.of(
                            new BeehiveTreeDecorator(0.5f), new ShelfshroomTreeDecorator()))
                    .build())
            .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
  .spreadHorizontally()
  .applyChance(1));

    public static final ConfiguredFeature<?, ?> WISTERIA_TREE = registerFF("wisteria_tree", Feature.TREE.configure(new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(WWBlocks.WISTERIA.LOG.getDefaultState()),
                    new LargeOakTrunkPlacer(3, 11, 0),
                    new SimpleBlockStateProvider(WWBlocks.WISTERIA.LEAVES.getDefaultState()),
                    new SimpleBlockStateProvider(WWBlocks.WISTERIA.SAPLING.getDefaultState()),
                    new RandomSpreadFoliagePlacer(
                            ConstantIntProvider.create(3),
                            ConstantIntProvider.create(0),
                            ConstantIntProvider.create(2), (50 + RANDOM.nextInt(5))),
                    new TwoLayersFeatureSize(3, 0, 2)).decorators(ImmutableList.of(
                            new BeehiveTreeDecorator(0.5f), new TrunkVineTreeDecorator()))
                    .build())
            .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
            .spreadHorizontally()
            .applyChance(1));

    public WWBiomeModifications() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), SpawnGroup.AMBIENT, WWEntities.BUTTERFLY,
                20, 1, 4);
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> registerFF(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION,
                RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier("wilderworld", id)));
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier("wilderworld", id)).getValue(), configuredFeature);
    }

    private static DataPool.Builder<BlockState> pool() {
        return DataPool.builder();
    }
}
