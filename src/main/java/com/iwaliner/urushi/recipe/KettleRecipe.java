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

public class KettleRecipe implements Recipe<Container> {

    protected  NonNullList<Ingredient> ingredient;
    private final ItemStack result;
    private final ResourceLocation location;
    public static ResourceLocation locationType=new ResourceLocation(ModCoreUrushi.ModID,"kettle");


    public KettleRecipe(NonNullList<Ingredient> ingredient, ItemStack result, ResourceLocation location) {
        this.ingredient = ingredient;
        this.result=result;
        this.location = location;
    }
    public RecipeType<?> getType() {
        return RecipeTypeRegister.KettleRecipe;
    }
    public NonNullList<Ingredient> getIngredients(){
        return ingredient;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return  this.ingredient.get(0).test(container.getItem(0)) && this.ingredient.get(1).test(container.getItem(1));
    }

    public ItemStack assemble(Container p_267036_, RegistryAccess p_266699_) {
        return this.result.copy();
    }
    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return result.copy();
    }
    public ItemStack getResultItem() {
        return result.copy();
    }
    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }
    @Override
    public ResourceLocation getId() {
        return location;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegister.KettleSerializer.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ItemAndBlockRegister.kettle.get());
    }

    public static class KettleRecipeType implements RecipeType<KettleRecipe> {
        @Override
        public String toString() {
            return KettleRecipe.locationType.toString();
        }
    }

    public static class KettleSerializer<T extends KettleRecipe>implements RecipeSerializer<KettleRecipe> {
        public NonNullList<Ingredient> itemsFromJson(JsonArray p_44276_) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for(int i = 0; i < p_44276_.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(p_44276_.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        @Override
        public KettleRecipe fromJson(ResourceLocation location, JsonObject json) {
            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(json, "ingredients"));

            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new KettleRecipe(nonnulllist,itemstack,location);
        }

        @Nullable
        @Override
        public KettleRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> input=NonNullList.withSize(2,Ingredient.EMPTY);
            for(int j = 0; j < input.size(); ++j) {
                input.set(j, Ingredient.fromNetwork(buffer));
            }
            ItemStack output=buffer.readItem();
            return new KettleRecipe(input,output,location);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, KettleRecipe recipe) {
            for(Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }
            buffer.writeItem(recipe.result);
        }
    }
}
