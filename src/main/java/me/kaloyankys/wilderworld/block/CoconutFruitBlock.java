package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.util.classes.CoconutUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class CoconutFruitBlock extends Block {
    public static final IntProperty STAGE = IntProperty.of("stage", 1, 3);

    public CoconutFruitBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(STAGE);
    }

    @Override
    public void onBlockBreakStart(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        super.onBlockBreakStart(blockState, world, blockPos, playerEntity);

        if (CoconutUtil.stage(world, blockPos) == 3) {
            CoconutUtil.fall(world, blockPos);
        }
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random) {
        super.randomTick(blockState, serverWorld, blockPos, random);

        if (CoconutUtil.stage(serverWorld, blockPos) < 3) {
            CoconutUtil.grow(serverWorld, blockPos);
        }
    }
}
