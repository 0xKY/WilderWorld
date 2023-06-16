package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.init.WWBlocks;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FreezeTopLayerFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(FreezeTopLayerFeature.class)
public abstract class FreezeTopLayerFeatureMixin {

    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    public void generate(FeatureContext<DefaultFeatureConfig> featureContext, CallbackInfoReturnable<Boolean> cir) {
        cir.cancel();
        StructureWorldAccess structureWorldAccess = featureContext.getWorld();
        BlockPos blockPos = featureContext.getOrigin();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int k = blockPos.getX() + i;
                int l = blockPos.getZ() + j;
                int m = structureWorldAccess.getTopY(Heightmap.Type.MOTION_BLOCKING, k, l);
                mutable.set(k, m, l);
                mutable2.set(mutable).move(Direction.DOWN, 1);
                Biome biome = structureWorldAccess.getBiome(mutable).value();
                ArrayList<BlockState> states = new ArrayList<>();
                BlockPos.iterateOutwards(mutable2, 0, 1, 0).forEach((pos) -> states.add(featureContext.getWorld().getBlockState(pos)));
                if (biome.canSetIce(structureWorldAccess, mutable2, false) && !states.contains(WWBlocks.TRAVERTINE.getDefaultState()) && !states.contains(WWBlocks.TRAVERTINE_PEACH.getDefaultState())) {
                    structureWorldAccess.setBlockState(mutable2, Blocks.ICE.getDefaultState(), 2);
                }
                if (biome.canSetSnow(structureWorldAccess, mutable) && (!states.contains(WWBlocks.TRAVERTINE.getDefaultState()) && !states.contains(WWBlocks.TRAVERTINE_PEACH.getDefaultState()))) {
                    structureWorldAccess.setBlockState(mutable, Blocks.SNOW.getDefaultState(), 2);
                    BlockState blockState = structureWorldAccess.getBlockState(mutable2);
                    if (blockState.contains(SnowyBlock.SNOWY)) {
                        structureWorldAccess.setBlockState(mutable2, blockState.with(SnowyBlock.SNOWY, true), 2);
                    }
                }
            }
        }
    }
}
