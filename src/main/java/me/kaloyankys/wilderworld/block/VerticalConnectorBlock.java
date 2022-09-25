package me.kaloyankys.wilderworld.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class VerticalConnectorBlock extends Block {
    public static final IntProperty TYPE = IntProperty.of("type", 0, 2);

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

        if (world.getBlockState(blockPos.up()).isOf(this)) {
            world.setBlockState(blockPos.up(), blockState.with(TYPE, 0));
        }
    }

    @Override
    public boolean canPlaceAt(BlockState blockState, WorldView worldView, BlockPos blockPos) {
        return super.canPlaceAt(blockState, worldView, blockPos) && (worldView.getBlockState(blockPos.up()).isOf(this) || worldView.getBlockState(blockPos.up()).isSolidBlock(worldView, blockPos));
    }

    @Override
    public void neighborUpdate(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        super.neighborUpdate(blockState, world, blockPos, block, blockPos2, bl);

        if (world.getBlockState(blockPos.down()).isOf(this)) {
            if (world.getBlockState(blockPos.up()).isOf(this)) {
                world.setBlockState(blockPos.up(), blockState.with(TYPE, 1));
            } else {
                world.setBlockState(blockPos.up(), blockState.with(TYPE, 2));
            }
        } else {
            world.setBlockState(blockPos.up(), blockState.with(TYPE, 0));
        }
    }
}
