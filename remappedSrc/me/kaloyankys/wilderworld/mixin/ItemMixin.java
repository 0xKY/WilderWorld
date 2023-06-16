package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.block.IceCreamBlock;
import me.kaloyankys.wilderworld.util.classes.IceCreamUtil;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemConvertible {
    @Inject(at = @At("HEAD"), method = "useOnBlock")
    private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (context.getPlayer() != null && context.getPlayer().getStackInHand(context.getHand()).isOf(Items.BOWL)
                && context.getWorld().getBlockState(context.getBlockPos()).getBlock() instanceof IceCreamBlock iceCreamBlock) {
            context.getPlayer().getStackInHand(context.getHand()).decrement(1);
            if (context.getPlayer().getInventory().contains(ItemStack.EMPTY)) {
                context.getPlayer().giveItemStack(iceCreamBlock.asItem().getDefaultStack());
            } else {
                context.getPlayer().dropItem(iceCreamBlock.asItem().getDefaultStack(), false, true);
            }
            if ((context.getWorld().getBlockState(context.getBlockPos()).get(IceCreamBlock.LAYERS) > 1)) {
                IceCreamUtil.changeLayers(context.getWorld(), context.getBlockPos(), iceCreamBlock, -1);
            } else {
                context.getWorld().breakBlock(context.getBlockPos(), false);
            }
        }
    }
}