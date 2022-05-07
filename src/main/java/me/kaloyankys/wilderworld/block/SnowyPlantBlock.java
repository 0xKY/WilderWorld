package me.kaloyankys.wilderworld.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SnowyPlantBlock extends PlantBlock {
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

        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();

        return this.getDefaultState().with(SNOWY, world.getBiome(pos).comp_349().isCold(pos));
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}
