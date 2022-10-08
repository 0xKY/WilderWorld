package me.kaloyankys.wilderworld.world;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.MegaJungleTrunkPlacer;

import java.util.List;
import java.util.function.BiConsumer;

public class LargeForkingTrunkPlacer extends MegaJungleTrunkPlacer {
    public LargeForkingTrunkPlacer(int i, int j, int k) {
        super(i, j, k);
    }

    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
        List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        list.addAll(super.generate(world, replacer, random, height, startPos, config));

        for (int i = height - 2 - random.nextInt(4); i > height / 10; i -= 1 + random.nextInt(4)) {
            float f = random.nextFloat() * 6.2831855F;
            int j = 0;
            int k = 0;

            for (int l = 0; l < 5; ++l) {
                j = (int) (1.5F + MathHelper.cos(f) * (float) l);
                k = (int) (1.5F + MathHelper.sin(f) * (float) l);
                BlockPos blockPos = startPos.add(j, i - 3 + l / 2, k);
                getAndSetState(world, replacer, random, blockPos, config);
            }

            list.add(new FoliagePlacer.TreeNode(startPos.add(j, i, k), -2, false));
        }

        return list;
    }
}
