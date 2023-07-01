package me.kaloyankys.wilderworld.world;

import me.kaloyankys.wilderworld.block.VerticalConnectorBlock;
import me.kaloyankys.wilderworld.init.WWBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.predicate.block.BlockPredicate;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;

import java.util.function.Predicate;

public class WisteriaFoliagePlacer extends RandomSpreadFoliagePlacer {
    private final int leafPlacementAttempts;

    public WisteriaFoliagePlacer(IntProvider intProvider, IntProvider intProvider2, IntProvider intProvider3, int i) {
        super(intProvider, intProvider2, intProvider3, i);

        this.leafPlacementAttempts = i;
    }

    @Override
    protected void generate(TestableWorld testableWorld, BlockPlacer blockPlacer, Random random, TreeFeatureConfig treeFeatureConfig, int i, TreeNode treeNode, int j, int k, int l) {
        BlockPos blockPos = treeNode.getCenter();
        BlockPos.Mutable mutable = blockPos.mutableCopy();
        for (int m = 0; m < this.leafPlacementAttempts; ++m) {
            mutable.set(blockPos, random.nextInt(k) - random.nextInt(k), random.nextInt(j) - random.nextInt(j), random.nextInt(k) - random.nextInt(k));
            placeFoliageBlock(testableWorld, blockPlacer, random, treeFeatureConfig, mutable);
            //for (int a = 3; a > 0; a--) {
                    //placeBlossomBlock(biConsumer, blockPos.down(a), a);
            //}
        }
    }

    protected static void placeBlossomBlock(BlockPlacer blockPlacer, BlockPos blockPos, int size) {
        BlockState blockState = WWBlocks.DROOPBLOOM.getDefaultState().with(VerticalConnectorBlock.TYPE, size);
        blockPlacer.placeBlock(blockPos, blockState);
    }

    protected static boolean placeFoliageBlock(TestableWorld testableWorld, BlockPlacer blockPlacer, Random random, TreeFeatureConfig treeFeatureConfig, BlockPos blockPos) {
        if (TreeFeature.canReplace(testableWorld, blockPos)) {
            BlockState blockState = treeFeatureConfig.foliageProvider.get(random, blockPos);
            if (blockState.contains(Properties.WATERLOGGED)) {
                blockState = blockState.with(Properties.WATERLOGGED, testableWorld.testFluidState(blockPos, fluidState -> fluidState.isEqualAndStill(Fluids.WATER)));
            }
            blockPlacer.placeBlock(blockPos, blockState);
            if (random.nextInt(10) == 0) {
                if (testableWorld.testBlockState(blockPos.down(2), BlockPredicate.make(Blocks.AIR)) && testableWorld.testBlockState(blockPos.down(1), BlockPredicate.make(Blocks.AIR)) && testableWorld.testBlockState(blockPos.down(1), BlockPredicate.make(Blocks.AIR))) {
                    for (int a = 3; a > 0; a--) {
                        placeBlossomBlock(blockPlacer, blockPos.down(a), a);
                    }
                } else if (testableWorld.testBlockState(blockPos.down(1), Predicate.isEqual(Blocks.AIR.getDefaultState()))) {
                    placeBlossomBlock(blockPlacer, blockPos.down(1), 1);
                }
            }
        }
        return false;
    }

    @Override
    protected void generateSquare(TestableWorld testableWorld, BlockPlacer blockPlacer, Random random, TreeFeatureConfig treeFeatureConfig, BlockPos blockPos, int i, int j, boolean bl) {
        int k = bl ? 1 : 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int l = -i; l <= i + k; ++l) {
            for (int m = -i; m <= i + k; ++m) {
                if (this.isPositionInvalid(random, l, j, m, i, bl)) continue;
                mutable.set(blockPos, l, j, m);
                placeFoliageBlock(testableWorld, blockPlacer, random, treeFeatureConfig, mutable);
                for (int a = 3; a > 0; a--) {
                    placeBlossomBlock(blockPlacer, blockPos.down(a), a);
                }
            }
        }
    }
}
