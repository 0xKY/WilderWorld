package me.kaloyankys.wilderworld.init;

import com.google.common.collect.ImmutableList;
import me.kaloyankys.wilderworld.world.ShelfshroomTreeDecorator;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.HeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.placer.DoublePlantPlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

@SuppressWarnings("deprecation")
public class WWBiomeModifications {

    public static final ConfiguredFeature<?, ?> BIRD_OF_PARADISE_PATCH = Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder
            (new SimpleBlockStateProvider(WWBlocks.BIRD_OF_PARADISE.getDefaultState()), DoublePlantPlacer.INSTANCE)).tries(32).build());

    public static final ConfiguredFeature<?, ?> EDELWEISS_PATCH = Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder
            (new SimpleBlockStateProvider(WWBlocks.EDELWEISS.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(32).build());

    public static final ConfiguredFeature<?, ?> RAGING_VIOLET_PATCH = Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder
            (new SimpleBlockStateProvider(WWBlocks.RAGING_VIOLET.getDefaultState()), SimpleBlockPlacer.INSTANCE)).tries(32).build());

    public static final ConfiguredFeature<?, ?> SHELFSHROOMS = Feature.GLOW_LICHEN.configure(new GlowLichenFeatureConfig(
            20, false, true, true, 0.5F, ImmutableList.of(
                    Blocks.OAK_LOG.getDefaultState(), Blocks.BIRCH_LOG.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), Blocks.STONE.getDefaultState()
            )))
            .spreadHorizontally()
            .uniformRange(YOffset.getBottom(), YOffset.fixed(256)).repeat(UniformIntProvider.create(20, 30));

    public static final ConfiguredFeature<?, ?> ASPEN_BIRCH_TREE = Feature.TREE.configure(new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()),
                    new StraightTrunkPlacer(8, 3, 0),
                    new SimpleBlockStateProvider(WWBlocks.ASPEN_LEAVES.getDefaultState()),
                    new SimpleBlockStateProvider(Blocks.BIRCH_SAPLING.getDefaultState()),
                    new SpruceFoliagePlacer(
                            UniformIntProvider.create(4, 6),
                            UniformIntProvider.create(0, 4),
                            UniformIntProvider.create(2, 4)),
                    new TwoLayersFeatureSize(5, 0, 5)).decorators(ImmutableList.of(
                            new BeehiveTreeDecorator(0.5f), new TrunkVineTreeDecorator(), new ShelfshroomTreeDecorator()))
                    .build())
            .decorate(Decorator.HEIGHTMAP.configure(new HeightmapDecoratorConfig(Heightmap.Type.MOTION_BLOCKING)))
  .spreadHorizontally()
  .applyChance(3);


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

        RegistryKey<ConfiguredFeature<?, ?>> shelfshrooms = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier("wilderworld", "shelfshrooms"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, shelfshrooms.getValue(),  SHELFSHROOMS);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, shelfshrooms);

        RegistryKey<ConfiguredFeature<?, ?>> aspen = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY,
                new Identifier("wilderworld", "aspen"));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, aspen.getValue(),  ASPEN_BIRCH_TREE);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST), GenerationStep.Feature.VEGETAL_DECORATION, aspen);
    }

    private static DataPool.Builder<BlockState> pool() {
        return DataPool.builder();
    }
}
