package com.iwaliner.urushi.entiity.food;


import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class SoySourceRamenFoodEntity extends FoodEntity {

    public SoySourceRamenFoodEntity(EntityType<?> p_i48580_1_, Level p_i48580_2_) {
        super(ItemAndBlockRegister.soy_source_ramen.get(), EntityRegister.SoySourceRamenFoodEntity.get(), p_i48580_2_);
    }


}
