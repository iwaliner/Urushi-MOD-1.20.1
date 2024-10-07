package com.iwaliner.urushi.util;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
 
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;

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
}
