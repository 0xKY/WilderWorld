package me.kaloyankys.wilderworld.entity.render;

import com.google.common.collect.ImmutableList;
import me.kaloyankys.wilderworld.entity.GeyserStreamEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class GeyserStreamModel extends EntityModel<GeyserStreamEntity> {
    private final ModelPart body;

    public GeyserStreamModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored(false)
                        .cuboid(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, new Dilation(0.0F))
                        .uv(0, 24)
                        .mirrored(false)
                        .cuboid(-6.0F, -13.0F, -6.0F, 12.0F, 12.0F, 12.0F, new Dilation(1.0F)),
                ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void setAngles(GeyserStreamEntity entity, float f, float g, float h, float i, float j) {
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
        ImmutableList.of(this.body).forEach((modelRenderer) -> modelRenderer.render(matrixStack, vertexConsumer, i, j, f, g, h, k));
    }
}
