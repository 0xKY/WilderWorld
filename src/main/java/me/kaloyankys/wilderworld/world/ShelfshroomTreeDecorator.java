package me.kaloyankys.wilderworld.world;

import com.mojang.serialization.Codec;
import me.kaloyankys.wilderworld.block.ShelfshroomBlock;
import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.mixin.TreeDecoratorTypeInvoker;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class ShelfshroomTreeDecorator extends TreeDecorator {

    public static final ShelfshroomTreeDecorator DECORATOR = new ShelfshroomTreeDecorator();
    public static final Codec<ShelfshroomTreeDecorator> CODEC = Codec.unit(() -> DECORATOR);
    public static TreeDecoratorType<ShelfshroomTreeDecorator> SHELFSHROOM = register();

    @Override
    protected TreeDecoratorType<?> getType() {
        return SHELFSHROOM;
    }

    @Override
    public void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, List<BlockPos> logPositions, List<BlockPos> leavesPositions) {
        if (world instanceof ChunkRegion world1) {
            for (BlockPos pos : logPositions) {
                Direction dir = Direction.fromHorizontal(random.nextInt(4));
                if (world1.getBlockState(pos.offset(dir)).isAir()) {
                    world1.setBlockState(pos.offset(dir), WWBlocks.SHELFSHROOM.getDefaultState().with(ShelfshroomBlock.FACING, dir), 3);
                }
            }
        } else if (world instanceof World world1) {
            for (BlockPos pos : logPositions) {
                Direction dir = Direction.fromHorizontal(random.nextInt(4));
                if (world1.getBlockState(pos.offset(dir)).isAir()) {
                    world1.setBlockState(pos.offset(dir), WWBlocks.SHELFSHROOM.getDefaultState().with(ShelfshroomBlock.FACING, dir), 3);
                }
            }
        }
    }

    private static TreeDecoratorType<ShelfshroomTreeDecorator> register() {
        SHELFSHROOM = TreeDecoratorTypeInvoker.createType(CODEC);
        Registry.register(Registry.TREE_DECORATOR_TYPE, new Identifier("wilderworld", "shelfshroom_decorator"), SHELFSHROOM);
        return SHELFSHROOM;
    }
}