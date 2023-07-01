package me.kaloyankys.wilderworld.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.world.LargeForkingTrunkPlacer;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.MegaJungleTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

@Mixin(MegaJungleTrunkPlacer.class)
public abstract class LargeTrunkPlacerMixin extends TrunkPlacer {

    private final LargeForkingTrunkPlacer largeForkingTrunkPlacer;
    public LargeTrunkPlacerMixin(int i, int j, int k) {
        super(i, j, k);
        largeForkingTrunkPlacer = new LargeForkingTrunkPlacer(i, j, k);
    }

    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    private void generate(TestableWorld testableWorld, BiConsumer<BlockPos, BlockState> biConsumer, Random random, int i, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig, CallbackInfoReturnable<List<FoliagePlacer.TreeNode>> cir) {
        cir.cancel();
        if (testableWorld.testBlockState(blockPos, Predicate.isEqual(WWBlocks.ASPEN.log().getDefaultState()))) {
            cir.setReturnValue(largeForkingTrunkPlacer.generate(testableWorld, biConsumer, random, i, blockPos, treeFeatureConfig));
        } else {
            List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
            list.addAll(superDuper(testableWorld, biConsumer, random, i, blockPos, treeFeatureConfig));

            for(int j = i - 2 - random.nextInt(4); j > i / 2; j -= 2 + random.nextInt(4)) {
                float f = random.nextFloat() * 6.2831855F;
                int k = 0;
                int l = 0;

                for(int m = 0; m < 5; ++m) {
                    k = (int)(1.5F + MathHelper.cos(f) * (float)m);
                    l = (int)(1.5F + MathHelper.sin(f) * (float)m);
                    BlockPos blockPos2 = blockPos.add(k, j - 3 + m / 2, l);
                    this.getAndSetState(testableWorld, biConsumer, random, blockPos2, treeFeatureConfig);
                }

                list.add(new FoliagePlacer.TreeNode(blockPos.add(k, j, l), -2, false));
            }

            cir.setReturnValue(list);
        }
    }

    private ImmutableList<FoliagePlacer.TreeNode> superDuper(TestableWorld testableWorld, BiConsumer<BlockPos, BlockState> biConsumer, Random random, int i, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig) {
        BlockPos blockPos2 = blockPos.down();
        setToDirt(testableWorld, biConsumer, random, blockPos2, treeFeatureConfig);
        setToDirt(testableWorld, biConsumer, random, blockPos2.east(), treeFeatureConfig);
        setToDirt(testableWorld, biConsumer, random, blockPos2.south(), treeFeatureConfig);
        setToDirt(testableWorld, biConsumer, random, blockPos2.south().east(), treeFeatureConfig);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int j = 0; j < i; ++j) {
            this.setLog(testableWorld, biConsumer, random, mutable, treeFeatureConfig, blockPos, 0, j, 0);
            if (j < i - 1) {
                this.setLog(testableWorld, biConsumer, random, mutable, treeFeatureConfig, blockPos, 1, j, 0);
                this.setLog(testableWorld, biConsumer, random, mutable, treeFeatureConfig, blockPos, 1, j, 1);
                this.setLog(testableWorld, biConsumer, random, mutable, treeFeatureConfig, blockPos, 0, j, 1);
            }
        }

        return ImmutableList.of(new FoliagePlacer.TreeNode(blockPos.up(i), 0, true));
    }

    private void setLog(TestableWorld testableWorld, BiConsumer<BlockPos, BlockState> biConsumer, Random random, BlockPos.Mutable mutable, TreeFeatureConfig treeFeatureConfig, BlockPos blockPos, int i, int j, int k) {
        mutable.set(blockPos, i, j, k);
        this.trySetState(testableWorld, biConsumer, random, mutable, treeFeatureConfig);
    }
}
