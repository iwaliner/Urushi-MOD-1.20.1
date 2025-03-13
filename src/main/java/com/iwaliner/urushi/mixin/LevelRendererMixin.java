package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.DimensionRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.block.SlideDoorBlock;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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

    @Shadow protected abstract boolean doesMobEffectBlockSky(Camera p_234311_);

    @Shadow @Nullable private VertexBuffer starBuffer;

    @Shadow @Nullable private VertexBuffer skyBuffer;

     @Unique
     private static final ResourceLocation SUN_LOCATION=new ResourceLocation(ModCoreUrushi.ModID,"textures/environment/kakuriyo_sun.png");
    @Unique
    private static final ResourceLocation MOON_LOCATION=new ResourceLocation(ModCoreUrushi.ModID,"textures/environment/kakuriyo_moon_phases.png");

    private static final ResourceLocation DEEP_LOCATION=new ResourceLocation(ModCoreUrushi.ModID,"textures/environment/deep.png");
    private static final ResourceLocation SECOND_MOON_LOCATION=new ResourceLocation(ModCoreUrushi.ModID,"textures/environment/kakuriyo_second_moon.png");


    @Shadow @Nullable private VertexBuffer darkBuffer;

    @Shadow @Final private static ResourceLocation SNOW_LOCATION;

    @Inject(method = "renderSky",at = @At("HEAD"), cancellable = true)
    private void renderSkyInject(PoseStack poseStack, Matrix4f matrix4f0, float f01, Camera camera, boolean b01, Runnable runnable, CallbackInfo ci){
        if(this.level!=null&&this.level.dimension()== DimensionRegister.KakuriyoKey) {
            if (this.level.effects().renderSky(level, ticks, f01, poseStack, camera, matrix4f0, b01, runnable))
                return;
            runnable.run();
            if (!b01) {
                FogType fogtype = camera.getFluidInCamera();
                if (fogtype != FogType.POWDER_SNOW && fogtype != FogType.LAVA && !this.doesMobEffectBlockSky(camera)) {
                       Vec3 vec3 = this.level.getSkyColor(this.minecraft.gameRenderer.getMainCamera().getPosition(), f01);
                        float f = (float)vec3.x;
                        float f1 = (float)vec3.y;
                        float f2 = (float)vec3.z;
                        FogRenderer.levelFogColor();
                        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                        RenderSystem.depthMask(false);
                        RenderSystem.setShaderColor(f, f1, f2, 1.0F);
                        ShaderInstance shaderinstance = RenderSystem.getShader();
                        this.skyBuffer.bind();
                        this.skyBuffer.drawWithShader(poseStack.last().pose(), matrix4f0, shaderinstance);
                        VertexBuffer.unbind();
                        RenderSystem.enableBlend();
                        if(camera.getPosition().y<40d) {
                            poseStack.pushPose();
                            float f001 = 1.0F - this.level.getRainLevel(f01);
                            float time = 0.5f;
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f001);
                            poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                            poseStack.mulPose(Axis.XP.rotationDegrees(time * 360.0F));
                            Matrix4f matrix4f2 = poseStack.last().pose();
                            float f00 = 500f;
                            RenderSystem.setShader(GameRenderer::getPositionTexShader);
                            RenderSystem.setShaderTexture(0, DEEP_LOCATION);
                            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                            bufferbuilder.vertex(matrix4f2, -f00, 100.0F, -f00).uv(0.0F, 0.0F).endVertex();
                            bufferbuilder.vertex(matrix4f2, f00, 100.0F, -f00).uv(1.0F, 0.0F).endVertex();
                            bufferbuilder.vertex(matrix4f2, f00, 100.0F, f00).uv(1.0F, 1.0F).endVertex();
                            bufferbuilder.vertex(matrix4f2, -f00, 100.0F, f00).uv(0.0F, 1.0F).endVertex();
                            BufferUploader.drawWithShader(bufferbuilder.end());
                            poseStack.popPose();
                        }
                   /* poseStack.pushPose();
                    float f11b = 1.0F - this.level.getRainLevel(f01)*0.6f;
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11b);
                    poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                    poseStack.mulPose(Axis.XP.rotationDegrees((this.level.getTimeOfDay(f01)*0.6f) * 360.0F));
                    Matrix4f matrix4f1b = poseStack.last().pose();
                    float f12b=30f;
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderTexture(0, SECOND_MOON_LOCATION);
                    bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                    bufferbuilder.vertex(matrix4f1b, -f12b, -100.0F, f12b).uv(0.0F, 0.0F).endVertex();
                    bufferbuilder.vertex(matrix4f1b, f12b, -100.0F, f12b).uv(1.0F, 0.0F).endVertex();
                    bufferbuilder.vertex(matrix4f1b, f12b, -100.0F, -f12b).uv(1.0F, 1.0F).endVertex();
                    bufferbuilder.vertex(matrix4f1b, -f12b, -100.0F, -f12b).uv(0.0F, 1.0F).endVertex();
                    BufferUploader.drawWithShader(bufferbuilder.end());
                    poseStack.popPose();*/

                    float[] afloat = this.level.effects().getSunriseColor(this.level.getTimeOfDay(f01), f01);
                        if (afloat != null) {
                            RenderSystem.setShader(GameRenderer::getPositionColorShader);
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                            poseStack.pushPose();
                            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                            float f3 = Mth.sin(this.level.getSunAngle(f01)) < 0.0F ? 180.0F : 0.0F;
                            poseStack.mulPose(Axis.ZP.rotationDegrees(f3));
                            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                            float f4 = afloat[0];
                            float f5 = afloat[1];
                            float f6 = afloat[2];
                            Matrix4f matrix4f = poseStack.last().pose();
                            bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
                            bufferbuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();
                            int i = 16;

                            for(int j = 0; j <= 16; ++j) {
                                float f7 = (float)j * ((float)Math.PI * 2F) / 16.0F;
                                float f8 = Mth.sin(f7);
                                float f9 = Mth.cos(f7);
                                bufferbuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
                            }

                            BufferUploader.drawWithShader(bufferbuilder.end());
                            poseStack.popPose();
                        }

                        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                        poseStack.pushPose();
                        float f11 = 1.0F - this.level.getRainLevel(f01);
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);
                        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                        poseStack.mulPose(Axis.XP.rotationDegrees(this.level.getTimeOfDay(f01) * 360.0F));
                        Matrix4f matrix4f1 = poseStack.last().pose();
                        float f12=30f;
                        RenderSystem.setShader(GameRenderer::getPositionTexShader);
                        RenderSystem.setShaderTexture(0, SUN_LOCATION);
                        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                        bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
                        BufferUploader.drawWithShader(bufferbuilder.end());

                        f12=30f;
                        RenderSystem.setShaderTexture(0, MOON_LOCATION);
                        int k = this.level.getMoonPhase();
                        int l = k % 4;
                        int i1 = k / 4 % 2;
                        float f13 = (float)(l + 0) / 4.0F;
                        float f14 = (float)(i1 + 0) / 2.0F;
                        float f15 = (float)(l + 1) / 4.0F;
                        float f16 = (float)(i1 + 1) / 2.0F;
                        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                        bufferbuilder.vertex(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
                        bufferbuilder.vertex(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
                        bufferbuilder.vertex(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
                        bufferbuilder.vertex(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
                        BufferUploader.drawWithShader(bufferbuilder.end());



                        float f10 = this.level.getStarBrightness(f01) * f11;
                        if (f10 > 0.0F) {
                            RenderSystem.setShaderColor(f10, f10, f10, f10);
                            FogRenderer.setupNoFog();
                            this.starBuffer.bind();
                            this.starBuffer.drawWithShader(poseStack.last().pose(), matrix4f0, GameRenderer.getPositionShader());
                            VertexBuffer.unbind();
                            runnable.run();
                        }

                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        RenderSystem.disableBlend();
                        RenderSystem.defaultBlendFunc();
                        poseStack.popPose();
                        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
                        double d0 = this.minecraft.player.getEyePosition(f01).y - this.level.getLevelData().getHorizonHeight(this.level);
                        if (d0 < 0.0D) {
                            poseStack.pushPose();
                            poseStack.translate(0.0F, 12.0F, 0.0F);
                            this.darkBuffer.bind();
                            this.darkBuffer.drawWithShader(poseStack.last().pose(), matrix4f0, shaderinstance);
                            VertexBuffer.unbind();
                            poseStack.popPose();
                        }

                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        RenderSystem.depthMask(true);

                }
            }
            ci.cancel();
        }
    }
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
