package me.kaloyankys.wilderworld.util.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface Snowy {
    default BlockState getPlacementState(BlockState defaultState, BooleanProperty snowy, World world, BlockPos pos) {
        return defaultState.with(snowy, !world.getBiome(pos).value().doesNotSnow(pos));
    }
}
