package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.block.SlushCauldronBlock;
import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.init.WWItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {
    @Inject(at = @At("HEAD"), method = "onBlockAdded")
    private void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
        if (world.getBlockState(pos).isOf(Blocks.BIRCH_SAPLING)) {
            world.setBlockState(pos, WWBlocks.ASPEN_SAPLING.getDefaultState());
        }
    }

    @Inject(at = @At("HEAD"), method = "onUse")
    private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (state.isOf(Blocks.WATER_CAULDRON) && player.getStackInHand(hand).isOf(Blocks.ICE.asItem())) {
            world.setBlockState(pos, WWBlocks.SLUSH_CAULDRON.getDefaultState().with(SlushCauldronBlock.LEVEL, state.get(LeveledCauldronBlock.LEVEL)));
        } else if (state.isOf(Blocks.WATER_CAULDRON) && player.getStackInHand(hand).isOf(WWItems.ICE_CUBE)) {
            world.setBlockState(pos, WWBlocks.SLUSH_CAULDRON.getDefaultState().with(SlushCauldronBlock.LEVEL, 1));
        }
    }
}

