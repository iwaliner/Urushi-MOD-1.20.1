package com.iwaliner.urushi.entiity.food;


import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class SweetfishWithSaltFoodEntity extends FoodEntity {

    public SweetfishWithSaltFoodEntity(EntityType<?> p_i48580_1_, Level p_i48580_2_) {
        super(ItemAndBlockRegister.sweetfish_with_salt.get(), EntityRegister.SweetfishWithSaltFoodEntity.get(), p_i48580_2_);
    }


}
