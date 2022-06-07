package me.kaloyankys.wilderworld.item;

import me.kaloyankys.wilderworld.util.interfaces.FoodEffects;
import me.kaloyankys.wilderworld.util.interfaces.MaximumPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ConditionalFoodItem extends Item {
    private final int maxFood;
    private final int maxSaturation;
    private final MaximumPredicate maximumPredicate;
    private final FoodEffects effects;

    public ConditionalFoodItem(Settings settings, int maxFood, int maxSaturation, MaximumPredicate maximumPredicate, FoodEffects effects) {
        super(settings);
        this.maxFood = maxFood;
        this.maxSaturation = maxSaturation;
        this.maximumPredicate = maximumPredicate;
        this.effects = effects;
    }

    @Override
    public ItemStack finishUsing(ItemStack itemStack, World world, LivingEntity livingEntity) {
        this.effects.apply(maxFood, maxSaturation, world, livingEntity, itemStack, maximumPredicate.onlyWhen(world, livingEntity, itemStack));
        return super.finishUsing(itemStack, world, livingEntity);
    }
}
