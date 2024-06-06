package com.iwaliner.urushi;


import com.iwaliner.urushi.world.feature.BlockNearWaterReplaceFeature;
import com.iwaliner.urushi.world.feature.BlockReplaceFeature;
import com.iwaliner.urushi.world.feature.JapaneseTimberBambooFeature;
import com.iwaliner.urushi.world.feature.KakuriyoPortalFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class FeatureRegister {
    public static final DeferredRegister<Feature<?>> Features = DeferredRegister.create(ForgeRegistries.FEATURES, ModCoreUrushi.ModID);
    public static final RegistryObject<Feature<JapaneseTimberBambooFeature.Configuration>> Bamboo=Features.register("bamboo", () -> new JapaneseTimberBambooFeature(JapaneseTimberBambooFeature.Configuration.CODEC));
    public static final RegistryObject<Feature<BlockReplaceFeature.Configuration>> BLOCK_REPLACE=Features.register("block_replace", () -> new BlockReplaceFeature(BlockReplaceFeature.Configuration.CODEC));
    public static final RegistryObject<Feature<BlockNearWaterReplaceFeature.Configuration>> BLOCK_REPLACE_NEAR_WATER=Features.register("block_replace_near_water", () -> new BlockNearWaterReplaceFeature(BlockNearWaterReplaceFeature.Configuration.CODEC));
    public static final RegistryObject<Feature<KakuriyoPortalFeature.Configuration>> KakuriyoPortal=Features.register("kakuriyo_portal", () -> new KakuriyoPortalFeature(KakuriyoPortalFeature.Configuration.CODEC));
    public static <T extends FeatureConfiguration> RegistryObject<Feature<T>> register(String name, Supplier<Feature<T>> featureSupplier) {
        return Features.register(name, featureSupplier);
    }
    public static void register(IEventBus eventBus) {
        Features.register(eventBus);
    }
}
