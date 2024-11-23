package com.iwaliner.urushi.entiity.model;

import com.google.common.collect.ImmutableList;
import com.iwaliner.urushi.entiity.KakuriyoVillagerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.PathfinderMob;

import java.util.function.Function;

public class OniModel<T extends PathfinderMob> extends HumanoidModel<T> {
    private final ModelPart oniHorn1;
    private final ModelPart oniHorn2;

    public OniModel(ModelPart part) {
        this(part,RenderType::entityCutoutNoCull);
    }
    public OniModel(ModelPart part, Function<ResourceLocation, RenderType> p_170680_) {
        super(part,p_170680_);
        oniHorn1=part.getChild("horn1");
        oniHorn2=part.getChild("horn2");
    }
    public void copyPropertiesTo(HumanoidModel<T> p_102873_) {
        super.copyPropertiesTo(p_102873_);
        p_102873_.head.copyFrom(this.oniHorn1);
        p_102873_.head.copyFrom(this.oniHorn2);
    }




    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(this.head,this.oniHorn2,this.oniHorn1);
    }
    public void setupAnim(T p_102001_, float p_102002_, float p_102003_, float p_102004_, float p_102005_, float p_102006_) {
        super.setupAnim(p_102001_, p_102002_, p_102003_, p_102004_, p_102005_, p_102006_);
       // AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, false, this.attackTime, p_102004_);


        this.oniHorn2.copyFrom(this.head);
        this.oniHorn1.copyFrom(this.head);


    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        float f=0F;
        PartPose partpose = PartPose.offset(0.0F, 0F, 0.0F);
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0F)), PartPose.offset(0.0F, 0.0F + f, 0.0F));
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0F).extend(0.5F)), PartPose.offset(0.0F, 0.0F + f, 0.0F));
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0F)), PartPose.offset(0.0F, 0.0F + f, 0.0F));
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0F)), PartPose.offset(-5.0F, 2.0F + f, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0F)), PartPose.offset(5.0F, 2.0F + f, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0F)), PartPose.offset(-1.9F, 12.0F + f, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0F)), PartPose.offset(1.9F, 12.0F + f, 0.0F));

        partdefinition.addOrReplaceChild("horn1", CubeListBuilder.create().texOffs(25, 0).addBox(2F, -10F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0F)), partpose);
        partdefinition.addOrReplaceChild("horn2", CubeListBuilder.create().texOffs(25, 0).addBox(-3F, -10F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0F)), partpose);
        return LayerDefinition.create(meshdefinition, 64, 64);
    }
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderToBuffer(poseStack,vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        oniHorn1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        oniHorn2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
    public void setAllVisible(boolean p_103419_) {
        super.setAllVisible(p_103419_);
        this.oniHorn1.visible = p_103419_;
        this.oniHorn2.visible = p_103419_;

    }
}

