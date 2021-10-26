package me.kaloyankys.wilderworld.world;

import com.mojang.serialization.Codec;
import me.kaloyankys.wilderworld.block.ShelfshroomBlock;
import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.mixin.TreeDecoratorTypeRegisterInvoker;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class ShelfshroomTreeDecorator extends TreeDecorator {
    public static final TreeDecoratorType<ShelfshroomTreeDecorator> SHELFSHROOM =
            TreeDecoratorTypeRegisterInvoker.invokeRegister("wilderworld:shelfshroom_decorator", ShelfshroomTreeDecorator.CODEC);

    public static final Codec<ShelfshroomTreeDecorator> CODEC;
    public static final ShelfshroomTreeDecorator DECORATOR = new ShelfshroomTreeDecorator();

    @Override
    protected TreeDecoratorType<?> getType() {
        return SHELFSHROOM;
    }

    static {
        CODEC = Codec.unit(() -> DECORATOR);
    }

    @Override
    public void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions) {
     World world1 = (World) world;
        for (BlockPos pos : logPositions) {
            if (random.nextInt(25) == 0) {
                Direction dir = Direction.fromHorizontal(random.nextInt(4));
                if (world1.getBlockState(pos.offset(dir)).isAir()) {
                    world1.setBlockState(pos.offset(dir), WWBlocks.SHELFSHROOM.getDefaultState().with(ShelfshroomBlock.FACING, dir), 3);
                }
            }
        }
    }
}