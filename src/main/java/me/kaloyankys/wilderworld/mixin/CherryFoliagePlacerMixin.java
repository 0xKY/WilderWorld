package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.block.VerticalConnectorBlock;
import me.kaloyankys.wilderworld.init.WWBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.CherryFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(CherryFoliagePlacer.class)
public abstract class CherryFoliagePlacerMixin extends FoliagePlacer {
    @Shadow
    @Final
    private float hangingLeavesChance;

    @Shadow @Final private float hangingLeavesExtensionChance;

    public CherryFoliagePlacerMixin(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    private void generate(TestableWorld world, FoliagePlacer.BlockPlacer arg, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset, CallbackInfo ci) {
        ci.cancel();

        boolean bl = treeNode.isGiantTrunk();
        BlockPos blockPos = treeNode.getCenter().up(offset);
        int i = radius + treeNode.getFoliageRadius() - 1;
        this.generateSquare(world, arg, random, config, blockPos, i - 2, foliageHeight - 3, bl);
        this.generateSquare(world, arg, random, config, blockPos, i - 1, foliageHeight - 4, bl);
        for (int j = foliageHeight - 5; j >= 0; --j) {
            this.generateSquare(world, arg, random, config, blockPos, i, j, bl);
        }
        this.method_49247_custom(world, arg, random, config, blockPos, i, -1, bl, this.hangingLeavesChance, this.hangingLeavesExtensionChance);
        this.method_49247_custom(world, arg, random, config, blockPos, i - 1, -2, bl, this.hangingLeavesChance, this.hangingLeavesExtensionChance);
    }


    private boolean placeFoliageBlockCustom(TestableWorld world, FoliagePlacer.BlockPlacer arg, Random random, TreeFeatureConfig config, BlockPos pos) {
        if (!TreeFeature.canReplace(world, pos)) {
            return false;
        }
        BlockState blockState = random.nextInt(20) == 0 ? config.foliageProvider.get(random, pos) : WWBlocks.WISTERIA_BLOSSOM_LEAVES.getDefaultState();
        int droopBloomHeight = random.nextInt(3) + 1;
        for (int i = 0; i < droopBloomHeight; i++) {
            BlockPos down = pos.down(i);
            if (world.testBlockState(down, Predicate.isEqual(Blocks.AIR.getDefaultState()))) {
                arg.placeBlock(down, WWBlocks.DROOPBLOOM.getDefaultState().with(VerticalConnectorBlock.TYPE, 3 - i));
            }
        }
        if (blockState.contains(Properties.WATERLOGGED)) {
            blockState = blockState.with(Properties.WATERLOGGED, world.testFluidState(pos, fluidState -> fluidState.isEqualAndStill(Fluids.WATER)));
        }
        arg.placeBlock(pos, blockState);
        return true;
    }

    private void method_49247_custom(TestableWorld testableWorld, FoliagePlacer.BlockPlacer arg, Random random, TreeFeatureConfig treeFeatureConfig, BlockPos blockPos, int i, int j, boolean bl, float f, float g) {
        this.generateSquare(testableWorld, arg, random, treeFeatureConfig, blockPos, i, j, bl);
        int k = bl ? 1 : 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : Direction.Type.HORIZONTAL) {
            Direction direction2 = direction.rotateYClockwise();
            int l = direction2.getDirection() == Direction.AxisDirection.POSITIVE ? i + k : i;
            mutable.set(blockPos, 0, j - 1, 0).move(direction2, l).move(direction, -i);
            for (int m = -i; m < i + k; ++m) {
                boolean bl2 = arg.hasPlacedBlock(mutable.move(Direction.UP));
                mutable.move(Direction.DOWN);
                if (bl2 && !(random.nextFloat() > f) && placeFoliageBlockCustom(testableWorld, arg, random, treeFeatureConfig, mutable) && !(random.nextFloat() > g)) {
                    if (treeFeatureConfig.trunkProvider.get(random, blockPos).isOf(WWBlocks.WISTERIA.log())) {
                        placeFoliageBlockCustom(testableWorld, arg, random, treeFeatureConfig, blockPos);
                    } else {
                        placeFoliageBlock(testableWorld, arg, random, treeFeatureConfig, blockPos);
                    }
                    mutable.move(Direction.UP);
                }
                mutable.move(direction);
            }
        }
    }
}
