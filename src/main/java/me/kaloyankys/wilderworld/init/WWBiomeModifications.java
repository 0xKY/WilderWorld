package me.kaloyankys.wilderworld.init;

import com.google.common.collect.ImmutableList;
import me.kaloyankys.wilderworld.world.ShelfshroomTreeDecorator;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.BushFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.placer.DoublePlantPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.ForkingTrunkPlacer;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;
import net.minecraft.world.gen.trunk.MegaJungleTrunkPlacer;

import java.util.Random;

@SuppressWarnings("deprecation")
public class WWBiomeModifications {
    private static final Random RANDOM = new Random();

    public static final ConfiguredFeature<?, ?> BIRD_OF_PARADISE_PATCH = Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder
            (new SimpleBlockStateProvider(WWBlocks.BIRD_OF_PARADISE.getDefaultState()), DoublePlantPlacer.INSTANCE)).tries(32).build());

    public static final ConfiguredFeature<?, ?> EDELWEISS_PATCH = Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder
            (new SimpleBlockStateProvider(WWBlocks.EDELWEISS.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(32).build());

    public static final ConfiguredFeature<?, ?> RAGING_VIOLET_PATCH = Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder
            (new SimpleBlockStateProvider(WWBlocks.RAGING_VIOLET.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(32).build());

    public static final ConfiguredFeature<?, ?> PHOSPHOSHOOTS_PATCH = Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder
            (new SimpleBlockStateProvider(WWBlocks.PHOSPHOSHOOTS.getDefaultState()), SimpleBlockPlacer.INSTANCE)).
            spreadY(4).spreadZ(6).spreadX(8).tries(32).build());

    public static final ConfiguredFeature<?, ?> SHELFSHROOMS = Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder
            (new SimpleBlockStateProvider(WWBlocks.SHELFSHROOM.getDefaultState()), SimpleBlockPlacer.INSTANCE))
            .spreadY(1).spreadZ(2).spreadX(2).tries(32).build());

    public static final ConfiguredFeature<?, ?> ASPEN_BIRCH_TREE = Feature.TREE.configure(new TreeFeatureConfig.Builder(
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
  .applyChance(1);

    public static final ConfiguredFeature<?, ?> WISTERIA_TREE = Feature.TREE.configure(new TreeFeatureConfig.Builder(
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
            .applyChance(1);

    public static void initModifications() {
        RegistryKey<ConfiguredFeature<?, ?>> birdOfParadisePatch = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier("wilderworld", "bird_of_paradise_patch"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, birdOfParadisePatch.getValue(),  BIRD_OF_PARADISE_PATCH);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, birdOfParadisePatch);

        RegistryKey<ConfiguredFeature<?, ?>> edelweissPatch = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier("wilderworld", "edelweiss_patch"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, edelweissPatch.getValue(),  EDELWEISS_PATCH);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, edelweissPatch);

        RegistryKey<ConfiguredFeature<?, ?>> ragingVioletPatch = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier("wilderworld", "raging_violet_patch"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ragingVioletPatch.getValue(),  RAGING_VIOLET_PATCH);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, ragingVioletPatch);

        RegistryKey<ConfiguredFeature<?, ?>> phosphoshootPatch = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier("wilderworld", "phosphoshoot_patch"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, phosphoshootPatch.getValue(),  PHOSPHOSHOOTS_PATCH);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, phosphoshootPatch);


        RegistryKey<ConfiguredFeature<?, ?>> shelfshrooms = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier("wilderworld", "shelfshrooms"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, shelfshrooms.getValue(),  SHELFSHROOMS);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, shelfshrooms);

        RegistryKey<ConfiguredFeature<?, ?>> aspen = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier("wilderworld", "aspen"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, aspen.getValue(),  ASPEN_BIRCH_TREE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, aspen);

        RegistryKey<ConfiguredFeature<?, ?>> wisteria = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier("wilderworld", "wisteria"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, wisteria.getValue(),  WISTERIA_TREE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, wisteria);
    }

    private static DataPool.Builder<BlockState> pool() {
        return DataPool.builder();
    }
}
