package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.util.classes.ConnectorUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class VerticalConnectorBlock extends Block {
    public static final IntProperty TYPE = IntProperty.of("size", 1, 3);

    public VerticalConnectorBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(TYPE);
    }

    @Override
    public void onPlaced(World world, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        super.onPlaced(world, blockPos, blockState, livingEntity, itemStack);

        BlockState up = world.getBlockState(blockPos.up());
        BlockState down = world.getBlockState(blockPos.up());
        int sUp = sizeOf(up);
        int sDown = sizeOf(down);
        int s = sizeOf(blockState);

        if (sUp < 3 && sUp > 0) {
            size(world, up, blockPos.up(), sUp + 1);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState blockState, WorldView worldView, BlockPos blockPos) {
        return ConnectorUtil.placeConditions(blockPos, worldView);
    }

    @Override
    public void neighborUpdate(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        super.neighborUpdate(blockState, world, blockPos, block, blockPos2, bl);

        BlockState up = world.getBlockState(blockPos.up());
        BlockState down = world.getBlockState(blockPos.up());
        int sUp = sizeOf(up);
        int sDown = sizeOf(down);
        int s = sizeOf(blockState);

        BlockPos chainTop = ConnectorUtil.findTop(blockPos, world, ((w, pos, state) -> false));
        BlockPos chainBottom = ConnectorUtil.findBottom(blockPos, world, ((w, pos, state) -> false));

        world.setBlockState(chainTop, world.getBlockState(chainTop).with(TYPE, 3));
        world.setBlockState(chainBottom, world.getBlockState(chainBottom).with(TYPE, 1));

        if (!ConnectorUtil.placeConditions(blockPos, world)) {
            world.breakBlock(blockPos, true);
            if (world.getBlockState(blockPos.down()).isOf(this)) {
                world.breakBlock(blockPos.down(), true);
            }
        }
    }

    @Override
    public void onBroken(WorldAccess worldAccess, BlockPos blockPos, BlockState blockState) {
        super.onBroken(worldAccess, blockPos, blockState);

        BlockState up = worldAccess.getBlockState(blockPos.up());
        BlockState down = worldAccess.getBlockState(blockPos.up());
        int sUp = sizeOf(up);

        BlockPos chainBottom = ConnectorUtil.findBottom(blockPos, (World) worldAccess, ((w, pos, state) -> {
            w.breakBlock(pos, true);
            return false;
        }));
        BlockPos chainTop = ConnectorUtil.findBottom(blockPos, (World) worldAccess, ((w, pos, state) -> {
            w.breakBlock(pos, true);
            return false;
        }));

        if (sUp > 1) {
            size(worldAccess, up, blockPos.up(), 1);
            if (worldAccess.getBlockState(blockPos.down()).isOf(this)) {
                worldAccess.breakBlock(blockPos.down(), true);
                worldAccess.breakBlock(chainBottom, true);
                worldAccess.breakBlock(chainTop, true);
            }
        }
    }

    private static void size(WorldAccess world, BlockState state, BlockPos pos, int size) {
        world.setBlockState(pos, state.with(TYPE, size), 1);
    }

    private int sizeOf(BlockState state) {
        if (state.isOf(this)) {
            return state.get(TYPE);
        } else {
            return 0;
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return VoxelShapes.cuboid(0.1, 0.0, 0.1, 0.9, 1.0, 0.9);
    }
}
