package me.kaloyankys.wilderworld.entity.render;

import me.kaloyankys.wilderworld.client.WilderworldClient;
import me.kaloyankys.wilderworld.entity.DoodEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DoodEntityRenderer extends MobEntityRenderer<DoodEntity, DoodModel> {
    public static final Identifier[] TEXTURES = {
            new Identifier("wilderworld", "textures/entity/dood/dood.png"),
            new Identifier("wilderworld", "textures/entity/dood/chewing.png"),
            new Identifier("wilderworld", "textures/entity/dood/spitting.png")
    };

    public DoodEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new DoodModel(context.getPart(WilderworldClient.DOOD_RENDER_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(DoodEntity entity) {
        return entity.currentTexture;
    }

    @Override
    public void render(DoodEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        shadowRadius = 0.5F;
        matrixStack.push();
        if (entity.isBaby()) {
            matrixStack.scale(0.3F, 0.3F, 0.3F);
            shadowRadius = 0.25F;
        } else {
            matrixStack.scale(2.0F, 2.0F, 2.0F);
        }
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }
}
