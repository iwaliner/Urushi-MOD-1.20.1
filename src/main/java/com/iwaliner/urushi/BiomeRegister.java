package com.iwaliner.urushi;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class BiomeRegister {

    public static final DeferredRegister<Biome> BIOMES;
    public static final ResourceKey<Biome> SakuraForest;
    public static final ResourceKey<Biome> EulaliaPlains;
    public static final ResourceKey<Biome> AutumnForest;
    public static final ResourceKey<Biome> CedarForest;



    public static final List<ResourceKey<Biome>> KakuriyoList = new ArrayList<>();

    public BiomeRegister() {
    }

    public static void register(IEventBus eventBus) {
        BIOMES.register(eventBus);
        KakuriyoList.add(SakuraForest);
        KakuriyoList.add(EulaliaPlains);
        KakuriyoList.add(AutumnForest);
        KakuriyoList.add(CedarForest);


    }



    static {
        BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, ModCoreUrushi.ModID);
        SakuraForest = ResourceKey.create(Registries.BIOME, new ResourceLocation("sakura_forest"));
        EulaliaPlains =ResourceKey.create(Registries.BIOME, new ResourceLocation("eulalia_plains"));
        AutumnForest =ResourceKey.create(Registries.BIOME, new ResourceLocation("autumn_forest"));
        CedarForest =ResourceKey.create(Registries.BIOME, new ResourceLocation("cedar_forest"));



    }



}