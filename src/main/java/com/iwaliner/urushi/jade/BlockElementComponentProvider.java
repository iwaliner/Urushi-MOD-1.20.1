package com.iwaliner.urushi.jade;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.util.ElementUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;

public enum BlockElementComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if(ElementUtils.isWoodElement(accessor.getBlockState())){
            tooltip.add(Component.translatable("info.urushi.wood_element_block").withStyle(ChatFormatting.DARK_GREEN));
        }
        if(ElementUtils.isFireElement(accessor.getBlockState())){
            tooltip.add(Component.translatable("info.urushi.fire_element_block").withStyle(ChatFormatting.DARK_RED));
        }
        if(ElementUtils.isEarthElement(accessor.getBlockState())){
            tooltip.add(Component.translatable("info.urushi.earth_element_block").withStyle(ChatFormatting.GOLD));
        }
        if(ElementUtils.isMetalElement(accessor.getBlockState())){
            tooltip.add(Component.translatable("info.urushi.metal_element_block").withStyle(ChatFormatting.AQUA));
        }
        if(ElementUtils.isWaterElement(accessor.getBlockState())){
            tooltip.add(Component.translatable("info.urushi.water_element_block").withStyle(ChatFormatting.DARK_PURPLE));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(ModCoreUrushi.ModID,"block_element");
    }
}