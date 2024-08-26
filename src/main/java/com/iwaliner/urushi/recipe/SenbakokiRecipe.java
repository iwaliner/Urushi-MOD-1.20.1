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
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SenbakokiRecipe implements Recipe<Container> {

    private final NonNullList<Ingredient> ingredient;
    private final ItemStack output;
    private final NonNullList<ItemStack> sub_output;
    private final ResourceLocation location;
    public static ResourceLocation locationType=new ResourceLocation(ModCoreUrushi.ModID,"senbakoki");


    public SenbakokiRecipe(NonNullList<Ingredient> input, ItemStack output, ResourceLocation location) {
        this(input, output, location, NonNullList.create());
    }

    public SenbakokiRecipe(NonNullList<Ingredient> input, ItemStack output, ResourceLocation location, NonNullList<ItemStack> sub_output) {
        this.ingredient = input;
        this.output = output;
        this.location = location;
        this.sub_output = NonNullList.create();
        this.sub_output.addAll(sub_output);
    }

    public RecipeType<?> getType() {
        return RecipeTypeRegister.SenbakokiRecipe;
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
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    public NonNullList<Ingredient> getIngredient() {
        return ingredient;
    }
    @Override
    public ItemStack getResultItem(RegistryAccess p_267052_) {
        return output.copy();
    }
    public ItemStack getResultItem() {
        return output.copy();
    }

    public NonNullList<ItemStack> getSubResultItems() {
        return sub_output.stream().map(ItemStack::copy).collect(Collectors.toCollection(NonNullList::create));
    }

    @Override
    public ResourceLocation getId() {
        return location;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegister.SenbakokiSerializer.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ItemAndBlockRegister.senbakoki.get());
    }

    public NonNullList<Ingredient> getIngredients(){
        return ingredient;
    }
    public static class SenbakokiRecipeType implements RecipeType<SenbakokiRecipe> {
        @Override
        public String toString() {
            return SenbakokiRecipe.locationType.toString();
        }
    }

    public static class SenbakokiSerializer <T extends SenbakokiRecipe> implements RecipeSerializer<SenbakokiRecipe> {


        @Override
        public SenbakokiRecipe fromJson(ResourceLocation location, JsonObject json) {
            ItemStack output;
            JsonArray ingredient=GsonHelper.getAsJsonArray(json,"ingredients");
            NonNullList<Ingredient> input=NonNullList.withSize(1,Ingredient.EMPTY);
            NonNullList<ItemStack> sub_output = NonNullList.create();

            if (GsonHelper.isArrayNode(json, "result")) {
                var itt = GsonHelper.getAsJsonArray(json, "result").iterator();
                output = ShapedRecipe.itemStackFromJson(itt.next().getAsJsonObject());
                sub_output.addAll(StreamSupport.stream(Spliterators.spliteratorUnknownSize(itt, Spliterator.ORDERED), false)
                        .map(j -> ShapedRecipe.itemStackFromJson(j.getAsJsonObject())).toList());
            } else {
                output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            }

            for(int i=0;i<input.size();i++){
                input.set(i,Ingredient.fromJson(ingredient.get(0)));
            }
            return new SenbakokiRecipe(input,output,location,sub_output);
        }

        @Nullable
        @Override
        public SenbakokiRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> input=NonNullList.withSize(1,Ingredient.EMPTY);
            input.set(0,Ingredient.fromNetwork(buffer));
            ItemStack output=buffer.readItem();
            NonNullList<ItemStack> sub_output = NonNullList.create();
            int size = buffer.readInt();
            for (int i = 0; i < size; i++) sub_output.add(buffer.readItem());
            return new SenbakokiRecipe(input, output, location, sub_output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SenbakokiRecipe recipe) {
            for (Ingredient ingredient :recipe.getIngredient()){
                ingredient.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.getResultItem(), false);
            buffer.writeInt(recipe.sub_output.size());
            for (ItemStack stack : recipe.sub_output) buffer.writeItemStack(stack, false);
        }
    }
}
