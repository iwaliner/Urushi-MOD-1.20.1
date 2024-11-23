package com.iwaliner.urushi.entiity.renderer;

import com.iwaliner.urushi.ClientSetUp;
import com.iwaliner.urushi.entiity.KakuriyoVillagerEntity;
import com.iwaliner.urushi.entiity.model.OniModel;
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
public class KakuriyoVillagerRenderer extends MobRenderer<KakuriyoVillagerEntity, HumanoidModel<KakuriyoVillagerEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation("urushi:textures/entity/oni_girl.png");


    public KakuriyoVillagerRenderer(EntityRendererProvider.Context p_174131_) {
        super(p_174131_, new OniModel<>(p_174131_.bakeLayer(ClientSetUp.KAKURIYO_VILLAGER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, p_174131_.getItemInHandRenderer()));
    }


    public ResourceLocation getTextureLocation(KakuriyoVillagerEntity p_114773_) {
        return LOCATION;
    }
}
