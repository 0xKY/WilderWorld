package me.kaloyankys.wilderworld.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class WildHoneyBottleItem extends HoneyBottleItem {
    public WildHoneyBottleItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack itemStack, World world, LivingEntity livingEntity) {
        super.finishUsing(itemStack, world, livingEntity);
        if (livingEntity instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, itemStack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        Potion potion = PotionUtil.getPotion(itemStack);
        if (!world.isClient && potion != null) {
            potion.getEffects().forEach(statusEffectInstance -> livingEntity.removeStatusEffect(statusEffectInstance.getEffectType()));
        }

        if (itemStack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (livingEntity instanceof PlayerEntity && !((PlayerEntity)livingEntity).getAbilities().creativeMode) {
                ItemStack itemStack2 = new ItemStack(Items.GLASS_BOTTLE);
                PlayerEntity playerEntity = (PlayerEntity)livingEntity;
                if (!playerEntity.getInventory().insertStack(itemStack2)) {
                    playerEntity.dropItem(itemStack2, false);
                }
            }

            return itemStack;
        }
    }
}
