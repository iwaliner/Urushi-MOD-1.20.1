package com.iwaliner.urushi.blockentity.renderer;

import com.iwaliner.urushi.block.PlateBlock;
import com.iwaliner.urushi.blockentity.PlateBlockEntity;
import com.iwaliner.urushi.blockentity.SanboBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class PlateRenderer implements BlockEntityRenderer<PlateBlockEntity> {

    public PlateRenderer(BlockEntityRendererProvider.Context p_173602_) {
    }

    public void render(PlateBlockEntity blockEntity, float f1, PoseStack poseStack, MultiBufferSource bufferSource, int i1, int i2) {
        Direction direction = blockEntity.getBlockState().getValue(PlateBlock.FACING);
        ItemStack itemstack = blockEntity.getItem(0);
        int i = (int)blockEntity.getBlockPos().asLong();

        if (!itemstack.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5D, 0.0625D*1D, 0.5D);
            Direction direction1 = Direction.from2DDataValue((direction.get2DDataValue()) % 4).getOpposite();
            float f = -direction1.toYRot();
            poseStack.mulPose(Axis.YP.rotationDegrees(f));
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.scale(0.25F, 0.25F, 0.25F);
            Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.FIXED, i1, OverlayTexture.NO_OVERLAY, poseStack, bufferSource,blockEntity.getLevel(), i);
            poseStack.popPose();
        }
    }


}