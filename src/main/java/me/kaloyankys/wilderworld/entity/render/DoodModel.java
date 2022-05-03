// Made with Blockbench 4.1.1
// Exported for Minecraft version 1.17
// Paste this class into your mod and generate all required imports

package me.kaloyankys.wilderworld.entity.render;

import me.kaloyankys.wilderworld.entity.DoodEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class DoodModel extends SinglePartEntityModel<DoodEntity> {
    private final ModelPart root;

    private final ModelPart flipper1;
    private final ModelPart flipper2;
    private final ModelPart lleg;
    private final ModelPart rleg;
    private final ModelPart fin1;
    private final ModelPart fin2;
    private final ModelPart body;

    public DoodModel(ModelPart root) {
        this.root = root;
        this.flipper1 = root.getChild("flipper1");
        this.flipper2 = root.getChild("flipper2");
        this.body = root.getChild("body");
        this.rleg = root.getChild("rleg");
        this.lleg = root.getChild("lleg");
        this.fin1 = root.getChild("fin1");
        this.fin2 = root.getChild("fin2");
    }

    public static TexturedModelData getTexturedModelData() {

        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData flipper1 = root.addChild(
                "flipper1",
                ModelPartBuilder.create()
                        .uv(0, 0) // 12, 7
                        .mirrored(false)
                        .cuboid(6.5F, 5.0F, -1.0F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.of(-2.0F, 19.0F, -2.0F, 0.0F, 0.0F, 0.2182F)
        );

        ModelPartData flipper2 = root.addChild(
                "flipper2",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored(false)
                        .cuboid(-4.5F, 5.0F, 0.0F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.of(2.0F, 19.0F, -2.0F, 0.0F, 0.0F, -0.2182F)
        );

        ModelPartData lleg = root.addChild(
                "lleg",
                ModelPartBuilder.create()
                        .uv(1, 0)
                        .mirrored(true)
                        .cuboid(-0.5F, 22.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F))
                        .uv(-1, 9)
                        .mirrored(false)
                        .cuboid(-1.0F, 24.0F, -2.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.of(1.0F, 22.0F, -2.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData rleg = root.addChild(
                "rleg",
                ModelPartBuilder.create()
                        .uv(1, 0)
                        .mirrored(false)
                        .cuboid(-0.5F, 22.0F, 0.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F))
                        .uv(-1, 9)
                        .mirrored(false)
                        .cuboid(-1.5F, 24.0F, -2.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.of(-1.0F, 22.0F, -2.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData fin1 = root.addChild(
                "fin1",
                ModelPartBuilder.create()
                        .uv(12, 0)
                        .mirrored(false)
                        .cuboid(0.0F, -1.5F, 0.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 21.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData fin2 = root.addChild(
                "fin2",
                ModelPartBuilder.create()
                        .uv(12, 0)
                        .mirrored(false)
                        .cuboid(0.0F, -1.5F, 0.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.of(-0.5F, 21.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        ModelPartData body = root.addChild(
                "body",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .mirrored(false)
                        .cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 19.0F, -2.0F, 0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(data, 16, 16);
    }

    @Override
    public void setAngles(DoodEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        limbDistance = Math.min(0.25f, limbDistance);
        float l = MathHelper.cos(limbDistance * 1.5f + (float) Math.PI) * limbDistance;

        this.body.roll = 0.5f * MathHelper.sin(limbAngle) * 2.0f * limbDistance;

        this.flipper1.roll = -0.87266463f;
        this.flipper2.roll = 0.87266463f;
        this.flipper1.roll += l * 0.3f;
        this.flipper1.roll += 0.1f * MathHelper.sin(animationProgress * 0.5f * 0.2f);
        this.flipper2.roll += 0.1f * MathHelper.sin(animationProgress * 0.5f * 0.2f);


        this.fin1.yaw = -0.87266463f;
        this.fin2.yaw = 0.87266463f;
        this.fin1.yaw += l * 0.3f;
        this.fin1.yaw += 0.1f * MathHelper.sin(animationProgress * 0.5f * 0.2f);
        this.fin2.yaw += 0.1f * MathHelper.sin(animationProgress * 0.5f * 0.2f);

        this.lleg.pitch = MathHelper.sin(limbAngle) * limbDistance;
        this.rleg.pitch = MathHelper.sin(limbAngle + (float) Math.PI) * limbDistance;
        this.lleg.roll = 0.17453292f * MathHelper.cos(limbAngle) * limbDistance;
        this.rleg.roll = 0.17453292f * MathHelper.cos(limbAngle + (float) Math.PI) * limbDistance;
        this.lleg.pivotY = 1.05f * MathHelper.sin(limbAngle + (float) Math.PI) * limbDistance;
        this.rleg.pivotY = 1.05f * MathHelper.sin(limbAngle) * limbDistance;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}