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
public class MisoSoupFoodModel<T extends Entity> extends AbstractFoodModel<T> {

    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart bone3;
    private final ModelPart bone4;
    private final ModelPart bone5;
    private final ModelPart bone6;
    public MisoSoupFoodModel(ModelPart p_170538_) {
        super(p_170538_);
        this.bone = p_170538_;
        this.bone2 = p_170538_.getChild("bone2");
        this.bone3 = p_170538_.getChild("bone3");
        this.bone4 = p_170538_.getChild("bone4");
        this.bone5 = p_170538_.getChild("bone5");
        this.bone6 = p_170538_.getChild("bone6");
    }

    public void setupAnim(T p_225597_1_, float f1, float f2, float f3, float f4, float f5) {

    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 9).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(7, 17).addBox(-2.0F, -5.0F, 2.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone4 = partdefinition.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(0, 14).addBox(2.0F, -5.0F, -3.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone5 = partdefinition.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(7, 15).addBox(-3.0F, -5.0F, -3.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone6 = partdefinition.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(11, 9).addBox(-3.0F, -5.0F, -2.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone6.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return bone;
    }
}
