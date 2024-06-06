package com.iwaliner.urushi.recipe;

import com.iwaliner.urushi.ModCoreUrushi;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface IFryingRecipe extends Recipe<Container> {
    ResourceLocation locationType=new ResourceLocation(ModCoreUrushi.ModID,"frying");

  /*  @Override
    default RecipeType<?> getType(){
        return Registry.RECIPE_TYPE.getOptional(locationType).get();
    }*/

    @Override
    default boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_){
        return true;
    }

    @Override
    default boolean isSpecial() {
        return true;
    }
}
