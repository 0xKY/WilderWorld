package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.init.WWBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin extends LootableContainerBlockEntity implements Hopper {
    protected HopperBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Inject(at = @At("HEAD"), method = "serverTick", cancellable = true)
    private static void serverTick(World world, BlockPos pos, BlockState state, HopperBlockEntity blockEntity, CallbackInfo ci) {
        if (world.getBlockState(pos.up()).isOf(WWBlocks.BUTTERFLY_WING_MEMBRANE_BLOCK)) {
            ci.cancel();
        }
    }
}
