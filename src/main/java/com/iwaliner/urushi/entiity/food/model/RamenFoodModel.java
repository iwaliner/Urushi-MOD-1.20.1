package com.iwaliner.urushi.entiity.food.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class RamenFoodModel<T extends Entity> extends AbstractFoodModel<T> {

    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart bone3;
    private final ModelPart bone7;
    private final ModelPart bone8;
    private final ModelPart bone9;
    private final ModelPart bone10;
    private final ModelPart bone11;
    private final ModelPart bone12;
    private final ModelPart bone13;
    private final ModelPart bone14;
    private final ModelPart bone15;
    private final ModelPart bone16;


    public RamenFoodModel(ModelPart p_170538_) {
        super(p_170538_);
        this.bone = p_170538_;
        this.bone2 = p_170538_.getChild("bone2");
        this.bone3 = p_170538_.getChild("bone3");
        this.bone7 = p_170538_.getChild("bone7");
        this.bone8 = p_170538_.getChild("bone8");
        this.bone9 = p_170538_.getChild("bone9");
        this.bone10 = p_170538_.getChild("bone10");
        this.bone11 = p_170538_.getChild("bone11");
        this.bone12 = p_170538_.getChild("bone12");
        this.bone13 = p_170538_.getChild("bone13");
        this.bone14 = p_170538_.getChild("bone14");
        this.bone15 = p_170538_.getChild("bone15");
        this.bone16 = p_170538_.getChild("bone16");

    }

    public void setupAnim(T p_225597_1_, float f1, float f2, float f3, float f4, float f5) {

    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 0).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -4.0F, 2.0F,0.7854F, 0.0F, 0.0F));

        PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -6.0F, -3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition cube_r2 = bone3.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 29).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -5.0F, 2.0F,0.0F, -0.4363F, 0.0F));

        PartDefinition bone7 = partdefinition.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(22, 9).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bone8 = partdefinition.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(15,28).addBox(-3.0F, -3.0F, 3.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bone9 = partdefinition.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(9,24).addBox(-4.0F, -6.0F, 4.0F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bone10 = partdefinition.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(24,0).addBox(-4.0F, -3.0F, -3.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bone11 = partdefinition.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(0,21).addBox(3.0F, -3.0F, -4.0F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bone12 = partdefinition.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(11,9).addBox(-4.0F, -3.0F, -4.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bone13 = partdefinition.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(11,12).addBox(-5.0F, -6.0F, -4.0F, 1.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bone14 = partdefinition.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(22,17).addBox(-5.0F, -6.0F, -5.0F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bone15 = partdefinition.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(0,9).addBox(4.0F, -6.0F, -5.0F, 1.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bone16 = partdefinition.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(0,0).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone7.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone8.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone9.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone10.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone11.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone12.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone13.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone14.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone15.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone16.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return bone;
    }
}
