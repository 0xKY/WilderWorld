package me.kaloyankys.wilderworld.util.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@FunctionalInterface
public interface Fulfilling {
    boolean onlyWhen(World world, LivingEntity entity, ItemStack stack);
}
