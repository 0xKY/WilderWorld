package me.kaloyankys.wilderworld.item;

import me.kaloyankys.wilderworld.init.WWItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
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
        super.finishUsing(stack, world, user);
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        if (user instanceof PlayerEntity playerEntity) {
        /*
        } */
            if (stack.isEmpty()) {
                return new ItemStack(WWItems.EMPTY_MUG);
            }
            if (!((PlayerEntity) user).getAbilities().creativeMode) {
                if (!playerEntity.getInventory().insertStack(new ItemStack(WWItems.EMPTY_MUG))) {
                    playerEntity.dropItem((new ItemStack(WWItems.EMPTY_MUG)), false);
                }
            }
            if (sei != null) {
                playerEntity.addStatusEffect(sei);
            }
        }
        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 40;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public SoundEvent getDrinkSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}
