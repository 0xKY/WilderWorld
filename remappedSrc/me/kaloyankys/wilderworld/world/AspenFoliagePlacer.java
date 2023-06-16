package me.kaloyankys.wilderworld.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.kaloyankys.wilderworld.init.WWBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class AspenFoliagePlacer extends SpruceFoliagePlacer {
    public static final Codec<AspenFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> fillFoliagePlacerFields(instance)
            .and(IntProvider.createValidatingCodec(0, 24)
                    .fieldOf("trunk_height")
                    .forGetter((aspenFoliagePlacer) -> aspenFoliagePlacer.trunkHeight))
            .apply(instance, AspenFoliagePlacer::new));
    private final IntProvider trunkHeight;

    public AspenFoliagePlacer(IntProvider intProvider, IntProvider intProvider2, IntProvider intProvider3) {
        super(intProvider, intProvider2, intProvider3);
        this.trunkHeight = intProvider3;
    }

    @Override
    protected void generate(TestableWorld testableWorld, BiConsumer<BlockPos, BlockState> biConsumer, Random random, TreeFeatureConfig treeFeatureConfig, int i, FoliagePlacer.TreeNode treeNode, int j, int k, int l) {
        BlockPos blockPos = treeNode.getCenter();
        int m = random.nextInt(2);
        int n = 1;
        int o = 0;

        for(int p = l; p >= -j + i - 1; --p) {
            this.generateSquare(testableWorld, biConsumer, random, treeFeatureConfig, blockPos, m, p, treeNode.isGiantTrunk());
            if (m >= n) {
                m = o;
                o = 1;
                n = Math.min(n + 1, k + treeNode.getFoliageRadius());
            } else {
                ++m;
            }
        }

    }

    @Override
    protected void generateSquare(TestableWorld testableWorld, BiConsumer<BlockPos, BlockState> biConsumer, Random random, TreeFeatureConfig treeFeatureConfig, BlockPos blockPos, int i, int j, boolean bl) {
        if (testableWorld.testBlockState(blockPos.down(1), Predicate.isEqual(Blocks.AIR.getDefaultState()))) {
            super.generateSquare(testableWorld, biConsumer, random, treeFeatureConfig, blockPos, i, j, bl);
        }
    }
}
