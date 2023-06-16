package me.kaloyankys.wilderworld.util.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@FunctionalInterface
public interface FoodEffects {
    void apply(int maxFood, int maxSaturation, World world, LivingEntity entity, ItemStack stack, boolean isIdeal);
}
