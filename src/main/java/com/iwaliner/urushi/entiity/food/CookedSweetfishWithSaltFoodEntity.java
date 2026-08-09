package com.iwaliner.urushi.entiity.food;


import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class CookedSweetfishWithSaltFoodEntity extends FoodEntity {

    public CookedSweetfishWithSaltFoodEntity(EntityType<?> p_i48580_1_, Level p_i48580_2_) {
        super(ItemAndBlockRegister.cooked_sweetfish_with_salt.get(), EntityRegister.CookedSweetfishWithSaltFoodEntity.get(), p_i48580_2_);
    }


}
