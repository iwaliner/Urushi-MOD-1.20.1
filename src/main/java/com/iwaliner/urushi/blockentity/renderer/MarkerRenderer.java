package com.iwaliner.urushi.blockentity.renderer;

import com.iwaliner.urushi.block.MarkerBlock;
import com.iwaliner.urushi.blockentity.FillerBlockEntity;
import com.iwaliner.urushi.blockentity.MarkerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MarkerRenderer implements BlockEntityRenderer<MarkerBlockEntity> {
     public MarkerRenderer(BlockEntityRendererProvider.Context context) {
    }

    public void render(MarkerBlockEntity blockEntity, float f1, PoseStack poseStack, MultiBufferSource bufferSource, int i1, int i2) {
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.lines());
        BlockPos originPos = blockEntity.getBlockPos();
        BlockPos xPos = blockEntity.posMarkerX;
        BlockPos yPos = blockEntity.posMarkerY;
        BlockPos zPos = blockEntity.posMarkerZ;
        if (blockEntity.hasRange()) {
            double d0 = 0.5D;
            double d1 = 0.5D;
            double d2 = 0.5D;
            double d3 = xPos.getX() + 0.5D - originPos.getX();
            double d4 = yPos.getY() + 0.5D - originPos.getY();
            double d5 = zPos.getZ() + 0.5D - originPos.getZ();
            LevelRenderer.renderLineBox(poseStack, vertexconsumer, d0, d1, d2, d3, d4, d5, 0F, 0F, 1.0F, 1.0F, 0.5F, 0.5F, 1.0F);

        }

    }
    public boolean shouldRenderOffScreen(MarkerBlockEntity p_112581_) {
        return true;
    }

    public int getViewDistance() {
        return 128;
    }
}