package com.iwaliner.urushi;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegister {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ModCoreUrushi.ModID);
    public static final RegistryObject<SimpleParticleType> WoodElement = PARTICLES.register("wood_element", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FireElement = PARTICLES.register("fire_element", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> EarthElement = PARTICLES.register("earth_element", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> MetalElement = PARTICLES.register("metal_element", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> WaterElement = PARTICLES.register("water_element", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FallingRedLeaves = PARTICLES.register("falling_red_leaves", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FallingOrangeLeaves = PARTICLES.register("falling_orange_leaves", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FallingYellowLeaves = PARTICLES.register("falling_yellow_leaves", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FallingSakuraLeaves = PARTICLES.register("falling_sakura_leaves", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> WoodElementMedium = PARTICLES.register("wood_element_medium", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FireElementMedium = PARTICLES.register("fire_element_medium", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> EarthElementMedium = PARTICLES.register("earth_element_medium", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> MetalElementMedium = PARTICLES.register("metal_element_medium", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> WaterElementMedium = PARTICLES.register("water_element_medium", () -> new SimpleParticleType(true));


    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }
}
