package com.iwaliner.urushi.blockentity.renderer;

import com.iwaliner.urushi.block.BambooBasketBlock;
import com.iwaliner.urushi.blockentity.BambooBasketBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BambooBasketRenderer implements BlockEntityRenderer<BambooBasketBlockEntity> {
    private final ItemRenderer itemRenderer;
    public BambooBasketRenderer(BlockEntityRendererProvider.Context context) {
        itemRenderer=context.getItemRenderer();
    }

    public void render(BambooBasketBlockEntity blockEntity, float f1, PoseStack poseStack, MultiBufferSource bufferSource, int i1, int i2) {
        Direction direction = blockEntity.getBlockState().getValue(BambooBasketBlock.FACING);

//        int l = (int) blockEntity.getBlockPos().asLong();
        int size = blockEntity.getItemsSize();
        if(size<0 || size>5){
            return;
        }
        List<RenderItemInfo> renderItems = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ItemStack itemStack = blockEntity.getItem(i);
            if (!itemStack.isEmpty()) {
                renderItems.add(new RenderItemInfo(itemStack, i, direction));
            }
        }

        if (!renderItems.isEmpty()) {
            renderAllItems(renderItems, poseStack, bufferSource, i1, i2, blockEntity);
        }
    }

    private void renderAllItems(List<RenderItemInfo> renderItems, PoseStack poseStack, MultiBufferSource bufferSource,
                                int packedLight, int packedOverlay, BambooBasketBlockEntity blockEntity) {
        for (RenderItemInfo info : renderItems) {
            poseStack.pushPose();

            applyItemTransform(poseStack, info.index, info.direction);

            this.itemRenderer.renderStatic(info.itemStack, ItemDisplayContext.FIXED, packedLight, packedOverlay,
                poseStack, bufferSource, blockEntity.getLevel(), (int) blockEntity.getBlockPos().asLong());

            poseStack.popPose();
        }
    }

    private void applyItemTransform(PoseStack poseStack, int index, Direction direction) {
        if(direction.getAxis() == Direction.Axis.X) {
            poseStack.translate(0.5D, 0.0625D * 2D + (double) ((index + 1) & 4) * 0.0078125D + (double) index * 0.0015625D,
                index < 3 ? 0.3D + 0.2D * (double) index : 0.4D + 0.2D * (double) (index - 3));
        } else {
            poseStack.translate(index < 3 ? 0.3D + 0.2D * (double) index : 0.4D + 0.2D * (double) (index - 3),
                0.0625D * 2D + (double) ((index + 1) & 4) * 0.0078125D + (double) index * 0.0015625D, 0.5D);
        }

        Direction direction1 = Direction.from2DDataValue((direction.get2DDataValue()) % 4).getOpposite();
        float f = -direction1.toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(f));
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

        if(index < 3) {
            poseStack.scale(0.35F, 0.35F, 0.35F);
        } else {
            poseStack.scale(0.3499F, 0.3499F, 0.3499F);
        }
    }
        private record RenderItemInfo(ItemStack itemStack, int index, Direction direction) {}
}