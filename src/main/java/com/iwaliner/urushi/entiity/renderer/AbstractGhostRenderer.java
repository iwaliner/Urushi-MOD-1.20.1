package com.iwaliner.urushi.entiity.renderer;

import com.iwaliner.urushi.entiity.GhostEntity;
import com.iwaliner.urushi.entiity.model.GhostModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;


public abstract class AbstractGhostRenderer<T extends GhostEntity, M extends GhostModel<T>> extends HumanoidMobRenderer<T,M> {

    protected AbstractGhostRenderer(EntityRendererProvider.Context p_173910_, M p_173911_, M p_173912_, M p_173913_) {
        super(p_173910_, p_173911_, 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, p_173912_, p_173913_, p_173910_.getModelManager()));
    }



}
