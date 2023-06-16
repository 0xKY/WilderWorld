package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.init.WWItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IceBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(IceBlock.class)
public class IceBlockMixin extends TransparentBlock {
    protected IceBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "melt")
    private void melt(BlockState state, World world, BlockPos pos, CallbackInfo ci) {
        Random random = new Random();
        if (random.nextInt(10) == 0 && world.getBlockState(pos.down()).isOf(Blocks.DIRT)) {
            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), WWItems.MINT.getDefaultStack());
        }
    }
}
