package me.kaloyankys.wilderworld.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    private float skyDarkness;

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private float viewDistance;

    /* @Inject(at = @At("HEAD"), method = "getViewDistance")
    private void getViewDistance(CallbackInfoReturnable<Float> cir) {
        if (this.client.player != null && this.client.player.world.getRegistryKey() == World.OVERWORLD &&
                this.client.player.world.getBiome(this.client.player.getBlockPos()).getCategory() == Biome.Category.ICY) {
            this.viewDistance = viewDistance - 0.3f;
            this.skyDarkness = skyDarkness - 0.3f;
        }
    } */
}
