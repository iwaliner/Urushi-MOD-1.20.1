package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;

import java.util.List;

public class PetrifiedLogBlock extends HorizonalRotateBlock{
    public PetrifiedLogBlock(Properties p_i48377_1_) {
        super(p_i48377_1_);
    }
    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        if(this== ItemAndBlockRegister.petrified_log_with_wood_amber.get()){
            UrushiUtils.setInfo(list,"petrified_log_with_wood_amber");
        }else if(this== ItemAndBlockRegister.petrified_log_with_fire_amber.get()){
            UrushiUtils.setInfo(list,"petrified_log_with_fire_amber");
        }else if(this== ItemAndBlockRegister.petrified_log_with_earth_amber.get()){
            UrushiUtils.setInfo(list,"petrified_log_with_earth_amber");
        }else if(this== ItemAndBlockRegister.petrified_log_with_metal_amber.get()){
            UrushiUtils.setInfo(list,"petrified_log_with_metal_amber");
        }else if(this== ItemAndBlockRegister.petrified_log_with_water_amber.get()){
            UrushiUtils.setInfo(list,"petrified_log_with_water_amber");
        }
    }
}
