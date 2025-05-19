package com.iwaliner.urushi.blockentity.renderer;

import com.iwaliner.urushi.block.DoubledWoodenCabinetryBlock;
import com.iwaliner.urushi.blockentity.DoubledWoodenCabinetryBlockEntity;
import com.iwaliner.urushi.blockentity.FillerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FillerRenderer implements BlockEntityRenderer<FillerBlockEntity> {
    public FillerRenderer(BlockEntityRendererProvider.Context context) {
    }

    public void render(FillerBlockEntity blockEntity, float f1, PoseStack poseStack, MultiBufferSource bufferSource, int i1, int i2) {
        if (blockEntity.posOrigin != blockEntity.getBlockPos()) {
            VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.lines());
            double d0 = blockEntity.posOrigin.getX()+0.5D-blockEntity.getBlockPos().getX();
            double d1 = blockEntity.posOrigin.getY()+0.5D-blockEntity.getBlockPos().getY();
            double d2 = blockEntity.posOrigin.getZ()+0.5D-blockEntity.getBlockPos().getZ();
            LevelRenderer.renderLineBox(poseStack, vertexconsumer, d0, d1, d2, d0+blockEntity.rangeX, d1+blockEntity.rangeY, d2+blockEntity.rangeZ, 1F, 1F, 0F, 1.0F, 1.0F, 1.0F, 0.0F);
        }
    }
    public boolean shouldRenderOffScreen(FillerBlockEntity p_112581_) {
        return true;
    }

    public int getViewDistance() {
        return 128;
    }
}