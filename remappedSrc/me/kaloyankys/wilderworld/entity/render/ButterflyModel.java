package me.kaloyankys.wilderworld.entity.render;

import me.kaloyankys.wilderworld.entity.ButterflyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class ButterflyModel extends SinglePartEntityModel<ButterflyEntity> {
    private final ModelPart root;

    private final ModelPart body;
	private final ModelPart cube_r1;
	private final ModelPart wing1;
	private final ModelPart cube_r2;
	private final ModelPart wing2;
	private final ModelPart cube_r3;
	private final ModelPart antennae;
	private final ModelPart cube_r4;
	private final ModelPart body2;
	private final ModelPart cube_r5;
	private final ModelPart body3;
	private final ModelPart cube_r6;
	private float bodyPitch;

    public ButterflyModel(ModelPart root) {
        this.root = root;
		this.body = root.getChild("body");
		this.cube_r1 = body.getChild("cube_r1");
		this.wing1 = root.getChild("wing1");
		this.cube_r2 = wing1.getChild("cube_r2");
		this.wing2 = root.getChild("wing2");
		this.cube_r3 = wing2.getChild("cube_r3");
		this.antennae = root.getChild("antennae");
		this.cube_r4 = antennae.getChild("cube_r4");
		this.body2 = root.getChild("body2");
		this.cube_r5 = body2.getChild("cube_r5");
		this.body3 = root.getChild("body3");
		this.cube_r6 = body3.getChild("cube_r6");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData root = data.getRoot();

        ModelPartData body = root.addChild(
		    "body",
		    ModelPartBuilder.create()
		        ,
		    ModelTransform.of(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData cube_r1 = body.addChild(
		    "cube_r1",
		    ModelPartBuilder.create()
		        .uv(14, 13)
		        .mirrored(false)
		        .cuboid(-2.0F, -2.0F, -7.0F, 5.0F, 4.0F, 13.0F, new Dilation(0.0F)),
		    ModelTransform.of(0.0F, -2.0F, -2.0F, -3.1416F, 0.0F, 3.1416F)
		);

		ModelPartData wing1 = root.addChild(
		    "wing1",
		    ModelPartBuilder.create()
		        ,
		    ModelTransform.of(-3.0F, 20.0F, -2.0F, 0.0F, 0.0F, 0.6109F)
		);

		ModelPartData cube_r2 = wing1.addChild(
		    "cube_r2",
		    ModelPartBuilder.create()
		        .uv(11, 48)
		        .mirrored(false)
		        .cuboid(2.0F, -4.0F, -10.0F, 10.0F, 0.0F, 16.0F, new Dilation(0.0F)),
		    ModelTransform.of(3.0F, 4.0F, -1.0F, -3.1416F, 0.0F, 3.1416F)
		);

		ModelPartData wing2 = root.addChild(
		    "wing2",
		    ModelPartBuilder.create()
		        ,
		    ModelTransform.of(2.0F, 20.0F, -2.0F, 0.0F, 0.0F, -0.6109F)
		);

		ModelPartData cube_r3 = wing2.addChild(
		    "cube_r3",
		    ModelPartBuilder.create()
		        .uv(10, 31)
		        .mirrored(false)
		        .cuboid(-12.0F, -4.0F, -10.0F, 10.0F, 0.0F, 16.0F, new Dilation(0.0F)),
		    ModelTransform.of(-2.0F, 4.0F, -1.0F, -3.1416F, 0.0F, 3.1416F)
		);

		ModelPartData antennae = root.addChild(
		    "antennae",
		    ModelPartBuilder.create()
		        ,
		    ModelTransform.of(-1.0F, 20.0F, -8.0F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData cube_r4 = antennae.addChild(
		    "cube_r4",
		    ModelPartBuilder.create()
		        .uv(0, 0)
		        .mirrored(false)
		        .cuboid(2.0F, -5.0F, 5.0F, 0.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 2)
		        .mirrored(false)
		        .cuboid(-1.0F, -5.0F, 5.0F, 0.0F, 2.0F, 3.0F, new Dilation(0.0F)),
		    ModelTransform.of(1.0F, 4.0F, 5.0F, -3.1416F, 0.0F, 3.1416F)
		);

		ModelPartData body2 = root.addChild(
		    "body2",
		    ModelPartBuilder.create()
		        ,
		    ModelTransform.of(-2.0F, 24.0F, -3.0F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData cube_r5 = body2.addChild(
		    "cube_r5",
		    ModelPartBuilder.create()
		        .uv(0, 17)
		        .mirrored(false)
		        .cuboid(3.0F, 0.0F, -5.0F, 0.0F, 2.0F, 13.0F, new Dilation(0.0F)),
		    ModelTransform.of(3.0F, 0.0F, 3.0F, -3.1416F, 0.0F, 3.1416F)
		);

		ModelPartData body3 = root.addChild(
		    "body3",
		    ModelPartBuilder.create()
		        ,
		    ModelTransform.of(1.0F, 24.0F, -2.0F, 0.0F, 0.0F, 0.0F)
		);

		ModelPartData cube_r6 = body3.addChild(
		    "cube_r6",
		    ModelPartBuilder.create()
		        .uv(0, 17)
		        .mirrored(false)
		        .cuboid(3.0F, 0.0F, -8.0F, 0.0F, 2.0F, 13.0F, new Dilation(0.0F)),
		    ModelTransform.of(3.0F, 0.0F, -1.0F, -3.1416F, 0.0F, 3.1416F)
		);

        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public void setAngles(ButterflyEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		if (entity.age % 2 == 0) {
			wing1.roll = MathHelper.cos(0.436332F) * 3.1415927F * 0.15F;
			wing2.roll = MathHelper.cos(0.436332F) * 3.1415927F * 0.15F;
		} else {
			wing1.roll = -MathHelper.cos(0.436332F) * 3.1415927F * 0.15F;
			wing2.roll = -MathHelper.cos(0.436332F) * 3.1415927F * 0.15F;
		}
		this.body.pitch = 0.1F + MathHelper.cos(animationProgress * 0.18F) * 3.1415927F * 0.025F;
		this.wing1.pitch = 0.1F + MathHelper.cos(animationProgress * 0.18F) * 3.1415927F * 0.025F;
		this.wing2.pitch = 0.1F + MathHelper.cos(animationProgress * 0.18F) * 3.1415927F * 0.025F;
		this.antennae.pitch = 0.1F + MathHelper.cos(animationProgress * 0.18F) * 3.1415927F * 0.025F;
		this.body2.pitch = 0.1F + MathHelper.cos(animationProgress * 0.18F) * 3.1415927F * 0.025F;
		this.body3.pitch = 0.1F + MathHelper.cos(animationProgress * 0.18F) * 3.1415927F * 0.025F;

		if (this.bodyPitch > 0.0F) {
			this.body.pitch = ModelUtil.interpolateAngle(this.body.pitch, 3.0915928F * 2, this.bodyPitch);
			this.wing1.pitch = ModelUtil.interpolateAngle(this.wing1.pitch, 3.0915928F * 2, this.bodyPitch);
			this.wing2.pitch = ModelUtil.interpolateAngle(this.wing2.pitch, 3.0915928F * 2, this.bodyPitch);
			this.antennae.pitch = ModelUtil.interpolateAngle(this.antennae.pitch, 3.0915928F * 2, this.bodyPitch);
			this.body2.pitch = ModelUtil.interpolateAngle(this.body2.pitch, 3.0915928F * 2, this.bodyPitch);
			this.body3.pitch = ModelUtil.interpolateAngle(this.body3.pitch, 3.0915928F * 2, this.bodyPitch);
		}
	}

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}