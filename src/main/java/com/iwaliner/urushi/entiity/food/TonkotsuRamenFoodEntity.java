package com.iwaliner.urushi.entiity.food;


import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class TonkotsuRamenFoodEntity extends FoodEntity {

    public TonkotsuRamenFoodEntity(EntityType<?> p_i48580_1_, Level p_i48580_2_) {
        super(ItemAndBlockRegister.tonkotsu_ramen.get(), EntityRegister.TonkotsuRamenFoodEntity.get(), p_i48580_2_);
    }


}
