package com.iwaliner.urushi.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.RecipeTypeRegister;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;


import javax.annotation.Nullable;

public class SandpaperPolishingRecipe implements Recipe<Container> {

    private final NonNullList<Ingredient> ingredient;
    private final ItemStack output;
    private final ResourceLocation location;
    public static ResourceLocation locationType=new ResourceLocation(ModCoreUrushi.ModID,"polishing");


    public SandpaperPolishingRecipe(NonNullList<Ingredient> input, ItemStack output, ResourceLocation location) {
        this.ingredient = input;
        this.output = output;
        this.location = location;
    }
    public RecipeType<?> getType() {
        return RecipeTypeRegister.SandpaperPolishingRecipe;
    }
    @Override
    public boolean matches(Container inventory, Level world) {

        return ingredient.get(0).test(inventory.getItem(0));

    }

    @Override
    public ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        return output.copy();
    }
    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return output.copy();
    }
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    public NonNullList<Ingredient> getIngredient() {
        return ingredient;
    }



    @Override
    public ResourceLocation getId() {
        return location;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegister.SandpaperPolishingSerializer.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ItemAndBlockRegister.sandpaper_block.get());
    }

    public NonNullList<Ingredient> getIngredients(){
        return ingredient;
    }
    public static class SandpaperPolishingRecipeType implements RecipeType<SandpaperPolishingRecipe> {
        @Override
        public String toString() {
            return SandpaperPolishingRecipe.locationType.toString();
        }
    }

    public static class SandpaperPolishingSerializer <T extends SandpaperPolishingRecipe> implements RecipeSerializer<SandpaperPolishingRecipe> {


        @Override
        public SandpaperPolishingRecipe fromJson(ResourceLocation location, JsonObject json) {
            ItemStack output= ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"result"));
            JsonArray ingredient=GsonHelper.getAsJsonArray(json,"ingredients");
            NonNullList<Ingredient> input=NonNullList.withSize(1,Ingredient.EMPTY);
            for(int i=0;i<input.size();i++){
                input.set(i,Ingredient.fromJson(ingredient.get(0)));
            }
            return new SandpaperPolishingRecipe(input,output,location);
        }

        @Nullable
        @Override
        public SandpaperPolishingRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> input=NonNullList.withSize(1,Ingredient.EMPTY);
            input.set(0,Ingredient.fromNetwork(buffer));
            ItemStack output=buffer.readItem();
            return new SandpaperPolishingRecipe(input,output,location);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SandpaperPolishingRecipe recipe) {
            for (Ingredient ingredient :recipe.getIngredient()){
                ingredient.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.output,false);
        }
    }
}
