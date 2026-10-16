package com.iwaliner.urushi.jei;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.recipe.WaterElementCraftingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static mezz.jei.api.recipe.RecipeIngredientRole.INPUT;
import static mezz.jei.api.recipe.RecipeIngredientRole.OUTPUT;

public class WaterElementCraftingRecipeCategory implements IRecipeCategory<WaterElementCraftingRecipe> {
    public static final ResourceLocation location=new ResourceLocation(ModCoreUrushi.ModID,"water_element_crafting");
    public static final ResourceLocation tex=new ResourceLocation(ModCoreUrushi.ModID,"textures/gui/element_crafting.png");
    private final IDrawable background;
    private final IDrawable icon;
    private  final String textName;



    public WaterElementCraftingRecipeCategory(IGuiHelper guiHelper){
        this.background=guiHelper.createDrawable(tex,116,0,116,61);
        this.icon=guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ItemAndBlockRegister.water_element_crafting_table.get()));
        textName="water_element_crafting";
    }


    @Override
    public RecipeType<WaterElementCraftingRecipe> getRecipeType() {
        return JEIUrushiPlugin.JEI_WATER_ELEMENT_CRAFTING;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.jei.category.urushi."+textName);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }



    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, WaterElementCraftingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(INPUT, 19, 1)
                .addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(INPUT, 37, 19)
                .addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(INPUT,19, 37)
                .addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(INPUT, 1, 19)
                .addIngredients(recipe.getIngredients().get(3));

        builder.addSlot(OUTPUT, 95, 23)
                .addItemStack(recipe.getResultItem());
    }
    @Override
    public void draw(WaterElementCraftingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        int reiryoku = recipe.getReiryoku();
        Component string = Component.literal(String.valueOf(reiryoku));
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        guiGraphics.drawString(fontRenderer, string, 72, 9, -8355712, false);

    }
}
