package me.kaloyankys.wilderworld.item;

import me.kaloyankys.wilderworld.block.IceCreamBlock;
import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.util.interfaces.FlavourSet;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class IceCreamItem extends BlockItem {
    private final FlavourSet flavour;
    private final IceCreamBlock block;

    public IceCreamItem(IceCreamBlock block, Settings settings) {
        super(block, settings);
        this.block = block;
        this.flavour = block.getFlavour();
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.addStatusEffect(new StatusEffectInstance(flavour.getEffect(), 400, 0));
        if (user instanceof PlayerEntity player) {
            if (!player.isCreative()) {
                stack.decrement(1);
                if (player.getInventory().contains(ItemStack.EMPTY)) {
                    player.giveItemStack(Items.BOWL.getDefaultStack());
                } else {
                    player.dropItem(Items.BOWL.getDefaultStack(), true, true);
                }
            }
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 80;
    }

    @Override
    public Block getBlock() {
        return block;
    }
}
