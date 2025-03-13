package com.iwaliner.urushi;

import com.iwaliner.urushi.blockentity.menu.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegister {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ModCoreUrushi.ModID);



    public static final RegistryObject<MenuType<FryerMenu>> FryerMenu = MENUS.register("fryer", () -> IForgeMenuType.create((windowId, inv, data) -> {return new FryerMenu(windowId, inv);}));
    public static final RegistryObject<MenuType<DoubledWoodenCabinetryMenu>> DoubledWoodenCabinetryMenu = MENUS.register("doubled_wooden_cabinetry", () -> IForgeMenuType.create((windowId, inv, data) -> {return new DoubledWoodenCabinetryMenu(windowId, inv);}));
    public static final RegistryObject<MenuType<com.iwaliner.urushi.blockentity.menu.UrushiHopperMenu>> UrushiHopperMenu = MENUS.register("urushi_hopper", () -> IForgeMenuType.create((windowId, inv, data) -> {return new UrushiHopperMenu(windowId, inv);}));
    public static final RegistryObject<MenuType<AutoCraftingTableMenu>> AutoCraftingTableMenu = MENUS.register("auto_crafting_table", () -> IForgeMenuType.create((windowId, inv, data) -> {return new AutoCraftingTableMenu(windowId, inv);}));
    public static final RegistryObject<MenuType<SilkwormFarmMenu>> SilkwormFarmMenu = MENUS.register("silkworm_farm", () -> IForgeMenuType.create((windowId, inv, data) -> {return new SilkwormFarmMenu(windowId, inv);}));
    public static final RegistryObject<MenuType<KettleMenu>> KettleMenu = MENUS.register("kettle", () -> IForgeMenuType.create((windowId, inv, data) -> {return new KettleMenu(windowId, inv);}));
    public static final RegistryObject<MenuType<TranslatableBookMenu>> TranslatableBookMenu = MENUS.register("translatable_book", () -> IForgeMenuType.create((windowId, inv, data) -> {return new TranslatableBookMenu(windowId);}));




    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
