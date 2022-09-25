package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.util.interfaces.Snowy;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SnowyPlantBlock extends PlantBlock implements Snowy {
    private static final BooleanProperty SNOWY = BooleanProperty.of("snowy");

    public SnowyPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SNOWY);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        super.getPlacementState(ctx);
        return this.getPlacementState(this.getDefaultState(), SNOWY, ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return VoxelShapes.fullCube().offset(0, 0, 0);
    }
}
