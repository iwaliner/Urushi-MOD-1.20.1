package com.iwaliner.urushi.entiity.food.model;


import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public abstract class AbstractFoodModel<T extends Entity> extends HierarchicalModel<T> {

    public AbstractFoodModel(ModelPart p_170538_) {
  }



}
