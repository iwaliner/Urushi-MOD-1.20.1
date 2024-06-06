package com.iwaliner.urushi.entiity.renderer;

import com.iwaliner.urushi.entiity.JufuEffectDisplayEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JufuEffectRenderer extends FallingBlockRenderer {
    private final BlockRenderDispatcher dispatcher;
    public JufuEffectRenderer(EntityRendererProvider.Context p_174112_) {
        super(p_174112_);
        this.dispatcher = p_174112_.getBlockRenderDispatcher();
    }
    public void render(JufuEffectDisplayEntity fallingBlockEntity, float f1, float f2, PoseStack poseStack, MultiBufferSource bufferSource, int i) {
        BlockState blockstate = fallingBlockEntity.getBlockState();
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = fallingBlockEntity.level();
            if (blockstate != level.getBlockState(fallingBlockEntity.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                poseStack.pushPose();
                BlockPos blockpos = new BlockPos(Mth.floor(fallingBlockEntity.getX()), Mth.floor(fallingBlockEntity.getBoundingBox().maxY), Mth.floor(fallingBlockEntity.getZ()));
                poseStack.translate(-0.5D, 0D, -0.5D);
                var model = this.dispatcher.getBlockModel(blockstate);
                for (var renderType : model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(fallingBlockEntity.getStartPos())), net.minecraftforge.client.model.data.ModelData.EMPTY))
                    this.dispatcher.getModelRenderer().tesselateBlock(level, model, blockstate, blockpos.below(), poseStack, bufferSource.getBuffer(renderType), false, RandomSource.create(), blockstate.getSeed(fallingBlockEntity.getStartPos()), OverlayTexture.NO_OVERLAY, net.minecraftforge.client.model.data.ModelData.EMPTY, renderType);
                poseStack.popPose();
                super.render(fallingBlockEntity, f1, f2, poseStack, bufferSource, i);
            }
        }
    }
}
