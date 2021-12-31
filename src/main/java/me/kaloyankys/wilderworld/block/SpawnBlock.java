package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.entity.EggLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.Random;

public class SpawnBlock extends FallingBlock {
    private final EntityType<AnimalEntity> parent;

    public SpawnBlock(Settings settings, EggLayer entityType) {
        super(settings);
        parent = (EntityType<AnimalEntity>) entityType;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(2) == 0) {
            world.breakBlock(pos, false);
            AnimalEntity baby = parent.create(world);
            if (baby != null) {
                baby.setBaby(true);
                baby.setPos(pos.getX() + random.nextInt(2), pos.getY() + random.nextInt(3), pos.getZ() + random.nextInt(2));
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0, 0, 0, 16, 1, 16);
    }
}
