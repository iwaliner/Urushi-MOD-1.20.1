package com.iwaliner.urushi.entiity.food.renderer;


import com.iwaliner.urushi.ClientSetUp;
import com.iwaliner.urushi.entiity.food.ButadonFoodEntity;
import com.iwaliner.urushi.entiity.food.FoodEntity;
import com.iwaliner.urushi.entiity.food.GreenTeaFoodEntity;
import com.iwaliner.urushi.entiity.food.model.GreenTeaFoodModel;
import com.iwaliner.urushi.entiity.food.model.RiceFoodModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GreenTeaFoodRenderer<T extends GreenTeaFoodEntity> extends EntityRenderer<T> {

    public static ResourceLocation TEXTURE_LOCATION;
    public static EntityModel<FoodEntity> model ;
    public GreenTeaFoodRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new GreenTeaFoodModel<>(context.bakeLayer(ClientSetUp.GREEN_TEA));
        this.TEXTURE_LOCATION=new ResourceLocation("urushi:textures/entity/food/green_tea.png");
    }
    public void render(T p_115418_, float p_115419_, float p_115420_, PoseStack p_115421_, MultiBufferSource p_115422_, int p_115423_) {
        p_115421_.pushPose();
        p_115421_.translate(0.0D, (double)0.75F, 0.0D);
        p_115421_.mulPose(Axis.XN.rotationDegrees(180.0F));
        p_115421_.mulPose(Axis.YN.rotationDegrees(180.0F - p_115419_));
        p_115421_.scale(0.5F, 0.5F, 0.5F);
        VertexConsumer vertexconsumer = p_115422_.getBuffer(this.model.renderType(this.getTextureLocation(p_115418_)));
        this.model.renderToBuffer(p_115421_, vertexconsumer, p_115423_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_115421_.popPose();
        super.render(p_115418_, p_115419_, p_115420_, p_115421_, p_115422_, p_115423_);}


    public ResourceLocation getTextureLocation(GreenTeaFoodEntity p_110775_1_) {
        return TEXTURE_LOCATION;
    }
}