package com.iwaliner.urushi.util;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ParticleRegister;
import com.iwaliner.urushi.TagUrushi;
import com.iwaliner.urushi.item.AbstractMagatamaItem;
import com.iwaliner.urushi.util.interfaces.*;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.state.BlockState;


import java.util.*;


public class ElementUtils {

    public static boolean hasReiryoku(Item item) {
        return item instanceof HasReiryokuItem;
    }

    public static boolean isWoodElementItem(ItemStack stack) {
        return stack.is(TagUrushi.WOOD_ELEMENT_ITEM);
    }

    public static boolean isFireElementItem(ItemStack stack) {
        return stack.is(TagUrushi.FIRE_ELEMENT_ITEM);
    }

    public static boolean isEarthElementItem(ItemStack stack) {
        return stack.is(TagUrushi.EARTH_ELEMENT_ITEM);
    }

    public static boolean isMetalElementItem(ItemStack stack) {
        return stack.is(TagUrushi.METAL_ELEMENT_ITEM);
    }

    public static boolean isWaterElementItem(ItemStack stack) {
        return stack.is(TagUrushi.WATER_ELEMENT_ITEM);
    }



    public static boolean isWoodElement(BlockState state) {

        if (state.is(TagUrushi.DISABLE_WOOD_ELEMENT)) {
            return false;
        }
        if (state.is(TagUrushi.REGISTER_WOOD_ELEMENT)) {
            return true;
        }
return false;
    }

    public static boolean isFireElement(BlockState state) {

        if (state.is(TagUrushi.DISABLE_FIRE_ELEMENT)) {
            return false;
        }
        if (state.is(TagUrushi.REGISTER_FIRE_ELEMENT)) {
            return true;
        }
        return false;
    }

    public static boolean isEarthElement(BlockState state) {

        if (state.is(TagUrushi.DISABLE_EARTH_ELEMENT)) {
            return false;
        }
        if (state.is(TagUrushi.REGISTER_EARTH_ELEMENT)) {
            return true;
        }
    return false;
    }

    public static boolean isMetalElement(BlockState state) {

        if (state.is(TagUrushi.DISABLE_METAL_ELEMENT)) {
            return false;
        }
        if (state.is(TagUrushi.REGISTER_METAL_ELEMENT)) {
            return true;
        }

     return false;
    }

    public static boolean isWaterElement(BlockState state) {

        if (state.is(TagUrushi.DISABLE_WATER_ELEMENT)) {
            return false;
        }
        if (state.is(TagUrushi.REGISTER_WATER_ELEMENT)) {
            return true;
        }
        return false;
    }

    public static boolean isSpecificElement(ElementType type, BlockState state) {
        switch (type) {
            case WoodElement -> {
                return ElementUtils.isWoodElement(state);
            }
            case FireElement -> {
                return ElementUtils.isFireElement(state);
            }
            case EarthElement -> {
                return ElementUtils.isEarthElement(state);
            }
            case MetalElement -> {
                return ElementUtils.isMetalElement(state);
            }
            case WaterElement -> {
                return ElementUtils.isWaterElement(state);
            }
            default -> {
                return false;
            }
        }
    }



    public static void setBreakSpeedInfo(List<Component> list, int i, ElementType type) {
        String s = "";
        ChatFormatting chatFormatting = ChatFormatting.GRAY;
        if(i == 0){
            return;
        }

        switch (type) {
            case WoodElement:
                s = "info.urushi.wood_element_of_block";
                chatFormatting = ChatFormatting.DARK_GREEN;
                break;
            case FireElement:
                s = "info.urushi.fire_element_of_block";
                chatFormatting = ChatFormatting.DARK_RED;
                break;
            case EarthElement:
                s = "info.urushi.earth_element_of_block";
                chatFormatting = ChatFormatting.GOLD;
                break;
            case MetalElement:
                s = "info.urushi.metal_element_of_block";
                // chatFormatting = ChatFormatting.GRAY;
                break;
            case WaterElement:
                s = "info.urushi.water_element_of_block";
                chatFormatting = ChatFormatting.DARK_PURPLE;
                break;
            default:
                //break;
                // throw exception?
                return; // as no element matches, just return
        }
        String percentString = i + "% ";
        if(i > 0){
            percentString = "+" + percentString;
        }
        list.add((Component.translatable("info.urushi.blank").append(percentString)
                .append(Component.translatable(s))).withStyle(chatFormatting));
    }

    public static boolean isWoodElementMob(LivingEntity entity) {
        return entity instanceof Chicken;
    }

    public static boolean isFireElementMob(LivingEntity entity) {
        return entity instanceof Sheep;
    }

    public static boolean isEarthElementMob(LivingEntity entity) {
        return entity instanceof Cow;
    }

    public static boolean isMetalElementMob(LivingEntity entity) {
        return entity instanceof Horse;
    }

    public static boolean isWaterElementMob(LivingEntity entity) {
        return entity instanceof Pig;
    }

    public static boolean isElementMob(LivingEntity entity) {
        return isWoodElementMob(entity) || isFireElementMob(entity) || isEarthElementMob(entity) || isMetalElementMob(entity) || isWaterElementMob(entity);
    }

    public static final String REIRYOKU_AMOUNT = "stored_reiryoku";

    /**
     * 霊力の最大貯蔵可能量を返す
     **/
    public static int getReiryokuCapacity(ItemStack stack) {
        if (stack.getItem() instanceof HasReiryokuItem) {
            return ((HasReiryokuItem) stack.getItem()).getReiryokuCapacity();
        }
        return 0;
    }

    /**
     * 現在の霊力貯蔵量を返す
     **/
    public static int getStoredReiryokuAmount(ItemStack stack) {
        if (getReiryokuCapacity(stack) <= 0) {
            return 0;
        }
        CompoundTag compoundtag = stack.getTag();
        if (compoundtag == null) {
            stack.setTag(new CompoundTag());
            return 0;
        }
        return compoundtag.getInt(REIRYOKU_AMOUNT);

    }

    /**
     * 霊力貯蔵量を特定の量に変更する
     **/
    public static void setStoredReiryokuAmount(ItemStack stack, int i) {
        if (0 <= i && i <= getReiryokuCapacity(stack)) {
            CompoundTag compoundtag = stack.getTag();
            if (compoundtag == null) {
                stack.setTag(new CompoundTag());
            }
            stack.getTag().putInt(REIRYOKU_AMOUNT, i);
        }
    }

    /**
     * 霊力を増減させるが、結果が0以下や最大容量以上になる場合を考慮していない。
     **/
    public static void increaseStoredReiryokuAmount(ItemStack stack, int i) {
        CompoundTag compoundtag = stack.getTag();
        int pre = 0;
        if (compoundtag == null) {
            stack.setTag(new CompoundTag());
        } else {
            pre = stack.getTag().getInt(REIRYOKU_AMOUNT);
        }
        if (0 <= pre + i && pre + i <= getReiryokuCapacity(stack)) {
            stack.getTag().putInt(REIRYOKU_AMOUNT, pre + i);
        } else if (pre + i > getReiryokuCapacity(stack)) {
            stack.getTag().putInt(REIRYOKU_AMOUNT, getReiryokuCapacity(stack));
        } else {
            stack.getTag().putInt(REIRYOKU_AMOUNT, 0);
        }
    }

    /**
     * 霊力を増減させたとき、計算結果が定義域に含まれているかどうか
     **/
    public static boolean willBeInDomain(ItemStack stack, int i) {
        CompoundTag compoundtag = stack.getTag();
        int pre = 0;
        if (compoundtag == null) {
            stack.setTag(new CompoundTag());
        } else {
            pre = stack.getTag().getInt(REIRYOKU_AMOUNT);
        }
        return 0 <= pre + i && pre + i <= getReiryokuCapacity(stack);
    }

    /**
     * 霊力を増減させたとき、定義域からはみ出た端数を返す
     **/
    public static int getExtraReiryokuAmount(ItemStack stack, int i) {
        CompoundTag compoundtag = stack.getTag();
        int pre = 0;
        if (compoundtag == null) {
            stack.setTag(new CompoundTag());
        } else {
            pre = stack.getTag().getInt(REIRYOKU_AMOUNT);
        }

        if (0 <= pre + i && pre + i <= getReiryokuCapacity(stack)) {
            //stack.getTag().putInt(REIRYOKU_AMOUNT, pre + i);
            return 0;
        } else if (pre + i > getReiryokuCapacity(stack)) {
            /**霊力を増やしすぎて計算結果が最大容量を超えたとき、入りきらなかった量を正の値で返す。つまり、貯蔵量4990、最大貯蔵量5000にの霊力を30増やそうとすると20が返ってくる。**/
            //  stack.getTag().putInt(REIRYOKU_AMOUNT, getReiryokuCapacity(stack));
            return pre + i - getReiryokuCapacity(stack);
        } else {
            /**霊力を減らしすぎて計算結果が0になったとき、引ききれなかった量を負の値で返す。つまり、貯蔵量10の霊力を90減らそうとすると-80が返ってくる。**/
            //   stack.getTag().putInt(REIRYOKU_AMOUNT, 0);
            return -(i - pre);
        }
    }

    /**
     * インベントリ内の勾玉を探す
     */
    public static ItemStack getMagatamaInInventory(Player player, ElementType elementType) {
        ItemStack result = ItemStack.EMPTY;
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack = player.getInventory().getItem(i);
            if (itemstack.getItem() instanceof AbstractMagatamaItem magatamaItem) {
                if (magatamaItem.getElementType() == elementType) {
                    result = itemstack;
                    break;
                }
            }
        }
        return result;
    }


    public static boolean isSoukokuBlock(LevelAccessor level, BlockPos pos, BlockState currentState) {
        BlockState neighborState = level.getBlockState(pos);
        if (currentState.getBlock() == Blocks.CYAN_CONCRETE_POWDER) {
            return neighborState.getBlock() == Blocks.YELLOW_CONCRETE_POWDER;
        } else if (currentState.getBlock() == Blocks.RED_CONCRETE_POWDER) {
            return neighborState.getBlock() == Blocks.WHITE_CONCRETE_POWDER;
        } else if (currentState.getBlock() == Blocks.YELLOW_CONCRETE_POWDER) {
            return neighborState.getBlock() == Blocks.PURPLE_CONCRETE_POWDER;
        } else if (currentState.getBlock() == Blocks.WHITE_CONCRETE_POWDER) {
            return neighborState.getBlock() == Blocks.CYAN_CONCRETE_POWDER;
        } else if (currentState.getBlock() == Blocks.PURPLE_CONCRETE_POWDER) {
            return neighborState.getBlock() == Blocks.RED_CONCRETE_POWDER;
        }
        return false;

    }

    public static ItemStack getOverflowStack(ElementType type) {
        ItemStack overflowItem;
        switch (type) {
            case WoodElement -> overflowItem = new ItemStack(ItemAndBlockRegister.wood_amber.get());
            case FireElement -> overflowItem = new ItemStack(ItemAndBlockRegister.fire_amber.get());
            case EarthElement -> overflowItem = new ItemStack(ItemAndBlockRegister.earth_amber.get());
            case MetalElement -> overflowItem = new ItemStack(ItemAndBlockRegister.metal_amber.get());
            case WaterElement -> overflowItem = new ItemStack(ItemAndBlockRegister.water_amber.get());
            default -> overflowItem = ItemStack.EMPTY;
        }
        return overflowItem;
    }

    public static ParticleOptions getMediumElementParticle(ElementType type) {
        ParticleOptions particleOptions;
        switch (type) {
            case WoodElement -> particleOptions = ParticleRegister.WoodElementMedium.get();
            case FireElement -> particleOptions = ParticleRegister.FireElementMedium.get();
            case EarthElement -> particleOptions = ParticleRegister.EarthElementMedium.get();
            case MetalElement -> particleOptions = ParticleRegister.MetalElementMedium.get();
            // case WaterElement ->
            default -> particleOptions = ParticleRegister.WaterElementMedium.get();
        }
        return particleOptions;

    }

    public static Component getStoredReiryokuDisplayMessage(int current, int max, ElementType elementType) {
        ChatFormatting color = ChatFormatting.RESET;
        String string = "error";
        switch (elementType) {
            case WoodElement -> {
                color = ChatFormatting.GREEN;
                string = "info.urushi.stored_wood_reiryoku.message";
            }
            case FireElement -> {
                color = ChatFormatting.RED;
                string = "info.urushi.stored_fire_reiryoku.message";
            }
            case EarthElement -> {
                color = ChatFormatting.GOLD;
                string = "info.urushi.stored_earth_reiryoku.message";
            }
            case MetalElement -> {
                color = ChatFormatting.WHITE;
                string = "info.urushi.stored_metal_reiryoku.message";
            }
            // case WaterElement -> {
            default -> {
                color = ChatFormatting.LIGHT_PURPLE;
                string = "info.urushi.stored_water_reiryoku.message";
            }
        }
        return Component.translatable(string).append(" " + current + " / " + max).withStyle(color);
    }
    public static ParticleOptions getElementParticle(ElementType elementType){
        switch (elementType){

            case FireElement -> {
                return ParticleRegister.FireElement.get();
            }
            case EarthElement -> {
                return ParticleRegister.EarthElement.get();
            }
            case MetalElement -> {
                return ParticleRegister.MetalElement.get();
            }
            case WaterElement -> {
                return ParticleRegister.WaterElement.get();
            }
            default -> {
               return ParticleRegister.WoodElement.get();
            }
        }
    }

    public static ParticleOptions getElementMediumParticle(ElementType elementType){
        switch (elementType){

            case FireElement -> {
                return ParticleRegister.FireElementMedium.get();
            }
            case EarthElement -> {
                return ParticleRegister.EarthElementMedium.get();
            }
            case MetalElement -> {
                return ParticleRegister.MetalElementMedium.get();
            }
            case WaterElement -> {
                return ParticleRegister.WaterElementMedium.get();
            }
            default -> {
                return ParticleRegister.WoodElementMedium.get();
            }
        }
    }
    public static MutableComponent getIconComponent(ElementType elementType){
        switch (elementType){

            case FireElement -> {
                return Component.literal("\uE8A2").withStyle((style) -> {
                    return style.withColor(ChatFormatting.WHITE);
                });
            }
            case EarthElement -> {
                return Component.literal("\uE8A3").withStyle((style) -> {
                    return style.withColor(ChatFormatting.WHITE);
                });
            }
            case MetalElement -> {
                return Component.literal("\uE8A4").withStyle((style) -> {
                    return style.withColor(ChatFormatting.WHITE);
                });
            }
            case WaterElement -> {
                return Component.literal("\uE8A5").withStyle((style) -> {
                    return style.withColor(ChatFormatting.WHITE);
                });
            }
            default -> {
                return Component.literal("\uE8A1").withStyle((style) -> {
                    return style.withColor(ChatFormatting.WHITE);
                });
            }
        }
    }

}
