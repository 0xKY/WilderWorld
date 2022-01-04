package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.init.WWEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class MembraneBlockEntity extends LootableContainerBlockEntity {

    private ItemStack filter = ItemStack.EMPTY;
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(this.size(), filter);

    protected MembraneBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    public MembraneBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(WWEntities.MEMBRANE, blockPos, blockState);
    }

    public void onCollision(Entity entity) {
        if (entity instanceof ItemEntity item) {
            Random random = new Random();
            if (this.getFilter() == ItemStack.EMPTY) {
                this.setFilter(item.getStack());
            } else if (this.getFilter().getItem() != item.getStack().getItem()) {
                entity.addVelocity(entity.getVelocity().x, (random.nextInt(2) + 1) / 10.0, entity.getVelocity().z);
                entity.teleport(entity.getX(), pos.up().getY(), entity.getZ());
            }
        }
    }


    @Override
    public int size() {
        return 1;
    }


    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.membrane");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        nbt.getString("filter");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putString("filter", filter.getItem().toString());
    }

    private ItemStack getFilter() {
        return filter;
    }

    private void setFilter(ItemStack stack) {
        filter = stack;
        this.markDirty();
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        inventory = list;
    }
}