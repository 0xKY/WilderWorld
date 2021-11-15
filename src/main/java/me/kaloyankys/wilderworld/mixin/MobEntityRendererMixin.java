package me.kaloyankys.wilderworld.mixin;

import com.google.common.collect.Lists;
import me.kaloyankys.wilderworld.client.GlowingEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MobEntityRenderer.class)
public abstract class MobEntityRendererMixin<T extends MobEntity, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {

    private final List<FeatureRenderer<?, ?>> features = Lists.newArrayList();

    public MobEntityRendererMixin(EntityRendererFactory.Context context, M entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    private void mobEntityRendererMixin(EntityRendererFactory.Context context, M entityModel, float f, CallbackInfo ci) {
        if (context.getRenderDispatcher().targetedEntity != null) {
            features.add(new GlowingEntityRenderer(this, context.getRenderDispatcher().targetedEntity));
        }
    }
}
