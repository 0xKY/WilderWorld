package me.kaloyankys.wilderworld.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class SnowyBushBlock extends TallPlantBlock {
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

        final BlockState[] state = new BlockState[1];
        BlockPos.iterateOutwards(ctx.getBlockPos(), 1, 0, 1).forEach((blockPos -> state[0] =
                ctx.getWorld().getBlockState(blockPos).isOf(Blocks.SNOW) ? this.getDefaultState().with(SNOWY, true) :
                        this.getDefaultState().with(SNOWY, false)));

        return state[0];
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}
