package me.kaloyankys.wilderworld.item;

import me.kaloyankys.wilderworld.init.WWItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

import java.util.Random;

public class CoffeeMugItem extends MugItem {
    public CoffeeMugItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        Random random = new Random();
        super.finishUsing(stack, world, user);
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        if (!world.isClient) {
            user.getStatusEffects().forEach((effect) -> {
                if (random.nextInt(5) == 0) {
                    user.removeStatusEffect(effect.getEffectType());
                }
            });
        }
        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        }
        if (user instanceof PlayerEntity playerEntity && !((PlayerEntity) user).getAbilities().creativeMode) {
            if (!playerEntity.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE))) {
                playerEntity.dropItem((new ItemStack(WWItems.EMPTY_MUG)), false);
            }
        }
        return stack;
    }
}
