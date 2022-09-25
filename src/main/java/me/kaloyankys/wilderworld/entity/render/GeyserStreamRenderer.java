package me.kaloyankys.wilderworld.entity.render;

import me.kaloyankys.wilderworld.client.WilderworldClient;
import me.kaloyankys.wilderworld.entity.GeyserStreamEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GeyserStreamRenderer extends MobEntityRenderer<GeyserStreamEntity, GeyserStreamModel> {
    private static final Identifier TEXTURE = new Identifier("wilderworld", "textures/entity/geyser/geyser_stream.png");

    public GeyserStreamRenderer(EntityRendererFactory.Context context) {
        super(context, new GeyserStreamModel(context.getPart(WilderworldClient.GEYSER_STREAM_RENDER_LAYER)), 0.5f);
    }

    @Override
    public void render(GeyserStreamEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(entity.getExtent() * 3, entity.getExtent() * entity.getSize() * 4, entity.getExtent() * 3);
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

    @Override
    public Identifier getTexture(GeyserStreamEntity entity) {
        return TEXTURE;
    }
}
