package com.iwaliner.urushi;

import com.iwaliner.urushi.block.*;
import com.iwaliner.urushi.network.FramedBlockTextureConnectionPacket;
import com.iwaliner.urushi.network.NetworkAccess;
import com.iwaliner.urushi.util.UrushiUtils;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = ModCoreUrushi.ModID, value = Dist.CLIENT)
public class ClientNonBusSetUp {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if(ClientSetUp.connectionKey.consumeClick()) {
            NetworkAccess.sendToServer(new FramedBlockTextureConnectionPacket(ClientSetUp.connectionKey.isDown()));
        }
    }
    @SubscribeEvent
    public static void RenderLineEvent(RenderHighlightEvent.Block event) {
        PoseStack poseStack = event.getPoseStack();
        LevelRenderer levelRenderer = event.getLevelRenderer();
        MultiBufferSource multiBufferSource = event.getMultiBufferSource();
        Entity entity=event.getCamera().getEntity();
        if(entity instanceof Player player) {
            BlockPos pos = new BlockPos((int) event.getTarget().getLocation().x, (int) event.getTarget().getLocation().y, (int) event.getTarget().getLocation().z);
            ItemStack heldStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            Level level = player.level();
            BlockState state = level.getBlockState(pos);
            HitResult hitResult = Minecraft.getInstance().hitResult;
            BlockPos hitPos = ((BlockHitResult) Objects.requireNonNull(hitResult)).getBlockPos();
            BlockState hitState=level.getBlockState(hitPos);

            if (heldStack.canPerformAction(ToolActions.PICKAXE_DIG)||heldStack.canPerformAction(ToolActions.HOE_DIG)||heldStack.canPerformAction(ToolActions.SHOVEL_DIG)||heldStack.canPerformAction(ToolActions.AXE_DIG)|| Block.byItem(heldStack.getItem()) instanceof Roof225Block ||Block.byItem(heldStack.getItem()) instanceof Roof45Block) {

                int range=1;
                for(int i=-range;i<=range;i++){
                    for(int j=-range;j<=range;j++){
                        for(int k=-range;k<=range;k++){
                            BlockPos offsetPos=hitPos.offset(i,j,k);
                            BlockState offsetState=level.getBlockState(offsetPos);
                            if(offsetState.getBlock() instanceof Roof225Block){
                                renderHitOutline(poseStack, multiBufferSource.getBuffer(RenderType.lines()), event.getCamera().getEntity(), event.getCamera().getPosition().x, event.getCamera().getPosition().y, event.getCamera().getPosition().z, offsetPos, Blocks.STONE_SLAB.defaultBlockState());
                            }else if(offsetState.getBlock() instanceof Roof45Block){
                                renderHitOutline(poseStack, multiBufferSource.getBuffer(RenderType.lines()), event.getCamera().getEntity(), event.getCamera().getPosition().x, event.getCamera().getPosition().y, event.getCamera().getPosition().z, offsetPos, Blocks.STONE.defaultBlockState());
                            }
                        }
                    }
                }
            }
            if(hitState.getBlock() instanceof ParapetBlock){
                renderHitOutline(poseStack, multiBufferSource.getBuffer(RenderType.lines()), event.getCamera().getEntity(), event.getCamera().getPosition().x, event.getCamera().getPosition().y, event.getCamera().getPosition().z, hitPos, Blocks.CAULDRON.defaultBlockState());
            }
        }
    }
    private static void renderShape(PoseStack p_109783_, VertexConsumer p_109784_, VoxelShape p_109785_, double p_109786_, double p_109787_, double p_109788_) {
        PoseStack.Pose posestack$pose = p_109783_.last();
        p_109785_.forAllEdges((p_234280_, p_234281_, p_234282_, p_234283_, p_234284_, p_234285_) -> {
            float f = (float)(p_234283_ - p_234280_);
            float f1 = (float)(p_234284_ - p_234281_);
            float f2 = (float)(p_234285_ - p_234282_);
            float f3 = Mth.sqrt(f * f + f1 * f1 + f2 * f2);
            f /= f3;
            f1 /= f3;
            f2 /= f3;
            p_109784_.vertex(posestack$pose.pose(), (float)(p_234280_ + p_109786_), (float)(p_234281_ + p_109787_), (float)(p_234282_ + p_109788_)).color(1f, 1f, 0f, 1f).normal(posestack$pose.normal(), f, f1, f2).endVertex();
            p_109784_.vertex(posestack$pose.pose(), (float)(p_234283_ + p_109786_), (float)(p_234284_ + p_109787_), (float)(p_234285_ + p_109788_)).color(1f, 1f, 0f, 1f).normal(posestack$pose.normal(), f, f1, f2).endVertex();
        });
    }
    private static void renderHitOutline(PoseStack p_109638_, VertexConsumer p_109639_, Entity p_109640_, double p_109641_, double p_109642_, double p_109643_, BlockPos p_109644_, BlockState p_109645_) {
        renderShape(p_109638_, p_109639_, p_109645_.getShape(p_109640_.level(), p_109644_, CollisionContext.of(p_109640_)), (double)p_109644_.getX() - p_109641_, (double)p_109644_.getY() - p_109642_, (double)p_109644_.getZ() - p_109643_);
    }

    @SubscribeEvent
    public static void RenderGUIEvent(RenderGuiOverlayEvent event) {
        if(event.getOverlay()== VanillaGuiOverlay.HOTBAR.type()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null&&!Minecraft.getInstance().options.hideGui) {
                ItemStack mainHandStack = player.getMainHandItem();
                Block heldBlock=Block.byItem(mainHandStack.getItem());
                GuiGraphics guiGraphics=event.getGuiGraphics();
                int screenWidth = event.getWindow().getGuiScaledWidth();
                int i = guiGraphics.guiWidth() / 2;
                Window window=event.getWindow();
                if(heldBlock instanceof AbstractFramedBlock ||heldBlock instanceof FramedPaneBlock){
                    if (ClientSetUp.connectionKey.isDown()) {
                        UrushiUtils.displayToggleKeyImage(guiGraphics, "connectable_block_purple", guiGraphics.guiWidth(),guiGraphics.guiHeight());
                    } else {
                        UrushiUtils.displayToggleKeyImage(guiGraphics, "connectable_block_blue", guiGraphics.guiWidth(),guiGraphics.guiHeight());
                    }
                }else if(heldBlock instanceof SenbakokiBlock){
                    UrushiUtils.displayImage(guiGraphics,"senbakoki",window);
                }else if(heldBlock instanceof RiceCauldronBlock||heldBlock instanceof DirtFurnaceBlock){
                    UrushiUtils.displayImage(guiGraphics,"rice_cauldron_and_dirt_furnace",window);
                }

            }
        }

    }

    @SubscribeEvent
    public static void renderTooltipEvent(RenderTooltipEvent.Pre event) {
        ItemStack stack = event.getItemStack();
        Item heldItem=stack.getItem();
        GuiGraphics guiGraphics = event.getGraphics();
        Block heldBlock=Block.byItem(stack.getItem());
        if (heldBlock instanceof AbstractFramedBlock ||heldBlock instanceof FramedPaneBlock){
            UrushiUtils.displayImage(guiGraphics,"connectable_block",20,55);
        }else if(heldBlock instanceof RiceCauldronBlock ||heldBlock instanceof DirtFurnaceBlock ||heldItem==ItemAndBlockRegister.raw_rice.get()){
            UrushiUtils.displayImage(guiGraphics,"rice_cauldron_and_dirt_furnace",20,55);
        }else if(heldBlock instanceof Roof45Block){
            UrushiUtils.displayImage(guiGraphics,"roof_45",20,55);
        }else if(heldBlock instanceof Roof225Block){
            UrushiUtils.displayImage(guiGraphics,"roof_225",20,55);
        }else if(heldBlock instanceof SenbakokiBlock||heldBlock==ItemAndBlockRegister.rice_crop.get()){
            UrushiUtils.displayImage(guiGraphics,"senbakoki",20,55);
        }

    }
}
