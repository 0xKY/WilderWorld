package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.util.interfaces.Snowy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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
        return this.getPlacementState(ctx, this.getDefaultState(), SNOWY, ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}
