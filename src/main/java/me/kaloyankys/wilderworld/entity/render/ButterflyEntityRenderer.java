package me.kaloyankys.wilderworld.entity.render;

import me.kaloyankys.wilderworld.client.WilderworldClient;
import me.kaloyankys.wilderworld.entity.ButterflyEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ButterflyEntityRenderer extends MobEntityRenderer<ButterflyEntity, ButterflyModel> {

    private static final Identifier[] TEXTURE = {
            new Identifier("wilderworld", "textures/entity/butterfly/flame.png"),
            new Identifier("wilderworld", "textures/entity/butterfly/sap.png"),
            new Identifier("wilderworld", "textures/entity/butterfly/aqua.png"),
            new Identifier("wilderworld", "textures/entity/butterfly/pixie.png")
    };

    private static final Identifier ROXANNE = new Identifier("wilderworld", "textures/entity/butterfly/roxanne.png");

    public ButterflyEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ButterflyModel(context.getPart(WilderworldClient.BUTTERFLY_RENDER_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(ButterflyEntity entity) {
        if (entity.getCustomName() != null && entity.getCustomName().asString().equals("Roxanne")) {
            return ROXANNE;
        } else if (!entity.isBaby()) {
            return TEXTURE[entity.getVariant() % TEXTURE.length];
        } else {
            return TEXTURE[entity.getVariant() % TEXTURE.length];
        }
    }

    @Override
    public void render(ButterflyEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        shadowRadius = 0.5F;
        matrixStack.push();
        if (mobEntity.isBaby()) {
            matrixStack.scale(0.3F, 0.3F, 0.3F);
            shadowRadius = 0.25F;
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }
}
