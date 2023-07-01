package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.block.VerticalConnectorBlock;
import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.util.records.WWFeature;
import me.kaloyankys.wilderworld.world.WisteriaFoliagePlacer;
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
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(RandomSpreadFoliagePlacer.class)
public abstract class RandomSpreadFoliagePlacerMixin extends FoliagePlacer {
    @Shadow @Final private int leafPlacementAttempts;
    @Shadow @Final private IntProvider foliageHeight;

    public RandomSpreadFoliagePlacerMixin(IntProvider intProvider, IntProvider intProvider2) {
        super(intProvider, intProvider2);
    }

    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    private void generate(TestableWorld testableWorld, BlockPlacer blockPlacer, Random random, TreeFeatureConfig treeFeatureConfig, int i, TreeNode treeNode, int j, int k, int l, CallbackInfo ci) {
        ci.cancel();
        BlockPos blockPos = treeNode.getCenter();
        BlockPos.Mutable mutable = blockPos.mutableCopy();
        if (treeFeatureConfig.trunkProvider.get(random, blockPos).isOf(WWBlocks.WISTERIA.log())) {
            for (int m = 0; m < this.leafPlacementAttempts; ++m) {
                mutable.set(blockPos, random.nextInt(k) - random.nextInt(k), random.nextInt(j) - random.nextInt(j), random.nextInt(k) - random.nextInt(k));
                placeWWFoliageBlock(testableWorld, blockPlacer, random, treeFeatureConfig, mutable);
            }
        } else {
            for(int m = 0; m < this.leafPlacementAttempts; ++m) {
                mutable.set(blockPos, random.nextInt(k) - random.nextInt(k), random.nextInt(j) - random.nextInt(j), random.nextInt(k) - random.nextInt(k));
                placeFoliageBlock(testableWorld, blockPlacer, random, treeFeatureConfig, mutable);
            }

        }
    }

    private static void placeBlossomBlock(BlockPlacer blockPlacer, BlockPos blockPos, int size) {
        BlockState blockState = WWBlocks.DROOPBLOOM.getDefaultState().with(VerticalConnectorBlock.TYPE, size);
        blockPlacer.placeBlock(blockPos, blockState);
    }

    private static boolean placeWWFoliageBlock(TestableWorld testableWorld, BlockPlacer blockPlacer, Random random, TreeFeatureConfig treeFeatureConfig, BlockPos blockPos) {
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


    private void generateWWSquare(TestableWorld testableWorld, BlockPlacer blockPlacer, Random random, TreeFeatureConfig treeFeatureConfig, BlockPos blockPos, int i, int j, boolean bl) {
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

