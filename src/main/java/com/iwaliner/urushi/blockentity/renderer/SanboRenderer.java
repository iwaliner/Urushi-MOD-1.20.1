package com.iwaliner.urushi.blockentity.renderer;

import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.blockentity.SanboBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SanboRenderer implements BlockEntityRenderer<SanboBlockEntity> {

    public SanboRenderer(BlockEntityRendererProvider.Context p_173602_) {
    }

    public void render(SanboBlockEntity blockEntity, float f1, PoseStack poseStack, MultiBufferSource bufferSource, int i1, int i2) {
        Direction direction = blockEntity.getBlockState().getValue(CampfireBlock.FACING);
        ItemStack itemstack = blockEntity.getDisplayingStack();
        int i = (int)blockEntity.getBlockPos().asLong();



            if (!itemstack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5D, 0.0625D*14, 0.5D);
                Direction direction1 = Direction.from2DDataValue((direction.get2DDataValue()) % 4);
                float f = -direction1.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(f));
                //poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                poseStack.scale(0.5F, 0.5F, 0.5F);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.FIXED, i1, OverlayTexture.NO_OVERLAY, poseStack, bufferSource,blockEntity.getLevel(), i);
                poseStack.popPose();
            }


            /**以下、バニラのビーコンビームを使った実験の跡。*/
/*
            int i3=1;
            int hight=10;
            float[] color=new float[3]; //色をRGB指定
            color[0]=0.9f;//Red
            color[1]=0f;  //Green
            color[2]=0f;  //Blue
            float innerCoreLayerSize=0.02F; //内側の不透明な部分の大きさ。
            float outerTranslucentLayerSize=0.04F; //外側の半透明な部分の大きさ。
            float aspectRatio=1F; //テクスチャのアスペクト比を指定。1Fがデフォルトで、1未満だとテクスチャが画像縦方向に伸びる。

        ResourceLocation texture=new ResourceLocation(ModCoreUrushi.ModID,"textures/block/plaster_namako.png");
        renderBeam(poseStack,bufferSource,texture,f1,aspectRatio,blockEntity.getLevel().getGameTime(),i3,hight,color, innerCoreLayerSize, outerTranslucentLayerSize);
    */
    }

    /**以下、バニラのビーコンビームを使った実験の跡。*/
/*
    public static void renderBeam(PoseStack p_112185_, MultiBufferSource p_112186_, ResourceLocation p_112187_, float p_112188_, float p_112189_, long p_112190_, int p_112191_, int p_112192_, float[] p_112193_, float p_112194_, float p_112195_) {
        int i = p_112191_ + p_112192_;
        p_112185_.pushPose();
        p_112185_.translate(0.5D, 0.0D, 0.5D);
        float f = (float)Math.floorMod(p_112190_, 40) + p_112188_;
        float f1 = p_112192_ < 0 ? f : -f;
        float f2 = Mth.frac(f1 * 0.2F - (float)Mth.floor(f1 * 0.1F));
        float f3 = p_112193_[0];
        float f4 = p_112193_[1];
        float f5 = p_112193_[2];
        p_112185_.pushPose();
        p_112185_.mulPose(Axis.XP.rotationDegrees(90.0F)); //バニラのBeaconRenderer.javaにはない行。ここで90度回転させて地面と平行にしている。
        p_112185_.mulPose(Axis.YP.rotationDegrees(f * 2.25F - 45.0F));
        float f6 = 0.0F;
        float f8 = 0.0F;
        float f9 = -p_112194_;
        float f10 = 0.0F;
        float f11 = 0.0F;
        float f12 = -p_112194_;
        float f13 = 0.0F;
        float f14 = 1.0F;
        float f15 = -1.0F + f2;
        float f16 = (float)p_112192_ * p_112189_ * (0.5F / p_112194_) + f15;
        renderPart(p_112185_, p_112186_.getBuffer(RenderType.beaconBeam(p_112187_, false)), f3, f4, f5, 1.0F, p_112191_, i, 0.0F, p_112194_, p_112194_, 0.0F, f9, 0.0F, 0.0F, f12, 0.0F, 1.0F, f16, f15);
        p_112185_.popPose();
        f6 = -p_112195_;
        float f7 = -p_112195_;
        f8 = -p_112195_;
        f9 = -p_112195_;
        f13 = 0.0F;
        f14 = 1.0F;
        f15 = -1.0F + f2;
        f16 = (float)p_112192_ * p_112189_ + f15;
        renderPart(p_112185_, p_112186_.getBuffer(RenderType.beaconBeam(p_112187_, true)), f3, f4, f5, 0.125F, p_112191_, i, f6, f7, p_112195_, f8, f9, p_112195_, p_112195_, p_112195_, 0.0F, 1.0F, f16, f15);
        p_112185_.popPose();
    }

    private static void renderPart(PoseStack p_112156_, VertexConsumer p_112157_, float p_112158_, float p_112159_, float p_112160_, float p_112161_, int p_112162_, int p_112163_, float p_112164_, float p_112165_, float p_112166_, float p_112167_, float p_112168_, float p_112169_, float p_112170_, float p_112171_, float p_112172_, float p_112173_, float p_112174_, float p_112175_) {
        PoseStack.Pose posestack$pose = p_112156_.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        renderQuad(matrix4f, matrix3f, p_112157_, p_112158_, p_112159_, p_112160_, p_112161_, p_112162_, p_112163_, p_112164_, p_112165_, p_112166_, p_112167_, p_112172_, p_112173_, p_112174_, p_112175_);
        renderQuad(matrix4f, matrix3f, p_112157_, p_112158_, p_112159_, p_112160_, p_112161_, p_112162_, p_112163_, p_112170_, p_112171_, p_112168_, p_112169_, p_112172_, p_112173_, p_112174_, p_112175_);
        renderQuad(matrix4f, matrix3f, p_112157_, p_112158_, p_112159_, p_112160_, p_112161_, p_112162_, p_112163_, p_112166_, p_112167_, p_112170_, p_112171_, p_112172_, p_112173_, p_112174_, p_112175_);
        renderQuad(matrix4f, matrix3f, p_112157_, p_112158_, p_112159_, p_112160_, p_112161_, p_112162_, p_112163_, p_112168_, p_112169_, p_112164_, p_112165_, p_112172_, p_112173_, p_112174_, p_112175_);
    }

    private static void renderQuad(Matrix4f p_112120_, Matrix3f p_112121_, VertexConsumer p_112122_, float p_112123_, float p_112124_, float p_112125_, float p_112126_, int p_112127_, int p_112128_, float p_112129_, float p_112130_, float p_112131_, float p_112132_, float p_112133_, float p_112134_, float p_112135_, float p_112136_) {
        addVertex(p_112120_, p_112121_, p_112122_, p_112123_, p_112124_, p_112125_, p_112126_, p_112128_, p_112129_, p_112130_, p_112134_, p_112135_);
        addVertex(p_112120_, p_112121_, p_112122_, p_112123_, p_112124_, p_112125_, p_112126_, p_112127_, p_112129_, p_112130_, p_112134_, p_112136_);
        addVertex(p_112120_, p_112121_, p_112122_, p_112123_, p_112124_, p_112125_, p_112126_, p_112127_, p_112131_, p_112132_, p_112133_, p_112136_);
        addVertex(p_112120_, p_112121_, p_112122_, p_112123_, p_112124_, p_112125_, p_112126_, p_112128_, p_112131_, p_112132_, p_112133_, p_112135_);
    }

    private static void addVertex(Matrix4f p_112107_, Matrix3f p_112108_, VertexConsumer p_112109_, float p_112110_, float p_112111_, float p_112112_, float p_112113_, int p_112114_, float p_112115_, float p_112116_, float p_112117_, float p_112118_) {
        p_112109_.vertex(p_112107_, p_112115_, (float)p_112114_, p_112116_).color(p_112110_, p_112111_, p_112112_, p_112113_).uv(p_112117_, p_112118_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_112108_, 0.0F, 1.0F, 0.0F).endVertex();
    }*/
}