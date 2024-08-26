package com.iwaliner.urushi.entiity.food;


import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class CheeseGyudonFoodEntity extends FoodEntity {

    public CheeseGyudonFoodEntity(EntityType<?> p_i48580_1_, Level p_i48580_2_) {
        super(ItemAndBlockRegister.cheese_gyudon.get(), EntityRegister.CheeseGyudonFoodEntity.get(), p_i48580_2_);
    }


}
