package com.iwaliner.urushi.jei;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.recipe.FryingRecipe;
import com.iwaliner.urushi.recipe.OilExtractingRecipe;
import com.iwaliner.urushi.recipe.SilkFarmRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
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

public class SilkwormFarmRecipeCategory implements IRecipeCategory<SilkFarmRecipe> {
    public static final ResourceLocation location=new ResourceLocation(ModCoreUrushi.ModID,"silkworm");
    public static final ResourceLocation tex=new ResourceLocation(ModCoreUrushi.ModID,"textures/gui/silkworm_farm.png");
    private final IDrawable background;
    private final IDrawable icon;
    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;
    private final int regularCookTime;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

    public  SilkwormFarmRecipeCategory(IGuiHelper guiHelper){
        this.background=guiHelper.createDrawable(tex,55,16,82,54);
        this.icon=guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,new ItemStack(ItemAndBlockRegister.silkworm_farm.get()));
        // this.background = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 0, 114, 82, 54);
        this.regularCookTime = 200;
        staticFlame = guiHelper.createDrawable(tex, 176, 0, 14, 14);
        animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);

        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return guiHelper.drawableBuilder(tex, 176, 14, 24, 17)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }


    @Override
    public RecipeType<SilkFarmRecipe> getRecipeType() {
        return JEIUrushiPlugin.JEI_SILKWORM;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.jei.category.urushi.silkworm");
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
    public void setRecipe(IRecipeLayoutBuilder builder, SilkFarmRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(INPUT, 1, 1)
                .addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(INPUT, 1, 37)
                .addIngredients(recipe.getIngredients().get(1));

        builder.addSlot(OUTPUT, 61, 19)
                .addItemStack(recipe.getResultItem());
    }
    public void draw(SilkFarmRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.animatedFlame.draw(guiGraphics, 1, 20);
        IDrawableAnimated arrow = this.getArrow(recipe);
        arrow.draw(guiGraphics, 24, 18);
        this.drawCookTime(recipe, guiGraphics, 45);
    }
    protected void drawCookTime(SilkFarmRecipe recipe, GuiGraphics guiGraphics, int y) {
        int cookTime = 100;
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            Component timeString = Component.translatable("gui.jei.category.urushi.frying.time.seconds", new Object[]{cookTimeSeconds});
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(timeString);
            guiGraphics.drawString(fontRenderer, timeString, this.getWidth() - stringWidth, y, -8355712, false);
        }

    }

    protected IDrawableAnimated getArrow(SilkFarmRecipe recipe) {
        int cookTime = 100;
        if (cookTime <= 0) {
            cookTime = regularCookTime;
        }
        return this.cachedArrows.getUnchecked(cookTime);
    }
}
