package com.iwaliner.urushi.util;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.block.HorizonalRotateSlabBlock;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class UrushiUtils {
    public  static Direction getDirectionFromInt(int i){
        return switch (i) {
            case 0 -> Direction.DOWN;
            case 1 -> Direction.UP;
            //case 2 -> Direction.NORTH;
            default -> Direction.NORTH;
            case 3 -> Direction.SOUTH;
            case 4 -> Direction.WEST;
            case 5 -> Direction.EAST;
        };
    }
    public  static int getIntFromDirection(Direction direction){
        return switch (direction) {
            case DOWN -> 0;
            case UP -> 1;
            case NORTH -> 2;
            case SOUTH -> 3;
            case WEST -> 4;
            case EAST -> 5;
            default -> 6;
        };
    }
    public static void setInfo(List<Component> list,String string){
        list.add((Component.translatable("info.urushi."+string )).withStyle(ChatFormatting.GRAY));
    }
    public static void setInfoWithColor(List<Component> list,String string,ChatFormatting chatFormatting){
        list.add((Component.translatable("info.urushi."+string )).withStyle(chatFormatting));
    }
    public static void setBlinkingInfoWithColor(List<Component> list,String string,Level level,ChatFormatting color1,ChatFormatting color2){
        try {
            if(level==null){
                list.add((Component.translatable("info.urushi." + string)).withStyle(color1));
            }else {
                long gametime = level.getGameTime() % 20;
                if (gametime < 10) {
                    list.add((Component.translatable("info.urushi." + string)).withStyle(color1));
                } else {
                    list.add((Component.translatable("info.urushi." + string)).withStyle(color2));
                }
            }
        }catch (Exception ignored){

        }

    }
    public static void BlockChangeNeighborStateSurvey(Level level, BlockPos pos, BlockState detectBlock, BlockState placeBlock, SoundEvent soundEvent){
            if (BlockNeighborStateSurvey(level,pos,detectBlock)) {
               level.setBlock(pos, placeBlock, 2);
                level.playSound((Player) null,pos,soundEvent, SoundSource.BLOCKS, 1.0F, 1F);
            }
    }
    public static void BlockChangeNeighborFluidSurvey(Level level, BlockPos pos,Fluid fluid, BlockState placeBlock, SoundEvent soundEvent){
        if (BlockNeighborFluidSurvey(level,pos,fluid)) {
            level.setBlock(pos, placeBlock, 2);
            level.playSound((Player) null,pos,soundEvent, SoundSource.BLOCKS, 1.0F, 1F);
        }
    }
    public static boolean BlockNeighborStateSurvey(Level level, BlockPos pos, BlockState detectBlock){
        boolean flag = false;
        for (int i = 0; i < 6; i++) {
            if (level.getBlockState(pos.relative(UrushiUtils.getDirectionFromInt(i))) ==detectBlock) {
                flag = true;
            }
        }
      return flag;
    }
    public static boolean BlockNeighborFluidSurvey(Level level, BlockPos pos, Fluid fluid){
        boolean flag = false;
        for (int i = 0; i < 6; i++) {
            if (level.getFluidState(pos.relative(UrushiUtils.getDirectionFromInt(i))).is(fluid)) {
                flag = true;
            }
        }
        return flag;
    }
    public static boolean BlockNeighborBlockSurvey(Level level, BlockPos pos,Block block){
        boolean flag = false;
        for (int i = 0; i < 6; i++) {
            if (level.getBlockState(pos.relative(UrushiUtils.getDirectionFromInt(i))).getBlock() ==block) {
                flag = true;
            }
        }
        return flag;
    }
    public static boolean isShogatsu() {
        LocalDate localdate = LocalDate.now();
        int i = localdate.get(ChronoField.DAY_OF_MONTH);
        int j = localdate.get(ChronoField.MONTH_OF_YEAR);
        return j == 1 && i <=7;
    }
    public static boolean isAprilFoolsDay() {
        LocalDate localdate = LocalDate.now();
        int i = localdate.get(ChronoField.DAY_OF_MONTH);
        int j = localdate.get(ChronoField.MONTH_OF_YEAR);
        return j == 4 && i ==1;
    }
    public static VoxelShape rotateSimpleBoxShapeHorizontally(VoxelShape baseShape, int angle){
        double x_min=baseShape.min(Direction.Axis.X)*16D;
        double x_max=baseShape.max(Direction.Axis.X)*16D;
        double y_min=baseShape.min(Direction.Axis.Y)*16D;
        double y_max=baseShape.max(Direction.Axis.Y)*16D;
        double z_min=baseShape.min(Direction.Axis.Z)*16D;
        double z_max=baseShape.max(Direction.Axis.Z)*16D;
        if(angle==90){
            return Block.box(16D-z_max,y_min,x_min,16D-z_min,y_max,x_max);
        }else if(angle==180){
            return Block.box(16D-x_max,y_min,16D-z_max,16D-x_min,y_max,16D-z_min);
        }else if(angle==270){
            return Block.box(z_min,y_min,16D-x_max,z_max,y_max,16D-x_min);
        }else{
            return baseShape;
        }
    }
    public static VoxelShape rotateSimpleBoxShapeHorizontally(VoxelShape baseShape,Direction direction){
        int angle;
        switch (direction){
            case EAST -> angle = 90;
            case SOUTH -> angle = 180;
            case WEST -> angle = 270;
            default -> angle = 0;
        }
        return rotateSimpleBoxShapeHorizontally(baseShape,angle);
    }
    public static boolean isMinecraftObject(String id){
        String[] strings=id.split("\\.");
        return strings[1].equals("minecraft");
    }

    public static boolean isUrushiObject(String id){
        String[] strings=id.split("\\.");
        return strings[1].equals("urushi");
    }
   public static boolean isSlab(Block block){
        return block instanceof SlabBlock ||block instanceof HorizonalRotateSlabBlock;
    }
    public static void onCraftingRiceBall(Item filling,ItemStack riceBall){
        if(riceBall.getTag()==null){
            CompoundTag tag=new CompoundTag();
            if(filling==ItemAndBlockRegister.ghost_core.get()){
                tag.putString("effect","levitation");
            }else if(filling==Items.BLAZE_POWDER){
                tag.putString("effect","ignite");
            }else if(filling==ItemAndBlockRegister.salmon_sashimi.get()){
                tag.putString("effect","strength");
            }else if(filling==Items.GLOWSTONE_DUST){
                tag.putString("effect","glow");
            }else if(filling==ItemAndBlockRegister.pickled_japanese_apricot.get()){
                tag.putString("effect","slow_fall");
            }else if(filling==Items.DRIED_KELP){
                tag.putString("effect","water_breathing");
            }else if(filling==Items.GUNPOWDER){
                tag.putString("effect","explode");
            }else if(filling==ItemAndBlockRegister.onsen_egg.get()){
                tag.putString("effect","jump");
            }else if(filling==ItemAndBlockRegister.rice_malt.get()){
                tag.putString("effect","nausea");
            }else if(filling==Items.COPPER_INGOT){
                tag.putString("effect","slowness");
            }else if(filling==Items.SPIDER_EYE){
                tag.putString("effect","poison");
            }else if(filling==Items.SNOWBALL){
                tag.putString("effect","freeze");
            }
            riceBall.setTag(tag);
        }
    }
    public static ItemStack getRandomRiceBall(int stackSize, Random random){
        ItemStack stack=new ItemStack(ItemAndBlockRegister.rice_ball.get(),stackSize);
        CompoundTag tag=new CompoundTag();
        int i=random.nextInt(12);

        stack.setTag(tag);
        return stack;
    }
    public static void runFunction(Level level,BlockPos pos,String commandUserName,String functionName){
        MinecraftServer server = level.getServer();
        if(server!=null) {
            CommandFunction.CacheableFunction function = new CommandFunction.CacheableFunction(new ResourceLocation(ModCoreUrushi.ModID, functionName));
            CommandSourceStack commandSourceStack=new CommandSourceStack(CommandSource.NULL, pos.getCenter(), new Vec2(0f,0f),  (ServerLevel)level , 4, commandUserName, Component.empty(), server, null);
            function.get(server.getFunctions()).ifPresent((p_289236_) -> {
                server.getFunctions().execute(p_289236_, commandSourceStack.withSuppressedOutput().withPermission(4));
            });
        }
    }
    public static void runFunction(Level level, Player player, float rotX, float rotY, Entity entity, String functionName){
        MinecraftServer server = level.getServer();
        if(server!=null) {
            CommandFunction.CacheableFunction function = new CommandFunction.CacheableFunction(new ResourceLocation(ModCoreUrushi.ModID, functionName));
            CommandSourceStack commandSourceStack=new CommandSourceStack(player, player.position(), new Vec2(rotX,rotY),  (ServerLevel)level , 4, player.getName().toString(), player.getName(), server, entity);
            function.get(server.getFunctions()).ifPresent((p_289236_) -> {
                server.getFunctions().execute(p_289236_, commandSourceStack.withSuppressedOutput().withPermission(4));
            });
        }
    }
    public static void setTitle(ServerPlayer player,Component component){
        player.connection.send(new ClientboundSetTitleTextPacket(component));
    }
    public static void displayImage(GuiGraphics guiGraphics, String textureName, Window window){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, -90.0F);
        guiGraphics.pose().scale(0.45F,0.45F,0.45F);
        guiGraphics.blit(new ResourceLocation(ModCoreUrushi.ModID,"textures/gui/"+textureName+".png"), window.getGuiScaledWidth()+250, window.getGuiScaledHeight()-100, 0, 0, 256, 256);
        guiGraphics.pose().popPose();
    }
    public static void displayToggleKeyImage(GuiGraphics guiGraphics, String textureName, int width,int height){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((float)(width/2 ), (float)(height / 2), 0.0F);
       guiGraphics.pose().scale(0.05F,0.05F,1F);
        guiGraphics.blit(new ResourceLocation(ModCoreUrushi.ModID,"textures/gui/"+textureName+".png"), (width/2)-80, (height/2)-230, 0, 0, 256, 256);
        guiGraphics.pose().popPose();
    }
    public static void displayImage(GuiGraphics guiGraphics, String textureName, int width,int height){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(0.45F,0.45F,0.45F);
        guiGraphics.blit(new ResourceLocation(ModCoreUrushi.ModID,"textures/gui/"+textureName+".png"), width, height, 0, 0, 256, 256);
        guiGraphics.pose().popPose();
    }

}
