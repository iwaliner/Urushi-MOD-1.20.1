package com.iwaliner.urushi.entiity.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;


public class CushionModel<T extends Entity> extends HierarchicalModel<T> {

    private final ModelPart box;
    public CushionModel(ModelPart p_170555_) {
        this.box = p_170555_;
    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("box", CubeListBuilder.create().texOffs(0, 0).addBox(-6F, 0.0F, -6F, 12F, 3F, 12.0F), PartPose.offset(0F, 21.0F, 0F));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public void setupAnim(T p_102632_, float p_102633_, float p_102634_, float p_102635_, float p_102636_, float p_102637_) {

    }

    public ModelPart root() {
        return this.box;
    }

}
