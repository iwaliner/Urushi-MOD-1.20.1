package com.iwaliner.urushi;

import com.iwaliner.urushi.block.HotSpringWaterBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FluidRegister {

    public static final DeferredRegister<Fluid> FLUIDS=DeferredRegister.create(ForgeRegistries.FLUIDS,ModCoreUrushi.ModID);


  public static final RegistryObject<FlowingFluid> HotSpringStill = FLUIDS.register("still_hot_spring_water",
          () -> new ForgeFlowingFluid.Source(FluidRegister.HOT_SPRING_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> HotSpringFlow = FLUIDS.register("flowing_hot_spring_water",
            () -> new ForgeFlowingFluid.Flowing(FluidRegister.HOT_SPRING_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties HOT_SPRING_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            FluidTypeRegister.HOT_SPRING_FLUID_TYPE, HotSpringStill, HotSpringFlow)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ItemAndBlockRegister.HotSpringBlock)
            .bucket(ItemAndBlockRegister.hot_spring_bucket);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
