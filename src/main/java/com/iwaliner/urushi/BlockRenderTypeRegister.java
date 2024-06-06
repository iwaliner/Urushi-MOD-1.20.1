package com.iwaliner.urushi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ModCoreUrushi.ModID ,value = Dist.CLIENT)
public class BlockRenderTypeRegister {
    @SubscribeEvent
    public static void onRenderTypeSetup(FMLClientSetupEvent event) {
        BlockColors blockcolors = Minecraft.getInstance().getBlockColors();
        ItemColors itemcolors = Minecraft.getInstance().getItemColors();
        blockcolors.register((state, reader, pos, i) -> BiomeColors.getAverageGrassColor(reader, pos),
                ItemAndBlockRegister.grass_block_with_fallen_red_leaves.get(),
                ItemAndBlockRegister.grass_block_with_fallen_orange_leaves.get(),
                ItemAndBlockRegister.grass_block_with_fallen_yellow_leaves.get(),
                ItemAndBlockRegister.grass_block_with_fallen_japanese_apricot_leaves.get(),
                ItemAndBlockRegister.grass_block_with_fallen_sakura_leaves.get(),
                ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_red_leaves.get(),
                ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_orange_leaves.get(),
                ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_yellow_leaves.get(),
                ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_japanese_apricot_leaves.get(),
                ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_sakura_leaves.get(),
                ItemAndBlockRegister.kakuriyo_grass_block.get());
        itemcolors.register((stack, i) -> {
                    return 12300080;
                },
                ItemAndBlockRegister.grass_block_with_fallen_red_leaves.get(),
                ItemAndBlockRegister.grass_block_with_fallen_orange_leaves.get(),
                ItemAndBlockRegister.grass_block_with_fallen_yellow_leaves.get(),
                ItemAndBlockRegister.grass_block_with_fallen_japanese_apricot_leaves.get(),
                ItemAndBlockRegister.grass_block_with_fallen_sakura_leaves.get(),
                ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_red_leaves.get(),
                ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_orange_leaves.get(),
                ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_yellow_leaves.get(),
                ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_japanese_apricot_leaves.get(),
                ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_sakura_leaves.get(),
                ItemAndBlockRegister.kakuriyo_grass_block.get());
        itemcolors.register((stack, i) -> {
                    return 13886461;
                },
                ItemAndBlockRegister.onsen_egg.get());

       // ItemBlockRenderTypes.setRenderLayer(FluidRegister.HotSpringStill.get(), RenderType.translucent());
      //  ItemBlockRenderTypes.setRenderLayer(FluidRegister.HotSpringFlow.get(), RenderType.translucent());






    }
}
