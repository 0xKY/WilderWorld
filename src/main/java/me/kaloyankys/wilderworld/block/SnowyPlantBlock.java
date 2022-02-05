package me.kaloyankys.wilderworld.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.world.biome.Biome;
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
        if (ctx.getWorld().getBiome(ctx.getBlockPos()).getCategory().equals(Biome.Category.ICY)) {
            return this.getDefaultState().with(SNOWY, true);
        } else {
            return this.getDefaultState().with(SNOWY, false);
        }
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}
