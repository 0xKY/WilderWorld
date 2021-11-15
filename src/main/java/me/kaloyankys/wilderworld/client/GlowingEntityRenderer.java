package me.kaloyankys.wilderworld.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;

public class GlowingEntityRenderer extends EyesFeatureRenderer {

    private static Entity glower;
    private final RenderLayer SKIN = this.getContextModel().getLayer(this.getTexture(glower));

    public GlowingEntityRenderer(FeatureRendererContext featureRendererContext, Entity entity) {
        super(featureRendererContext);
        glower = entity;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Entity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.getEyesTexture());
        LivingEntity livingEntity = (LivingEntity) entity;
        if (livingEntity.hasStatusEffect(StatusEffects.GLOWING)) {
           this.getContextModel().render(matrices, vertexConsumer,
                    15728640, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}
