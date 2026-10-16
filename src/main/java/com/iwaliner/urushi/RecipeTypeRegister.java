package com.iwaliner.urushi;

import com.iwaliner.urushi.recipe.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeTypeRegister {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ModCoreUrushi.ModID);
    public static final RegistryObject<FryingRecipe.FryingSerializer> FryingSerializer
            = RECIPE_SERIALIZER.register("frying", com.iwaliner.urushi.recipe.FryingRecipe.FryingSerializer::new);
    public static final RegistryObject<HammeringRecipe.HammeringSerializer> HammeringSerializer
            = RECIPE_SERIALIZER.register("hammering", com.iwaliner.urushi.recipe.HammeringRecipe.HammeringSerializer::new);

    public static final RegistryObject<com.iwaliner.urushi.recipe.OilExtractingRecipe.OilExtractingSerializer> OilExtractingSerializer
            = RECIPE_SERIALIZER.register("oil_extracting", com.iwaliner.urushi.recipe.OilExtractingRecipe.OilExtractingSerializer::new);

    public static final RegistryObject<com.iwaliner.urushi.recipe.ThrowingInRecipe.ThrowingInSerializer> ThrowingInSerializer
            = RECIPE_SERIALIZER.register("throwing_in", com.iwaliner.urushi.recipe.ThrowingInRecipe.ThrowingInSerializer::new);
    public static final RegistryObject<SenbakokiRecipe.SenbakokiSerializer> SenbakokiSerializer
            = RECIPE_SERIALIZER.register("senbakoki", com.iwaliner.urushi.recipe.SenbakokiRecipe.SenbakokiSerializer::new);
    public static final RegistryObject<com.iwaliner.urushi.recipe.FoxEatingRecipe.FoxEatingSerializer> FoxEatingSerializer
            = RECIPE_SERIALIZER.register("fox_eating", com.iwaliner.urushi.recipe.FoxEatingRecipe.FoxEatingSerializer::new);
    public static final RegistryObject<SandpaperPolishingRecipe.SandpaperPolishingSerializer> SandpaperPolishingSerializer
            = RECIPE_SERIALIZER.register("polishing", com.iwaliner.urushi.recipe.SandpaperPolishingRecipe.SandpaperPolishingSerializer::new);
    public static final RegistryObject<com.iwaliner.urushi.recipe.ChiseledLacquerLogRecipe.ChiseledLacquerLogSerializer> ChiseledLacquerLogSerializer
            = RECIPE_SERIALIZER.register("chiseled_lacquer_log", com.iwaliner.urushi.recipe.ChiseledLacquerLogRecipe.ChiseledLacquerLogSerializer::new);
    public static final RegistryObject<WoodElementCraftingRecipe.WoodElementCraftingSerializer> WoodElementCraftingSerializer
            = RECIPE_SERIALIZER.register("wood_element_crafting", com.iwaliner.urushi.recipe.WoodElementCraftingRecipe.WoodElementCraftingSerializer::new);
    public static final RegistryObject<FireElementCraftingRecipe.FireElementCraftingSerializer> FireElementCraftingSerializer
            = RECIPE_SERIALIZER.register("fire_element_crafting", com.iwaliner.urushi.recipe.FireElementCraftingRecipe.FireElementCraftingSerializer::new);
    public static final RegistryObject<EarthElementCraftingRecipe.EarthElementCraftingSerializer> EarthElementCraftingSerializer
            = RECIPE_SERIALIZER.register("earth_element_crafting", com.iwaliner.urushi.recipe.EarthElementCraftingRecipe.EarthElementCraftingSerializer::new);
    public static final RegistryObject<MetalElementCraftingRecipe.MetalElementCraftingSerializer> MetalElementCraftingSerializer
            = RECIPE_SERIALIZER.register("metal_element_crafting", com.iwaliner.urushi.recipe.MetalElementCraftingRecipe.MetalElementCraftingSerializer::new);
    public static final RegistryObject<WaterElementCraftingRecipe.WaterElementCraftingSerializer> WaterElementCraftingSerializer
            = RECIPE_SERIALIZER.register("water_element_crafting", com.iwaliner.urushi.recipe.WaterElementCraftingRecipe.WaterElementCraftingSerializer::new);
    public static final RegistryObject<SilkFarmRecipe.SilkFarmSerializer> SilkwormFarmSerializer
            = RECIPE_SERIALIZER.register("silkworm", SilkFarmRecipe.SilkFarmSerializer::new);
    public static final RegistryObject<KettleRecipe.KettleSerializer> KettleSerializer
            = RECIPE_SERIALIZER.register("kettle", com.iwaliner.urushi.recipe.KettleRecipe.KettleSerializer::new);
    public static final RegistryObject<RainwaterTankRecipe.RainwaterTankSerializer> RainwaterTankSerializer
            = RECIPE_SERIALIZER.register("rainwater_tank", com.iwaliner.urushi.recipe.RainwaterTankRecipe.RainwaterTankSerializer::new);

     public static RecipeType<FryingRecipe> FryingRecipe = new FryingRecipe.FryingRecipeType();
    public static RecipeType<HammeringRecipe> HammeringRecipe = new HammeringRecipe.HammeringRecipeType();
    public static RecipeType<OilExtractingRecipe> OilExtractingRecipe = new OilExtractingRecipe.OilExtractingRecipeType();
    public static RecipeType<ThrowingInRecipe> ThrowingInRecipe = new ThrowingInRecipe.ThrowingInRecipeType();
    public static RecipeType<SenbakokiRecipe> SenbakokiRecipe = new SenbakokiRecipe.SenbakokiRecipeType();


    public static RecipeType<FoxEatingRecipe> FoxEatingRecipe = new FoxEatingRecipe.FoxEatingRecipeType();
    public static RecipeType<SandpaperPolishingRecipe> SandpaperPolishingRecipe = new SandpaperPolishingRecipe.SandpaperPolishingRecipeType();
    public static RecipeType<ChiseledLacquerLogRecipe> ChiseledLacquerLogRecipe = new ChiseledLacquerLogRecipe.ChiseledLacquerLogRecipeType();
    public static RecipeType<WoodElementCraftingRecipe> WoodElementCraftingRecipe = new WoodElementCraftingRecipe.WoodElementCraftingRecipeType();
    public static RecipeType<FireElementCraftingRecipe> FireElementCraftingRecipe = new FireElementCraftingRecipe.FireElementCraftingRecipeType();
    public static RecipeType<EarthElementCraftingRecipe> EarthElementCraftingRecipe = new EarthElementCraftingRecipe.EarthElementCraftingRecipeType();
    public static RecipeType<MetalElementCraftingRecipe> MetalElementCraftingRecipe = new MetalElementCraftingRecipe.MetalElementCraftingRecipeType();
    public static RecipeType<WaterElementCraftingRecipe> WaterElementCraftingRecipe = new WaterElementCraftingRecipe.WaterElementCraftingRecipeType();
    public static RecipeType<SilkFarmRecipe> SilkwormFarmRecipe = new SilkFarmRecipe.SilkFarmRecipeType();
    public static RecipeType<KettleRecipe> KettleRecipe = new KettleRecipe.KettleRecipeType();
    public static RecipeType<RainwaterTankRecipe> RainwaterTankRecipe = new RainwaterTankRecipe.RainwaterTankRecipeType();
    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZER.register(eventBus);
    }
}
