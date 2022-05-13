package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.init.WWTags;
import me.kaloyankys.wilderworld.util.classes.CustomParticleUtil;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(WaterFluid.class)
public class WaterFluidMixin {
    @Inject(at = @At("HEAD"), method = "randomDisplayTick")
    private void randomDisplayTick(World world, BlockPos blockPos, FluidState fluidState, Random random, CallbackInfo ci) {
        if (world.getBlockState(blockPos.down()).isOf(WWBlocks.TRAVERTINE) || world.getBlockState(blockPos.down()).isOf(WWBlocks.TRAVERTINE_PEACH)) {
            if (random.nextInt(2) == 1) {
                ParticleUtil.spawnParticle(world, blockPos, Direction.UP, ParticleTypes.CAMPFIRE_COSY_SMOKE);
            } else
                ParticleUtil.spawnParticle(world, blockPos, Direction.UP, ParticleTypes.CAMPFIRE_SIGNAL_SMOKE);
        }
    }
}
