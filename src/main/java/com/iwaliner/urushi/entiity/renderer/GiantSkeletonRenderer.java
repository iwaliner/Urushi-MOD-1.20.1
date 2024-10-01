package com.iwaliner.urushi.entiity.renderer;

import com.iwaliner.urushi.entiity.GiantSkeletonEntity;
import com.iwaliner.urushi.entiity.model.GiantSkeletonModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GiantSkeletonRenderer extends MobRenderer<GiantSkeletonEntity, HumanoidModel<GiantSkeletonEntity>> {
    private static final ResourceLocation ZOMBIE_LOCATION = new ResourceLocation("urushi:textures/entity/giant_skeleton.png");
    private final float scale;

    public GiantSkeletonRenderer(EntityRendererProvider.Context p_174131_) {
        super(p_174131_, new GiantSkeletonModel(p_174131_.bakeLayer(ModelLayers.GIANT)), 0.5F *5f);
        this.scale = 5f;
        this.addLayer(new ItemInHandLayer<>(this, p_174131_.getItemInHandRenderer()));
        //this.addLayer(new HumanoidArmorLayer<>(this, new GiantZombieModel(p_174131_.bakeLayer(ModelLayers.GIANT_INNER_ARMOR)), new GiantZombieModel(p_174131_.bakeLayer(ModelLayers.GIANT_OUTER_ARMOR)), p_174131_.getModelManager()));
    }

    protected void scale(GiantSkeletonEntity p_114775_, PoseStack p_114776_, float p_114777_) {
        p_114776_.scale(this.scale, this.scale, this.scale);
    }

    public ResourceLocation getTextureLocation(GiantSkeletonEntity p_114773_) {
        return ZOMBIE_LOCATION;
    }
}
