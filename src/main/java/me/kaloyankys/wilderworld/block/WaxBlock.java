package me.kaloyankys.wilderworld.block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class WaxBlock extends Block {
    private static final VoxelShape SHAPE_BOTTOM_STICKY = VoxelShapes.cuboid(0.0, 0.1, 0.0, 1.0, 1.0, 1.0);
    private static final DirectionProperty FACING = Properties.FACING;
    private final ImmutableMap<BlockState, VoxelShape> shapes = this.getShapesForStates((st) -> st.get(FACING) == Direction.UP ? SHAPE_BOTTOM_STICKY : VoxelShapes.fullCube());

    public WaxBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(FACING);
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity) {
        super.onEntityCollision(blockState, world, blockPos, entity);

        entity.addVelocity(0, 0.5, 0);

        if (new Random().nextInt(10) == 0) {
            if (entity.getVelocity().getX() != 0) {
                addParticles(entity, 1);
            }
            if (entity.getVelocity().getZ() != 0) {
                addParticles(entity, 2);
            }
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext) {
        return super.getPlacementState(itemPlacementContext).with(FACING, itemPlacementContext.getPlayerLookDirection().getOpposite());
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return this.shapes.get(blockState);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return VoxelShapes.fullCube();
    }

    private static void addParticles(Entity entity, int i) {
        if (!entity.getWorld().isClient) {
            return;
        }
        BlockState blockState = Blocks.HONEY_BLOCK.getDefaultState();
        for (int j = 0; j < i; ++j) {
            entity.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), entity.getX(), entity.getEyeY(), entity.getZ(), 0.0, 0.0, 0.0);
        }
    }
}
