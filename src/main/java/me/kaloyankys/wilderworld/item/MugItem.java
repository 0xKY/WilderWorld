package me.kaloyankys.wilderworld.item;

import me.kaloyankys.wilderworld.init.WWItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MugItem extends HoneyBottleItem {

    @Nullable
    private final StatusEffectInstance sei;

    public MugItem(Item.Settings settings, @Nullable StatusEffectInstance statusEffectInstance) {
        super(settings);
        sei = statusEffectInstance;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!this.getDefaultStack().isOf(WWItems.EMPTY_MUG)) {
            if (user instanceof PlayerEntity player) {
                if (!player.isCreative()) {
                    stack.decrement(1);
                    player.addStatusEffect(sei);
                    if (player.getInventory().contains(ItemStack.EMPTY)) {
                        player.giveItemStack(WWItems.EMPTY_MUG.getDefaultStack());
                    } else {
                        player.dropItem(WWItems.EMPTY_MUG.getDefaultStack(), true, true);
                    }
                }
            }
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return !this.getDefaultStack().isOf(WWItems.EMPTY_MUG) ? 40 : 0;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return !this.getDefaultStack().isOf(WWItems.EMPTY_MUG) ? UseAction.DRINK : UseAction.NONE;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }
}
