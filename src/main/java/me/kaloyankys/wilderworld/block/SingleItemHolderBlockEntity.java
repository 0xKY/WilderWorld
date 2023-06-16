package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.init.WWEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class SingleItemHolderBlockEntity extends ChestBlockEntity {
    public SingleItemHolderBlockEntity(BlockPos pos, BlockState state) {
        super(WWEntities.SINGLE_ITEM_HOLDER, pos, state);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.single_item");
    }

    @Override
    public int size() {
        return 1;
    }

    public ItemStack get() {
        return this.getStack(0);
    }

    public void set(ItemStack stack) {
        this.setStack(0, stack);
    }
}