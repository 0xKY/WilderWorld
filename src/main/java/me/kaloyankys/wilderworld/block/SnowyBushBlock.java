package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.util.interfaces.Snowy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class SnowyBushBlock extends TallPlantBlock implements Snowy {
    private static final BooleanProperty SNOWY = BooleanProperty.of("snowy");

    public SnowyBushBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SNOWY);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        super.getPlacementState(ctx);
        return this.getPlacementState(ctx, this.getDefaultState(), SNOWY, ctx.getWorld(), ctx.getBlockPos());
    }
}
