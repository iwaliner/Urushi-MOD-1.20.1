package com.iwaliner.urushi.blockentity.menu;

import com.iwaliner.urushi.MenuRegister;
import com.iwaliner.urushi.RecipeTypeRegister;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;


public class FryerMenu extends AbstractFryerMenu{
    public FryerMenu(int p_39532_, Inventory p_39533_) {
        super(MenuRegister.FryerMenu.get(), RecipeTypeRegister.FryingRecipe,p_39532_, p_39533_);
    }

    public FryerMenu(int p_39535_, Inventory p_39536_, Container p_39537_, ContainerData p_39538_) {
        super(MenuRegister.FryerMenu.get(), RecipeTypeRegister.FryingRecipe, p_39535_, p_39536_, p_39537_, p_39538_);
    }
}
