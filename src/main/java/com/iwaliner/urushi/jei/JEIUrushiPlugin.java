package com.iwaliner.urushi.jei;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.RecipeTypeRegister;
import com.iwaliner.urushi.recipe.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIUrushiPlugin implements IModPlugin {
    public static final RecipeType<FryingRecipe> JEI_FRYING = RecipeType.create(ModCoreUrushi.ModID, "frying", FryingRecipe.class);
    public static final RecipeType<HammeringRecipe> JEI_HAMMERING = RecipeType.create(ModCoreUrushi.ModID, "hammering", HammeringRecipe.class);
    public static final RecipeType<OilExtractingRecipe> JEI_OIL_EXTRACTING = RecipeType.create(ModCoreUrushi.ModID, "oil_extracting", OilExtractingRecipe.class);
    public static final RecipeType<ThrowingInRecipe> JEI_THROWING_IN = RecipeType.create(ModCoreUrushi.ModID, "throwing_in", ThrowingInRecipe.class);
    public static final RecipeType<SenbakokiRecipe> JEI_SENBAKOKI =RecipeType.create(ModCoreUrushi.ModID, "senbakoki", SenbakokiRecipe.class);
    public static final RecipeType<SandpaperPolishingRecipe> JEI_POLISHING =RecipeType.create(ModCoreUrushi.ModID, "polishing", SandpaperPolishingRecipe.class);
    public static final RecipeType<ChiseledLacquerLogRecipe> JEI_CHISELED_LACQUER_LOG =RecipeType.create(ModCoreUrushi.ModID, "chiseled_lacquer_log", ChiseledLacquerLogRecipe.class);
    public static final RecipeType<WoodElementCraftingRecipe> JEI_WOOD_ELEMENT_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "wood_element_crafting", WoodElementCraftingRecipe.class);
    public static final RecipeType<FireElementCraftingRecipe> JEI_FIRE_ELEMENT_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "fire_element_crafting", FireElementCraftingRecipe.class);
    public static final RecipeType<EarthElementCraftingRecipe> JEI_EARTH_ELEMENT_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "earth_element_crafting", EarthElementCraftingRecipe.class);
    public static final RecipeType<MetalElementCraftingRecipe> JEI_METAL_ELEMENT_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "metal_element_crafting", MetalElementCraftingRecipe.class);
    public static final RecipeType<WaterElementCraftingRecipe> JEI_WATER_ELEMENT_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "water_element_crafting", WaterElementCraftingRecipe.class);
    public static final RecipeType<FoxEatingRecipe> JEI_FOX_EATING =RecipeType.create(ModCoreUrushi.ModID, "fox_eating", FoxEatingRecipe.class);
    public static final RecipeType<SilkFarmRecipe> JEI_SILKWORM =RecipeType.create(ModCoreUrushi.ModID, "silkworm", SilkFarmRecipe.class);
    public static final RecipeType<KettleRecipe> JEI_KETTLE =RecipeType.create(ModCoreUrushi.ModID, "kettle", KettleRecipe.class);
    public static final RecipeType<RainwaterTankRecipe> JEI_RAINWATER_TANK = RecipeType.create(ModCoreUrushi.ModID, "rainwater_tank", RainwaterTankRecipe.class);





    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ModCoreUrushi.ModID,"jei_plugin");
    }


    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
       registration.addRecipeCategories(new FryingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new HammeringRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new OilExtractingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ThrowingInRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SenbakokiRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FoxEatingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SandpaperPolishingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ChiseledLacquerLogRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new WoodElementCraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FireElementCraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new EarthElementCraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MetalElementCraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new WaterElementCraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SilkwormFarmRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new KettleRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new RainwaterTankRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager= Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<FryingRecipe> FryingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.FryingRecipe);
        registration.addRecipes(new RecipeType<>(FryingRecipeCategory.location,FryingRecipe.class),FryingRecipes);

        List<HammeringRecipe> HammeringRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.HammeringRecipe);
        registration.addRecipes(new RecipeType<>(HammeringRecipeCategory.location,HammeringRecipe.class),HammeringRecipes);

        List<OilExtractingRecipe> OilExtractingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.OilExtractingRecipe);
        registration.addRecipes(new RecipeType<>(OilExtractingRecipeCategory.location,OilExtractingRecipe.class),OilExtractingRecipes);

        List<ThrowingInRecipe> ThrowingInRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.ThrowingInRecipe);
        registration.addRecipes(new RecipeType<>(ThrowingInRecipeCategory.location,ThrowingInRecipe.class),ThrowingInRecipes);
        List<SenbakokiRecipe> SenbakokiRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.SenbakokiRecipe);
        registration.addRecipes(new RecipeType<>(SenbakokiRecipeCategory.location,SenbakokiRecipe.class),SenbakokiRecipes);
        List<FoxEatingRecipe> FoxEatingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.FoxEatingRecipe);
        registration.addRecipes(new RecipeType<>(FoxEatingRecipeCategory.location,FoxEatingRecipe.class),FoxEatingRecipes);
        List<SandpaperPolishingRecipe> SandpaperPolishingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.SandpaperPolishingRecipe);
        registration.addRecipes(new RecipeType<>(SandpaperPolishingRecipeCategory.location,SandpaperPolishingRecipe.class),SandpaperPolishingRecipes);
        List<ChiseledLacquerLogRecipe> ChiseledLacquerLogRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.ChiseledLacquerLogRecipe);
        registration.addRecipes(new RecipeType<>(ChiseledLacquerLogRecipeCategory.location,ChiseledLacquerLogRecipe.class),ChiseledLacquerLogRecipes);
        List<WoodElementCraftingRecipe> woodElementCraftingRecipes =recipeManager.getAllRecipesFor(RecipeTypeRegister.WoodElementCraftingRecipe);
        registration.addRecipes(new RecipeType<>(WoodElementCraftingRecipeCategory.location, WoodElementCraftingRecipe.class), woodElementCraftingRecipes);
        List<FireElementCraftingRecipe> fireElementCraftingRecipes =recipeManager.getAllRecipesFor(RecipeTypeRegister.FireElementCraftingRecipe);
        registration.addRecipes(new RecipeType<>(FireElementCraftingRecipeCategory.location, FireElementCraftingRecipe.class), fireElementCraftingRecipes);
        List<EarthElementCraftingRecipe> earthElementCraftingRecipes =recipeManager.getAllRecipesFor(RecipeTypeRegister.EarthElementCraftingRecipe);
        registration.addRecipes(new RecipeType<>(EarthElementCraftingRecipeCategory.location, EarthElementCraftingRecipe.class), earthElementCraftingRecipes);
        List<MetalElementCraftingRecipe> metalElementCraftingRecipes =recipeManager.getAllRecipesFor(RecipeTypeRegister.MetalElementCraftingRecipe);
        registration.addRecipes(new RecipeType<>(MetalElementCraftingRecipeCategory.location, MetalElementCraftingRecipe.class), metalElementCraftingRecipes);
        List<WaterElementCraftingRecipe> waterElementCraftingRecipes =recipeManager.getAllRecipesFor(RecipeTypeRegister.WaterElementCraftingRecipe);
        registration.addRecipes(new RecipeType<>(WaterElementCraftingRecipeCategory.location, WaterElementCraftingRecipe.class), waterElementCraftingRecipes);
        List<SilkFarmRecipe> SilkFarmRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.SilkwormFarmRecipe);
        registration.addRecipes(new RecipeType<>(SilkwormFarmRecipeCategory.location,SilkFarmRecipe.class),SilkFarmRecipes);
        List<KettleRecipe> KettleRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.KettleRecipe);
        registration.addRecipes(new RecipeType<>(KettleRecipeCategory.location,KettleRecipe.class),KettleRecipes);
        List<RainwaterTankRecipe> RainwaterTankRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.RainwaterTankRecipe);
        registration.addRecipes(new RecipeType<>(RainwaterTankRecipeCategory.location,RainwaterTankRecipe.class),RainwaterTankRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.fryer.get()), JEI_FRYING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.hammer.get()), JEI_HAMMERING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.oil_extractor.get()), JEI_OIL_EXTRACTING);
        registration.addRecipeCatalyst(new ItemStack(Items.WATER_BUCKET), JEI_THROWING_IN);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.senbakoki.get()), JEI_SENBAKOKI);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.sandpaper_block.get()), JEI_POLISHING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.chiseled_lacquer_log.get()), JEI_CHISELED_LACQUER_LOG);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.shichirin.get()), RecipeType.create( RecipeTypes.CAMPFIRE_COOKING.getUid().getNamespace(), RecipeTypes.CAMPFIRE_COOKING.getUid().getPath(), CampfireCookingRecipe.class));
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.wood_element_crafting_table.get()), JEI_WOOD_ELEMENT_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.fire_element_crafting_table.get()), JEI_FIRE_ELEMENT_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.earth_element_crafting_table.get()), JEI_EARTH_ELEMENT_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.metal_element_crafting_table.get()), JEI_METAL_ELEMENT_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.water_element_crafting_table.get()), JEI_WATER_ELEMENT_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.silkworm_farm.get()), JEI_SILKWORM);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.kettle.get()), JEI_KETTLE);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.rainwater_tank.get()), JEI_RAINWATER_TANK);
    }
}
