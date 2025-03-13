package com.iwaliner.urushi.entiity.renderer;

import com.iwaliner.urushi.ClientSetUp;
import com.iwaliner.urushi.entiity.CushionEntity;
import com.iwaliner.urushi.entiity.ExperienceDroppableFallingAnvilEntity;
import com.iwaliner.urushi.entiity.model.CushionModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExperienceDroppableAnvilRenderer<T extends ExperienceDroppableFallingAnvilEntity> extends EntityRenderer<T> {
    private final BlockRenderDispatcher blockRenderDispatcher;
    public ExperienceDroppableAnvilRenderer(EntityRendererProvider.Context context) {
        super(context);
        blockRenderDispatcher= context.getBlockRenderDispatcher();
    }

    @Override
    public void render(ExperienceDroppableFallingAnvilEntity entity, float p_114486_, float p_114487_, PoseStack poseStack, MultiBufferSource bufferSource, int i1) {
        super.render((T) entity, p_114486_, p_114487_, poseStack, bufferSource, i1);
        poseStack.pushPose();
        poseStack.translate(-0.5D,0D,-0.5D);
        //poseStack.scale(1.1f,1.1f,1.1f);
        blockRenderDispatcher.renderSingleBlock(Blocks.ANVIL.defaultBlockState(),poseStack,bufferSource,i1,OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(T p_114482_) {
        return null;
    }
}
