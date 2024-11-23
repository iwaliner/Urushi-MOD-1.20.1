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
    public static final RecipeType<WoodElementTier1CraftingRecipe> JEI_WOOD_ELEMENT_TIER1_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "wood_element_tier1_crafting", WoodElementTier1CraftingRecipe.class);
    public static final RecipeType<FireElementTier1CraftingRecipe> JEI_FIRE_ELEMENT_TIER1_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "fire_element_tier1_crafting", FireElementTier1CraftingRecipe.class);
    public static final RecipeType<EarthElementTier1CraftingRecipe> JEI_EARTH_ELEMENT_TIER1_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "earth_element_tier1_crafting", EarthElementTier1CraftingRecipe.class);
    public static final RecipeType<MetalElementTier1CraftingRecipe> JEI_METAL_ELEMENT_TIER1_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "metal_element_tier1_crafting", MetalElementTier1CraftingRecipe.class);
    public static final RecipeType<WaterElementTier1CraftingRecipe> JEI_WATER_ELEMENT_TIER1_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "water_element_tier1_crafting", WaterElementTier1CraftingRecipe.class);
    public static final RecipeType<WoodElementTier2CraftingRecipe> JEI_WOOD_ELEMENT_TIER2_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "wood_element_tier2_crafting", WoodElementTier2CraftingRecipe.class);
    public static final RecipeType<FireElementTier2CraftingRecipe> JEI_FIRE_ELEMENT_TIER2_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "fire_element_tier2_crafting", FireElementTier2CraftingRecipe.class);
    public static final RecipeType<EarthElementTier2CraftingRecipe> JEI_EARTH_ELEMENT_TIER2_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "earth_element_tier2_crafting", EarthElementTier2CraftingRecipe.class);
    public static final RecipeType<MetalElementTier2CraftingRecipe> JEI_METAL_ELEMENT_TIER2_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "metal_element_tier2_crafting", MetalElementTier2CraftingRecipe.class);
    public static final RecipeType<WaterElementTier2CraftingRecipe> JEI_WATER_ELEMENT_TIER2_CRAFTING =RecipeType.create(ModCoreUrushi.ModID, "water_element_tier2_crafting", WaterElementTier2CraftingRecipe.class);
    public static final RecipeType<FoxEatingRecipe> JEI_FOX_EATING =RecipeType.create(ModCoreUrushi.ModID, "fox_eating", FoxEatingRecipe.class);
    public static final RecipeType<SilkFarmRecipe> JEI_SILKWORM =RecipeType.create(ModCoreUrushi.ModID, "silkworm", SilkFarmRecipe.class);
    public static final RecipeType<KettleRecipe> JEI_KETTLE =RecipeType.create(ModCoreUrushi.ModID, "kettle", KettleRecipe.class);





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
        registration.addRecipeCategories(new WoodElementTier1CraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FireElementTier1CraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new EarthElementTier1CraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MetalElementTier1CraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new WaterElementTier1CraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new WoodElementTier2CraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new FireElementTier2CraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new EarthElementTier2CraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MetalElementTier2CraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new WaterElementTier2CraftingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new SilkwormFarmRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new KettleRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
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
        List<WoodElementTier1CraftingRecipe> WoodElementTier1CraftingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.WoodElementTier1CraftingRecipe);
        registration.addRecipes(new RecipeType<>(WoodElementTier1CraftingRecipeCategory.location,WoodElementTier1CraftingRecipe.class),WoodElementTier1CraftingRecipes);
        List<FireElementTier1CraftingRecipe> FireElementTier1CraftingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.FireElementTier1CraftingRecipe);
        registration.addRecipes(new RecipeType<>(FireElementTier1CraftingRecipeCategory.location,FireElementTier1CraftingRecipe.class),FireElementTier1CraftingRecipes);
        List<EarthElementTier1CraftingRecipe> EarthElementTier1CraftingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.EarthElementTier1CraftingRecipe);
        registration.addRecipes(new RecipeType<>(EarthElementTier1CraftingRecipeCategory.location,EarthElementTier1CraftingRecipe.class),EarthElementTier1CraftingRecipes);
        List<MetalElementTier1CraftingRecipe> MetalElementTier1CraftingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.MetalElementTier1CraftingRecipe);
        registration.addRecipes(new RecipeType<>(MetalElementTier1CraftingRecipeCategory.location,MetalElementTier1CraftingRecipe.class),MetalElementTier1CraftingRecipes);
        List<WaterElementTier1CraftingRecipe> WaterElementTier1CraftingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.WaterElementTier1CraftingRecipe);
        registration.addRecipes(new RecipeType<>(WaterElementTier1CraftingRecipeCategory.location,WaterElementTier1CraftingRecipe.class),WaterElementTier1CraftingRecipes);

        List<WoodElementTier2CraftingRecipe> WoodElementTier2CraftingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.WoodElementTier2CraftingRecipe);
        registration.addRecipes(new RecipeType<>(WoodElementTier2CraftingRecipeCategory.location,WoodElementTier2CraftingRecipe.class),WoodElementTier2CraftingRecipes);
        List<FireElementTier2CraftingRecipe> FireElementTier2CraftingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.FireElementTier2CraftingRecipe);
        registration.addRecipes(new RecipeType<>(FireElementTier2CraftingRecipeCategory.location,FireElementTier2CraftingRecipe.class),FireElementTier2CraftingRecipes);
        List<EarthElementTier2CraftingRecipe> EarthElementTier2CraftingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.EarthElementTier2CraftingRecipe);
        registration.addRecipes(new RecipeType<>(EarthElementTier2CraftingRecipeCategory.location,EarthElementTier2CraftingRecipe.class),EarthElementTier2CraftingRecipes);
        List<MetalElementTier2CraftingRecipe> MetalElementTier2CraftingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.MetalElementTier2CraftingRecipe);
        registration.addRecipes(new RecipeType<>(MetalElementTier2CraftingRecipeCategory.location,MetalElementTier2CraftingRecipe.class),MetalElementTier2CraftingRecipes);
        List<WaterElementTier2CraftingRecipe> WaterElementTier2CraftingRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.WaterElementTier2CraftingRecipe);
        registration.addRecipes(new RecipeType<>(WaterElementTier2CraftingRecipeCategory.location,WaterElementTier2CraftingRecipe.class),WaterElementTier2CraftingRecipes);
        List<SilkFarmRecipe> SilkFarmRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.SilkwormFarmRecipe);
        registration.addRecipes(new RecipeType<>(SilkwormFarmRecipeCategory.location,SilkFarmRecipe.class),SilkFarmRecipes);
        List<KettleRecipe> KettleRecipes=recipeManager.getAllRecipesFor(RecipeTypeRegister.KettleRecipe);
        registration.addRecipes(new RecipeType<>(KettleRecipeCategory.location,KettleRecipe.class),KettleRecipes);
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
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.wood_element_crafting_table_tier1.get()), JEI_WOOD_ELEMENT_TIER1_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.fire_element_crafting_table_tier1.get()), JEI_FIRE_ELEMENT_TIER1_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.earth_element_crafting_table_tier1.get()), JEI_EARTH_ELEMENT_TIER1_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.metal_element_crafting_table_tier1.get()), JEI_METAL_ELEMENT_TIER1_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.water_element_crafting_table_tier1.get()), JEI_WATER_ELEMENT_TIER1_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.wood_element_crafting_table_tier2.get()), JEI_WOOD_ELEMENT_TIER2_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.fire_element_crafting_table_tier2.get()), JEI_FIRE_ELEMENT_TIER2_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.earth_element_crafting_table_tier2.get()), JEI_EARTH_ELEMENT_TIER2_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.metal_element_crafting_table_tier2.get()), JEI_METAL_ELEMENT_TIER2_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.water_element_crafting_table_tier2.get()), JEI_WATER_ELEMENT_TIER2_CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.silkworm_farm.get()), JEI_SILKWORM);
        registration.addRecipeCatalyst(new ItemStack(ItemAndBlockRegister.kettle.get()), JEI_KETTLE);
    }
}
