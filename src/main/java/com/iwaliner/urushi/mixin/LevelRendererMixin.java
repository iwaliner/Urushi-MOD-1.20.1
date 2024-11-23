package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.DimensionRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.block.SlideDoorBlock;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)

public abstract class LevelRendererMixin {
    private static final ResourceLocation KAKURIYO_CLOUDS_LOCATION = new ResourceLocation(ModCoreUrushi.ModID,"textures/environment/kakuriyo_clouds.png");

    @Shadow @Nullable private ClientLevel level;

    @Shadow private int ticks;

    @Shadow private int prevCloudX;

    @Shadow private int prevCloudY;

    @Shadow private int prevCloudZ;

    @Shadow @Final private Minecraft minecraft;

    @Shadow @Nullable private CloudStatus prevCloudsType;

    @Shadow private Vec3 prevCloudColor;

    @Shadow private boolean generateClouds;

    @Shadow @Nullable private VertexBuffer cloudBuffer;

    @Shadow protected abstract BufferBuilder.RenderedBuffer buildClouds(BufferBuilder p_234262_, double p_234263_, double p_234264_, double p_234265_, Vec3 p_234266_);

    @Inject(method = "renderClouds",at = @At("HEAD"), cancellable = true)
    private void renderCloudsInject(PoseStack poseStack, Matrix4f matrix4f, float f01, double d01, double d02, double d03, CallbackInfo ci){
       if(this.level!=null&&this.level.dimension()== DimensionRegister.KakuriyoKey) {
           if (this.level.effects().renderClouds(level, ticks, f01, poseStack, d01, d02, d03, matrix4f))
               return;
           float f = this.level.effects().getCloudHeight();
           if (!Float.isNaN(f)) {
               RenderSystem.disableCull();
               RenderSystem.enableBlend();
               RenderSystem.enableDepthTest();
               RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
               RenderSystem.depthMask(true);
               float f1 = 12.0F;
               float f2 = 4.0F;
               double d0 = 2.0E-4D;
               double d1 = (double) (((float) this.ticks + f01) * 0.03F);
               double d2 = (d01 + d1) / 12.0D;
               double d3 = (double) (f - (float) d02 + 0.33F);
               double d4 = d03 / 12.0D + (double) 0.33F;
               d2 -= (double) (Mth.floor(d2 / 2048.0D) * 2048);
               d4 -= (double) (Mth.floor(d4 / 2048.0D) * 2048);
               float f3 = (float) (d2 - (double) Mth.floor(d2));
               float f4 = (float) (d3 / 4.0D - (double) Mth.floor(d3 / 4.0D)) * 4.0F;
               float f5 = (float) (d4 - (double) Mth.floor(d4));
               Vec3 vec3 = this.level.getCloudColor(f01);
               int i = (int) Math.floor(d2);
               int j = (int) Math.floor(d3 / 4.0D);
               int k = (int) Math.floor(d4);
               if (i != this.prevCloudX || j != this.prevCloudY || k != this.prevCloudZ || this.minecraft.options.getCloudsType() != this.prevCloudsType || this.prevCloudColor.distanceToSqr(vec3) > 2.0E-4D) {
                   this.prevCloudX = i;
                   this.prevCloudY = j;
                   this.prevCloudZ = k;
                   this.prevCloudColor = vec3;
                   this.prevCloudsType = this.minecraft.options.getCloudsType();
                   this.generateClouds = true;
               }

               if (this.generateClouds) {
                   this.generateClouds = false;
                   BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                   if (this.cloudBuffer != null) {
                       this.cloudBuffer.close();
                   }

                   this.cloudBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
                   BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = this.buildClouds(bufferbuilder, d2, d3, d4, vec3);
                   this.cloudBuffer.bind();
                   this.cloudBuffer.upload(bufferbuilder$renderedbuffer);
                   VertexBuffer.unbind();
               }

               RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
               RenderSystem.setShaderTexture(0, KAKURIYO_CLOUDS_LOCATION);
               FogRenderer.levelFogColor();
               poseStack.pushPose();
               poseStack.scale(12.0F, 1.0F, 12.0F);
               poseStack.translate(-f3, f4, -f5);
               if (this.cloudBuffer != null) {
                   this.cloudBuffer.bind();
                   int l = this.prevCloudsType == CloudStatus.FANCY ? 0 : 1;

                   for (int i1 = l; i1 < 2; ++i1) {
                       if (i1 == 0) {
                           RenderSystem.colorMask(false, false, false, false);
                       } else {
                           RenderSystem.colorMask(true, true, true, true);
                       }

                       ShaderInstance shaderinstance = RenderSystem.getShader();
                       this.cloudBuffer.drawWithShader(poseStack.last().pose(), matrix4f, shaderinstance);
                   }

                   VertexBuffer.unbind();
               }

               poseStack.popPose();
               RenderSystem.enableCull();
               RenderSystem.disableBlend();
               RenderSystem.defaultBlendFunc();
           }
           ci.cancel();
       }
    }

}
