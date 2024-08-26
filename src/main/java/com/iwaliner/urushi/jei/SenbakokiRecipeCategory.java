package com.iwaliner.urushi.jei;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.recipe.HammeringRecipe;
import com.iwaliner.urushi.recipe.SenbakokiRecipe;
import com.iwaliner.urushi.recipe.ThrowingInRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static mezz.jei.api.recipe.RecipeIngredientRole.*;

public class SenbakokiRecipeCategory implements IRecipeCategory<SenbakokiRecipe> {
    public static final ResourceLocation location=new ResourceLocation(ModCoreUrushi.ModID,"senbakoki");
    public static final ResourceLocation tex=new ResourceLocation(ModCoreUrushi.ModID,"textures/gui/senbakoki_jei.png");
    private final IDrawable background;
    private final IDrawable icon;
    private  final String textName;



    public SenbakokiRecipeCategory(IGuiHelper guiHelper){
        this.background=guiHelper.createDrawable(tex,0,0,118,35);
        this.icon=guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ItemAndBlockRegister.senbakoki.get()));
        textName="senbakoki";
    }


    @Override
    public RecipeType<SenbakokiRecipe> getRecipeType() {
        return JEIUrushiPlugin.JEI_SENBAKOKI;
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
    public void setRecipe(IRecipeLayoutBuilder builder, SenbakokiRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(INPUT, 1, 5)
                .addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(OUTPUT, 61, 5)
                .addItemStack(recipe.getResultItem());
        var subres = recipe.getSubResultItems().iterator();
        if (!subres.hasNext()) return;
        for (int i = 1; i <= 2 & subres.hasNext(); i++) {
            builder.addSlot(OUTPUT, 61 + 18 * i, 5).addItemStack(subres.next());
        }
    }


    @Override
    public void draw(SenbakokiRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Component text = Component.translatable("gui.jei.category.urushi."+textName+".text");
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        int stringWidth = fontRenderer.width(text);
        guiGraphics.drawString(fontRenderer, text, background.getWidth() - stringWidth, 28, -8355712, false);

    }
}
