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
public class RiceFoodModel<T extends Entity> extends AbstractFoodModel<T> {


    private final ModelPart bone7;
    private final ModelPart bone8;

    private final ModelPart bone10;
    private final ModelPart bone11;
    private final ModelPart bone12;




    public RiceFoodModel(ModelPart p_170538_) {
        super(p_170538_);

        this.bone7 = p_170538_;
        this.bone8 = p_170538_.getChild("bone8");
        this.bone10 = p_170538_.getChild("bone10");
        this.bone11 = p_170538_.getChild("bone11");
        this.bone12 = p_170538_.getChild("bone12");


    }

    public void setupAnim(T p_225597_1_, float f1, float f2, float f3, float f4, float f5) {

    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bone7 = partdefinition.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(0,0).addBox(-3.0F, -5.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bone8 = partdefinition.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(18,11).addBox(-3.0F, -6.0F, 3.0F, 7.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bone10 = partdefinition.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(9,16).addBox(-4.0F, -6.0F, -3.0F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bone11 = partdefinition.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(0,11).addBox(3.0F, -6.0F, -4.0F, 1.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition bone12 = partdefinition.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(18,0).addBox(-4.0F, -6.0F, -4.0F, 7.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone7.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone8.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone10.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone11.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone12.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        }

    @Override
    public ModelPart root() {
        return bone7;
    }
}
