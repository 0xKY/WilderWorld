package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.entity.ButterflyEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.Random;

public class SpawnBlock extends FallingBlock {
    public SpawnBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(2) == 0) {
            world.breakBlock(pos, false);
            ButterflyEntity baby = new ButterflyEntity(world);
            baby.setBaby(true);
            baby.setPersistent();
            baby.updatePosition(pos.getX() + random.nextInt(2), pos.getY() + random.nextInt(2), pos.getZ() + random.nextInt(2));
            world.spawnEntity(baby);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.0D, 0.0D, 0.0D, 1.0D, 0.1D, 1.0D);
    }
}
