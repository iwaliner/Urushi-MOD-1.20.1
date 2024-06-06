package com.iwaliner.urushi;

import com.iwaliner.urushi.fluidtype.HotSpringWaterFluidType;
import com.mojang.math.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluidTypeRegister {

    public static final DeferredRegister<FluidType> FLUID_TYPES=DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES,ModCoreUrushi.ModID);

    public static final RegistryObject<FluidType> HOT_SPRING_FLUID_TYPE = register("hot_spring_fluid",
            FluidType.Properties.create().lightLevel(2).density(1000).viscosity(1000).sound(SoundAction.get("drink"),
                    SoundEvents.BUCKET_FILL).canExtinguish(true).canConvertToSource(true));



    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new HotSpringWaterFluidType( properties));
    }
    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
