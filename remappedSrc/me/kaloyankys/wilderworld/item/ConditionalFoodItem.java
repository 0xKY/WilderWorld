package me.kaloyankys.wilderworld.item;

import me.kaloyankys.wilderworld.util.interfaces.FoodEffects;
import me.kaloyankys.wilderworld.util.interfaces.Fulfilling;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ConditionalFoodItem extends Item {
    private final int maxFood;
    private final int maxSaturation;
    private final Fulfilling fulfilling;
    private final FoodEffects effects;

    public ConditionalFoodItem(Settings settings, int maxFood, int maxSaturation, Fulfilling maximumPredicate, FoodEffects effects) {
        super(settings);
        this.maxFood = maxFood;
        this.maxSaturation = maxSaturation;
        this.fulfilling = maximumPredicate;
        this.effects = effects;
    }

    @Override
    public ItemStack finishUsing(ItemStack itemStack, World world, LivingEntity livingEntity) {
        this.effects.apply(maxFood, maxSaturation, world, livingEntity, itemStack, fulfilling.onlyWhen(world, livingEntity, itemStack));
        return super.finishUsing(itemStack, world, livingEntity);
    }
}
