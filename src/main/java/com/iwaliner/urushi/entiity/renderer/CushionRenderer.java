package com.iwaliner.urushi.entiity.renderer;

import com.iwaliner.urushi.ClientSetUp;
import com.iwaliner.urushi.entiity.CushionEntity;
import com.iwaliner.urushi.entiity.model.CushionModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class CushionRenderer<T extends CushionEntity> extends EntityRenderer<T> {
    private static final ResourceLocation[] TEXTURE_LOCATIONS = new ResourceLocation[]{new ResourceLocation("urushi:textures/entity/cushion/cushion_white.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_orange.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_magenta.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_light_blue.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_yellow.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_lime.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_pink.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_gray.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_silver.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_cyan.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_purple.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_blue.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_brown.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_green.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_red.png"), new ResourceLocation("urushi:textures/entity/cushion/cushion_black.png")};

      public final EntityModel<CushionEntity> model;

    public CushionRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CushionModel<>(context.bakeLayer(ClientSetUp.CUSHION));
    }
    public void render(CushionEntity entity, float p_225623_2_, float p_225623_3_, PoseStack p_225623_4_, MultiBufferSource p_225623_5_, int p_225623_6_) {
        p_225623_4_.pushPose();
        p_225623_4_.mulPose(Axis.XN.rotationDegrees(180.0F));
        p_225623_4_.translate(0.0D, (double) -1.5F, 0.0D);
        p_225623_4_.scale(1F, 1F, 1F);

        p_225623_4_.mulPose(Axis.YN.rotationDegrees(180.0F - p_225623_2_)); //この行がないとモデルの回転ができない

        VertexConsumer ivertexbuilder = p_225623_5_.getBuffer(this.model.renderType(TEXTURE_LOCATIONS[entity.getCushionType().ordinal()]));
        this.model.renderToBuffer(p_225623_4_, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_225623_4_.popPose();
        super.render((T) entity, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }


    public ResourceLocation getTextureLocation(CushionEntity p_110775_1_) {
        return TEXTURE_LOCATIONS[p_110775_1_.getCushionType().ordinal()];
    }
}
