package com.iwaliner.urushi.recipe;

import com.google.gson.JsonObject;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.RecipeTypeRegister;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nullable;

public class EarthElementCraftingRecipe extends AbstractElementCraftingRecipe{
    public EarthElementCraftingRecipe(NonNullList<Ingredient> input, ItemStack output, ResourceLocation location, int reiryoku) {
        super(input,output,location,reiryoku);
    }
    public RecipeType<?> getType() {
        return RecipeTypeRegister.EarthElementCraftingRecipe;
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegister.EarthElementCraftingSerializer.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ItemAndBlockRegister.earth_element_crafting_table.get());
    }

    public static class EarthElementCraftingRecipeType implements RecipeType<EarthElementCraftingRecipe> {
        @Override
        public String toString() {
            return new ResourceLocation(ModCoreUrushi.ModID,"earth_element_crafting").toString();
        }
    }

    public static class EarthElementCraftingSerializer<T extends EarthElementCraftingRecipe>implements RecipeSerializer<EarthElementCraftingRecipe> {


        @Override
        public EarthElementCraftingRecipe fromJson(ResourceLocation location, JsonObject json) {
            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(json, "ingredients"));
                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
                int i = GsonHelper.getAsInt(json, "reiryoku");
                return new EarthElementCraftingRecipe(nonnulllist,itemstack,location,i);

        }

        @Nullable
        @Override
        public EarthElementCraftingRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> input=NonNullList.withSize(4,Ingredient.EMPTY);
            for(int j = 0; j < input.size(); ++j) {
                input.set(j, Ingredient.fromNetwork(buffer));
            }
            ItemStack output=buffer.readItem();
            int i = buffer.readVarInt();
            return new EarthElementCraftingRecipe(input,output,location,i);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, EarthElementCraftingRecipe recipe) {
            for(Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }
            buffer.writeItem(recipe.output);
            buffer.writeVarInt(recipe.getReiryoku());

        }
    }
}
