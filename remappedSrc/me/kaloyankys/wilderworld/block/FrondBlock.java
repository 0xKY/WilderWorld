package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.init.WWBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class FrondBlock extends Block {
    public static IntProperty SIZE = IntProperty.of("size", 1, 3);
    public static DirectionProperty FACING = DirectionProperty.of("facing");

    public FrondBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SIZE, FACING);
        super.appendProperties(builder);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if (state.isOf(WWBlocks.FROND) && state.get(FACING).getAxis().isHorizontal() && state.get(FACING) == ctx.getSide().getOpposite()) {
            return super.getPlacementState(ctx).with(FACING, state.get(FACING)).with(SIZE, 1);
        } else {
            return super.getPlacementState(ctx).with(FACING, ctx.getSide()).with(SIZE, 1);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!world.getBlockState(pos.offset(state.get(FACING).getOpposite())).isSolidBlock(world, pos) && !world.getBlockState(pos.offset(state.get(FACING).getOpposite())).isOf(WWBlocks.FROND)) {
            world.breakBlock(pos, true);
        }
        if (world.getBlockState(pos.offset(state.get(FACING))).isOf(WWBlocks.FROND)) {
            world.setBlockState(pos, state.with(SIZE, Math.min(3, world.getBlockState(pos.offset(state.get(FACING))).get(SIZE) + 1)), 3);
        } return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}

