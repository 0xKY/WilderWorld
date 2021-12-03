package me.kaloyankys.wilderworld.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private float viewDistance;

    @Inject(at = @At("HEAD"), method = "getViewDistance")
    private void getViewDistance(CallbackInfoReturnable<Float> cir) {
        if (this.client.player != null && this.client.player.world.getRegistryKey() == World.OVERWORLD) {
            float darkness = ((float) this.client.player.getY()) * -1;
            this.viewDistance /= 2;
            this.viewDistance -= darkness;
        }
    }
}
