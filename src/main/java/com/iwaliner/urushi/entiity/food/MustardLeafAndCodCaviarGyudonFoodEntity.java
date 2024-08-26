package com.iwaliner.urushi.entiity.food;


import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class MustardLeafAndCodCaviarGyudonFoodEntity extends FoodEntity {

    public MustardLeafAndCodCaviarGyudonFoodEntity(EntityType<?> p_i48580_1_, Level p_i48580_2_) {
        super(ItemAndBlockRegister.mustard_leaf_and_cod_caviar_gyudon.get(), EntityRegister.MustardLeafAndCodCaviarGyudonFoodEntity.get(), p_i48580_2_);
    }


}
