package me.kaloyankys.wilderworld.util.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface WisteriaChainAffector {

    boolean affect(World world, BlockPos pos, BlockState state);
}
