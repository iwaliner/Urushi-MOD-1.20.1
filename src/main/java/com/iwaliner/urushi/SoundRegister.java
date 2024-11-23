package com.iwaliner.urushi;


import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegister {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModCoreUrushi.ModID);
    public static final RegistryObject<SoundEvent> WindBell=SOUNDS.register("wind_bell",()->SoundEvent.createVariableRangeEvent(new ResourceLocation(ModCoreUrushi.ModID,"wind_bell")));
    public static final RegistryObject<SoundEvent> UrushiAdvancements=SOUNDS.register("urushi_advancements",()->SoundEvent.createVariableRangeEvent(new ResourceLocation(ModCoreUrushi.ModID,"urushi_advancements")));
    public static final RegistryObject<SoundEvent> KakuriyoVillagerAmbient=SOUNDS.register("kakuriyo_villager_ambient",()->SoundEvent.createVariableRangeEvent(new ResourceLocation(ModCoreUrushi.ModID,"kakuriyo_villager_ambient")));
    public static final RegistryObject<SoundEvent> KakuriyoVillagerHurt=SOUNDS.register("kakuriyo_villager_hurt",()->SoundEvent.createVariableRangeEvent(new ResourceLocation(ModCoreUrushi.ModID,"kakuriyo_villager_hurt")));
    public static final RegistryObject<SoundEvent> KakuriyoVillagerDeath=SOUNDS.register("kakuriyo_villager_death",()->SoundEvent.createVariableRangeEvent(new ResourceLocation(ModCoreUrushi.ModID,"kakuriyo_villager_death")));
    public static final RegistryObject<SoundEvent> KakuriyoVillagerYes=SOUNDS.register("kakuriyo_villager_yes",()->SoundEvent.createVariableRangeEvent(new ResourceLocation(ModCoreUrushi.ModID,"kakuriyo_villager_yes")));


    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }

}
