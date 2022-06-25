package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.init.WWEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class MembraneBlockEntity extends ChestBlockEntity {

    public MembraneBlockEntity(BlockPos pos, BlockState state) {
        super(WWEntities.MEMBRANE, pos, state);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.membrane");
    }

    @Override
    public int size() {
        return 1;
    }
}