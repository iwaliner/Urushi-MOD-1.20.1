package com.iwaliner.urushi.entiity.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Monster;

import java.util.function.Function;

public class GhostModel <T extends Monster> extends HumanoidModel<T> {

    public GhostModel(ModelPart p_170677_) {
        this(p_170677_,RenderType::entityTranslucent);
    }
    public GhostModel(ModelPart p_170679_, Function<ResourceLocation, RenderType> p_170680_) {
        super(p_170679_,p_170680_);
    }
    public void setupAnim(T p_102001_, float p_102002_, float p_102003_, float p_102004_, float p_102005_, float p_102006_) {
        super.setupAnim(p_102001_, p_102002_, p_102003_, p_102004_, p_102005_, p_102006_);
        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, false, this.attackTime, p_102004_);

        float rx=this.rightLeg.x;
        float ry=this.rightLeg.y;
        float rz=this.rightLeg.z;
        float lx=this.leftLeg.x;
        float ly=this.leftLeg.y;
        float lz=this.leftLeg.z;

        this.rightLeg.copyFrom(this.body);
        this.leftLeg.copyFrom(this.body);
        this.rightLeg.x=rx;
        this.rightLeg.y=ry;
        this.rightLeg.z=rz;
        this.leftLeg.x=lx;
        this.leftLeg.y=ly;
        this.leftLeg.z=lz;

    }

}

