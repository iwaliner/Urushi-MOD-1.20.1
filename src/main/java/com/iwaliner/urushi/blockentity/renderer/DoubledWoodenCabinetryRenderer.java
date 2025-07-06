package com.iwaliner.urushi.blockentity.renderer;

import com.iwaliner.urushi.block.DoubledWoodenCabinetryBlock;
import com.iwaliner.urushi.blockentity.DoubledWoodenCabinetryBlockEntity;
import com.iwaliner.urushi.blockentity.SanboBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DoubledWoodenCabinetryRenderer implements BlockEntityRenderer<DoubledWoodenCabinetryBlockEntity> {
    private final ItemRenderer itemRenderer;
    public DoubledWoodenCabinetryRenderer(BlockEntityRendererProvider.Context context) {
        itemRenderer=context.getItemRenderer();
    }

    public void render(DoubledWoodenCabinetryBlockEntity blockEntity, float f1, PoseStack poseStack, MultiBufferSource bufferSource, int i1, int i2) {
        if(blockEntity.getBlockState().getBlock() instanceof DoubledWoodenCabinetryBlock) {
            Direction direction = blockEntity.getBlockState().getValue(DoubledWoodenCabinetryBlock.FACING);
            ItemStack itemstack = blockEntity.getDisplayStack();
            if (!itemstack.isEmpty()) {
                poseStack.pushPose();
                double d=2D;
                poseStack.translate(0.5D+(double) direction.getStepX()/d, 0.5D, 0.5D+(double) direction.getStepZ()/d);
                Direction direction1 = Direction.from2DDataValue((direction.getOpposite().get2DDataValue()) % 4);
                float f = -direction1.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(f));
                poseStack.scale(0.5F, 0.5F, 0.5F);
                this.itemRenderer.renderStatic(itemstack, ItemDisplayContext.FIXED, 15728880, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), (int) blockEntity.getBlockPos().relative(direction).asLong());
                poseStack.popPose();
            }

       }
    }
}