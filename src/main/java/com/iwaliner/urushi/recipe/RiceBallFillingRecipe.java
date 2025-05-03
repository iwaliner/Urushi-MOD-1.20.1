package com.iwaliner.urushi.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.RecipeTypeRegister;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SuspiciousEffectHolder;

import javax.annotation.Nullable;

public class RiceBallFillingRecipe extends CustomRecipe {

    public RiceBallFillingRecipe(ResourceLocation p_248870_, CraftingBookCategory p_250392_) {
        super(p_248870_, p_250392_);
    }
    public RecipeType<?> getType() {
        return RecipeTypeRegister.RiceBallFillingRecipe;
    }
    public boolean matches(CraftingContainer container, Level level) {
        boolean flag = false;
        if(container.getItem(1).is(ItemAndBlockRegister.rice.get())
                &&container.getItem(3).is(ItemAndBlockRegister.rice.get())
                &&container.getItem(5).is(ItemAndBlockRegister.rice.get())
                &&container.getItem(6).is(ItemAndBlockRegister.rice.get())
                &&container.getItem(7).is(ItemAndBlockRegister.rice.get())
                &&container.getItem(8).is(ItemAndBlockRegister.rice.get())){
            if(UrushiUtils.getRiceBallFillingItem().containsKey(container.getItem(4).getItem())){
                flag=true;
            }
        }
        return flag;
    }

    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack itemstack = new ItemStack(ItemAndBlockRegister.rice_ball.get(), 4);
        if(UrushiUtils.getRiceBallFillingItem().containsKey(container.getItem(4).getItem())){
            UrushiUtils.onCraftingRiceBall(container.getItem(4).getItem(),itemstack);
        }
        return itemstack;
    }

    public boolean canCraftInDimensions(int p_44489_, int p_44490_) {
       // return p_44489_ >= 3 && p_44490_ >= 3;
        return true;
    }

    public RecipeSerializer<?> getSerializer() {
        return RecipeTypeRegister.RiceBallFillingSerializer.get();
    }
    public static class RiceBallFillingRecipeType implements RecipeType<RiceBallFillingRecipe> {
        @Override
        public String toString() {
            return new ResourceLocation(ModCoreUrushi.ModID,"rice_ball").toString();
        }
    }
}
