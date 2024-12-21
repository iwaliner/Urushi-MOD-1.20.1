package com.iwaliner.urushi;


import com.iwaliner.urushi.block.*;
import com.iwaliner.urushi.blockentity.renderer.*;
import com.iwaliner.urushi.blockentity.screen.*;
import com.iwaliner.urushi.entiity.food.model.*;
import com.iwaliner.urushi.entiity.food.renderer.*;
import com.iwaliner.urushi.entiity.model.CushionModel;
import com.iwaliner.urushi.entiity.model.OniModel;
import com.iwaliner.urushi.entiity.renderer.CushionRenderer;
import com.iwaliner.urushi.entiity.renderer.GhostRenderer;
import com.iwaliner.urushi.entiity.renderer.GiantSkeletonRenderer;
import com.iwaliner.urushi.entiity.renderer.KakuriyoVillagerRenderer;
import com.iwaliner.urushi.json.*;
import com.iwaliner.urushi.particle.*;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.ToggleKeyMappingPlus;
import com.iwaliner.urushi.util.UrushiUtils;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = ModCoreUrushi.ModID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetUp {
    public static final ModelLayerLocation RICE = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "rice_food"), "rice_food");
    public static final ModelLayerLocation KARAAGE = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "karaage_food"), "karaage_food");
    public static final ModelLayerLocation TOFU = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "tofu_food"), "tofu_food");
    public static final ModelLayerLocation ABURAAGE = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "aburaage_food"), "aburaage_food");
    public static final ModelLayerLocation DANGO = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "dango_food"), "dango_food");
    public static final ModelLayerLocation RICE_CAKE = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "rice_cake_food"), "rice_cake_food");
    public static final ModelLayerLocation ROASTED_RICE_CAKE = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "roasted_rice_cake_food"), "roasted_rice_cake_food");
    public static final ModelLayerLocation CUSHION = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "cushion"), "cushion");
    public static final ModelLayerLocation SUSHI = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "sushi_food"), "sushi_food");
    public static final ModelLayerLocation SALMON_ROE_SUSHI = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "salmon_roe_sushi_food"), "salmon_roe_sushi_food");
    public static final ModelLayerLocation SHRIMP_SUSHI = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "shrimp_sushi_food"), "shrimp_sushi_food");
    public static final ModelLayerLocation INARI = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "inari_food"), "inari_food");
    public static final ModelLayerLocation RAMEN = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "ramen_food"), "ramen_food");
    public static final ModelLayerLocation MISO_SOUP = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "miso_soup_food"), "miso_soup_food");
    public static final ModelLayerLocation KAKURIYO_VILLAGER = new ModelLayerLocation(new ResourceLocation(ModCoreUrushi.ModID, "kakuriyo_villager"), "kakuriyo_villager");


    public static KeyMapping connectionKey = new ToggleKeyMappingPlus("key.urushi.connectionKey", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, "key.urushi.category");
    @SubscribeEvent
    public static void keyRegister(RegisterKeyMappingsEvent event) {
        event.register(ClientSetUp.connectionKey);
    }

     /**エンティティの見た目を登録*/
    @SubscribeEvent
    public static void RegisterEntityRendererEvent(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegister.RiceFoodEntity.get(), RiceFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.TKGFoodEntity.get(), TKGFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.SekihanFoodEntity.get(), SekihanFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.GyudonFoodEntity.get(), GyudonFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.ButadonFoodEntity.get(), ButadonFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.KitsuneUdonFoodEntity.get(), KitsuneUdonFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.KaraageFoodEntity.get(), KaraageFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.TofuFoodEntity.get(), TofuFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.AburaageFoodEntity.get(), AburaageFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.KusaDangoFoodEntity.get(), KusaDangoFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.ColorDangoFoodEntity.get(), ColorDangoFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.MitarashiDangoFoodEntity.get(), MitarashiDangoFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.RiceCakeFoodEntity.get(), RiceCakeFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.RoastedRiceCakeFoodEntity.get(), RoastedRiceCakeFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.TsunaSushiFoodEntity.get(), TsunaSushiFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.SalmonSushiFoodEntity.get(), SalmonSushiFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.SquidSushiFoodEntity.get(), SquidSushiFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.EggSushiFoodEntity.get(), EggSushiFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.SalmonRoeSushiFoodEntity.get(), SalmonRoeSushiFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.ShrimpSushiFoodEntity.get(), ShrimpSushiFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.GravelSushiFoodEntity.get(), GravelSushiFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.InariFoodEntity.get(), InariFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.KitsunebiEntity.get(),  (p_174088_) -> {
            return new ThrownItemRenderer<>(p_174088_, 1.0F, true);
        });
        event.registerEntityRenderer(EntityRegister.Ghost.get(), GhostRenderer::new);
        event.registerEntityRenderer(EntityRegister.GianntSkeleton.get(), GiantSkeletonRenderer::new);
        event.registerEntityRenderer(EntityRegister.Cushion.get(), CushionRenderer::new);
        event.registerEntityRenderer(EntityRegister.Jufu.get(),  (p_174088_) -> {
            return new ThrownItemRenderer<>(p_174088_, 1.0F, true);
        });
        event.registerEntityRenderer(EntityRegister.JufuEffectDisplay.get(), FallingBlockRenderer::new);
        event.registerEntityRenderer(EntityRegister.MisoSoupFoodEntity.get(), MisoSoupFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.CheeseGyudonFoodEntity.get(), CheeseGyudonFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.CheeseGyudonWithOnsenEggFoodEntity.get(), CheeseGyudonWithOnsenEggFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.GreenOnionAndRawEggGyudonFoodEntity.get(), GreenOnionAndRawEggGyudonFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.MustardLeafAndCodCaviarGyudonFoodEntity.get(), MustardLeafAndCodCaviarGyudonFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.SakuraMochiFoodEntity.get(), SakuraMochiFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.OhagiFoodEntity.get(), OhagiFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.KusaMochiFoodEntity.get(), KusaMochiFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.SoySourceRamenFoodEntity.get(), SoySourceRamenFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.SaltRamenFoodEntity.get(), SaltRamenFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.MisoRamenFoodEntity.get(), MisoRamenFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.TonkotsuRamenFoodEntity.get(), TonkotsuRamenFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.MincedTunaBowlFoodEntity.get(), MincedTunaBowlFoodRenderer::new);
        event.registerEntityRenderer(EntityRegister.KakuriyoVillager.get(), KakuriyoVillagerRenderer::new);
    }

    /**エンティティのレイヤーを指定*/
    @SubscribeEvent
    public static void registerLayerEvent(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(RICE, RiceFoodModel::createBodyLayer);
        event.registerLayerDefinition(KARAAGE, KaraageFoodModel::createBodyLayer);
        event.registerLayerDefinition(TOFU, TofuFoodModel::createBodyLayer);
        event.registerLayerDefinition(ABURAAGE, AburaageFoodModel::createBodyLayer);
        event.registerLayerDefinition(DANGO, DangoFoodModel::createBodyLayer);
        event.registerLayerDefinition(RICE_CAKE, RiceCakeFoodModel::createBodyLayer);
        event.registerLayerDefinition(ROASTED_RICE_CAKE, RoastedRiceCakeFoodModel::createBodyLayer);
        event.registerLayerDefinition(CUSHION, CushionModel::createBodyLayer);
        event.registerLayerDefinition(SUSHI, SushiFoodModel::createBodyLayer);
        event.registerLayerDefinition(SALMON_ROE_SUSHI, SalmonRoeSushiFoodModel::createBodyLayer);
        event.registerLayerDefinition(SHRIMP_SUSHI, ShrimpSushiFoodModel::createBodyLayer);
        event.registerLayerDefinition(INARI, InariFoodModel::createBodyLayer);
        event.registerLayerDefinition(RAMEN, RamenFoodModel::createBodyLayer);
        event.registerLayerDefinition(MISO_SOUP, MisoSoupFoodModel::createBodyLayer);
        event.registerLayerDefinition(KAKURIYO_VILLAGER, OniModel::createBodyLayer);



    }
    @SubscribeEvent
    public static void registerItemColorEvent(RegisterColorHandlersEvent.Item event) {
        event.register((stack, i) -> {return 12300080;},ItemAndBlockRegister.grass_block_with_fallen_red_leaves.get());
        event.register((stack, i) -> {return 12300080;},ItemAndBlockRegister.grass_block_with_fallen_orange_leaves.get());
        event.register((stack, i) -> {return 12300080;},ItemAndBlockRegister.grass_block_with_fallen_yellow_leaves.get());
        event.register((stack, i) -> {return 12300080;},ItemAndBlockRegister.grass_block_with_fallen_japanese_apricot_leaves.get());
        event.register((stack, i) -> {return 12300080;},ItemAndBlockRegister.grass_block_with_fallen_sakura_leaves.get());
        event.register((stack, i) -> {return 12300080;},ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_red_leaves.get());
        event.register((stack, i) -> {return 12300080;},ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_orange_leaves.get());
        event.register((stack, i) -> {return 12300080;},ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_yellow_leaves.get());
        event.register((stack, i) -> {return 12300080;},ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_japanese_apricot_leaves.get());
        event.register((stack, i) -> {return 12300080;},ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_sakura_leaves.get());
        event.register((stack, i) -> {return 12300080;},ItemAndBlockRegister.kakuriyo_grass_block.get());
        event.register((stack, i) -> {return 13886461;},ItemAndBlockRegister.onsen_egg.get());
    }
    @SubscribeEvent
    public static void registerBlockColorEvent(RegisterColorHandlersEvent.Block event) {
        event.register((state, reader, pos, i) -> reader!=null&&pos!=null? BiomeColors.getAverageGrassColor(Objects.requireNonNull(reader), Objects.requireNonNull(pos)):12300080,ItemAndBlockRegister.grass_block_with_fallen_red_leaves.get());
        event.register((state, reader, pos, i) -> reader!=null&&pos!=null?BiomeColors.getAverageGrassColor(Objects.requireNonNull(reader), Objects.requireNonNull(pos)):12300080,ItemAndBlockRegister.grass_block_with_fallen_orange_leaves.get());
        event.register((state, reader, pos, i) -> reader!=null&&pos!=null?BiomeColors.getAverageGrassColor(Objects.requireNonNull(reader), Objects.requireNonNull(pos)):12300080,ItemAndBlockRegister.grass_block_with_fallen_yellow_leaves.get());
        event.register((state, reader, pos, i) -> reader!=null&&pos!=null?BiomeColors.getAverageGrassColor(Objects.requireNonNull(reader), Objects.requireNonNull(pos)):12300080,ItemAndBlockRegister.grass_block_with_fallen_japanese_apricot_leaves.get());
        event.register((state, reader, pos, i) -> reader!=null&&pos!=null?BiomeColors.getAverageGrassColor(Objects.requireNonNull(reader), Objects.requireNonNull(pos)):12300080,ItemAndBlockRegister.grass_block_with_fallen_sakura_leaves.get());
        event.register((state, reader, pos, i) -> reader!=null&&pos!=null?BiomeColors.getAverageGrassColor(Objects.requireNonNull(reader), Objects.requireNonNull(pos)):12300080,ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_red_leaves.get());
        event.register((state, reader, pos, i) -> reader!=null&&pos!=null?BiomeColors.getAverageGrassColor(Objects.requireNonNull(reader), Objects.requireNonNull(pos)):12300080,ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_orange_leaves.get());
        event.register((state, reader, pos, i) -> reader!=null&&pos!=null?BiomeColors.getAverageGrassColor(Objects.requireNonNull(reader), Objects.requireNonNull(pos)):12300080,ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_yellow_leaves.get());
        event.register((state, reader, pos, i) -> reader!=null&&pos!=null?BiomeColors.getAverageGrassColor(Objects.requireNonNull(reader), Objects.requireNonNull(pos)):12300080,ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_japanese_apricot_leaves.get());
        event.register((state, reader, pos, i) -> reader!=null&&pos!=null?BiomeColors.getAverageGrassColor(Objects.requireNonNull(reader), Objects.requireNonNull(pos)):12300080,ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_sakura_leaves.get());
        event.register((state, reader, pos, i) -> reader!=null&&pos!=null?BiomeColors.getAverageGrassColor(Objects.requireNonNull(reader), Objects.requireNonNull(pos)):12300080,ItemAndBlockRegister.kakuriyo_grass_block.get());
        event.register((state, reader, pos, i) -> reader!=null&&pos!=null?BiomeColors.getAverageWaterColor(Objects.requireNonNull(reader), Objects.requireNonNull(pos)):12300080,ItemAndBlockRegister.rainwater_tank.get());

    }
    /**パーティクルの見た目を指定*/
    @SubscribeEvent
    public static void registerParticlesEvent(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleRegister.FireElement.get(), ElementParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegister.WoodElement.get(), ElementParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegister.EarthElement.get(), ElementParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegister.MetalElement.get(), ElementParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegister.WaterElement.get(), ElementParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegister.FallingRedLeaves.get(), FallingRedLeavesParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegister.FallingOrangeLeaves.get(), FallingOrangeLeavesParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegister.FallingYellowLeaves.get(), FallingYellowLeavesParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegister.FallingSakuraLeaves.get(), FallingSakuraLeavesParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegister.WoodElementMedium.get(), MediumParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegister.FireElementMedium.get(), MediumParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegister.EarthElementMedium.get(), MediumParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegister.MetalElementMedium.get(), MediumParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegister.WaterElementMedium.get(), MediumParticle.Provider::new);

    }



    @Nullable
    @SubscribeEvent
    public static void RegisterRendererEvent(FMLClientSetupEvent event) {


        /**キーボード操作を登録*/
        /**ClientRegistry.registerKeyBinding(connectionKey);*/

        /**アイテムの状態を登録*/
        event.enqueueWork(() -> {
            ItemProperties.register(ItemAndBlockRegister.iron_katana.get(), new ResourceLocation(ModCoreUrushi.ModID, "ishurting"), (itemStack, clientWorld, livingEntity,i) -> (livingEntity instanceof Player &&livingEntity.swinging&&livingEntity.getMainHandItem()==itemStack)?1:0);

            ItemProperties.register(ItemAndBlockRegister.wood_element_magatama.get(), new ResourceLocation(ModCoreUrushi.ModID, "stored_amount"), (itemStack, clientWorld, livingEntity,i) -> (int)Mth.floor((float) ElementUtils.getStoredReiryokuAmount(itemStack)/400) );
            ItemProperties.register(ItemAndBlockRegister.fire_element_magatama.get(), new ResourceLocation(ModCoreUrushi.ModID, "stored_amount"), (itemStack, clientWorld, livingEntity,i) -> (int)Mth.floor((float) ElementUtils.getStoredReiryokuAmount(itemStack)/400) );
            ItemProperties.register(ItemAndBlockRegister.earth_element_magatama.get(), new ResourceLocation(ModCoreUrushi.ModID, "stored_amount"), (itemStack, clientWorld, livingEntity,i) -> (int)Mth.floor((float) ElementUtils.getStoredReiryokuAmount(itemStack)/400) );
            ItemProperties.register(ItemAndBlockRegister.metal_element_magatama.get(), new ResourceLocation(ModCoreUrushi.ModID, "stored_amount"), (itemStack, clientWorld, livingEntity,i) -> (int)Mth.floor((float) ElementUtils.getStoredReiryokuAmount(itemStack)/400) );
            ItemProperties.register(ItemAndBlockRegister.water_element_magatama.get(), new ResourceLocation(ModCoreUrushi.ModID, "stored_amount"), (itemStack, clientWorld, livingEntity,i) -> (int)Mth.floor((float) ElementUtils.getStoredReiryokuAmount(itemStack)/400) );

            ItemProperties.register(Item.byBlock(ItemAndBlockRegister.japanese_timber_bamboo.get()), new ResourceLocation(ModCoreUrushi.ModID, "event"), (itemStack, clientWorld, livingEntity,i) -> UrushiUtils.isShogatsu()? 1 : 0);
            ItemProperties.register(ItemAndBlockRegister.raw_rice.get(), new ResourceLocation(ModCoreUrushi.ModID, "is_april_fools"), (itemStack, clientWorld, livingEntity,i) -> UrushiUtils.isAprilFoolsDay()? 1 : 0);
            ItemProperties.register(ItemAndBlockRegister.rice.get(), new ResourceLocation(ModCoreUrushi.ModID, "is_april_fools"), (itemStack, clientWorld, livingEntity,i) -> UrushiUtils.isAprilFoolsDay()? 1 : 0);

        });

       /**コンテナにGUIを登録*/
        MenuScreens.register(MenuRegister.FryerMenu.get(), FryerScreen::new);
        MenuScreens.register(MenuRegister.DoubledWoodenCabinetryMenu.get(), DoubledWoodenCabinetryScreen::new);
        MenuScreens.register(MenuRegister.UrushiHopperMenu.get(), UrushiHopperScreen::new);
        MenuScreens.register(MenuRegister.AutoCraftingTableMenu.get(), AutoCraftingTableScreen::new);
        MenuScreens.register(MenuRegister.SilkwormFarmMenu.get(), SilkwormFarmScreen::new);
        MenuScreens.register(MenuRegister.KettleMenu.get(), KettleScreen::new);


       /**見た目が特殊なBlockEntityの見た目を登録*/
        BlockEntityRenderers.register(BlockEntityRegister.Sanbo.get(), SanboRenderer::new);
        BlockEntityRenderers.register(BlockEntityRegister.Shichirin.get(), ShichirinRenderer::new);
        BlockEntityRenderers.register(BlockEntityRegister.Hokora.get(), HokoraRenderer::new);
        BlockEntityRenderers.register(BlockEntityRegister.Plate.get(), PlateRenderer::new);
        BlockEntityRenderers.register(BlockEntityRegister.BambooBasket.get(), BambooBasketRenderer::new);
        BlockEntityRenderers.register(BlockEntityRegister.DoubledWoodenCabinetryBlockEntity.get(), DoubledWoodenCabinetryRenderer::new);



        ModCoreUrushi.underDevelopmentList.add(Item.byBlock(ItemAndBlockRegister.senryoubako.get()));
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.additional_heart.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.giant_skeleton_spawn_egg.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.kakuriyo_villager_spawn_egg.get());
        ModCoreUrushi.underDevelopmentList.add(Item.byBlock(ItemAndBlockRegister.coin.get()));
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.white_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.orange_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.magenta_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.light_blue_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.yellow_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.lime_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.pink_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.gray_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.light_gray_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.cyan_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.purple_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.blue_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.brown_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.green_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.red_silk.get());
        ModCoreUrushi.underDevelopmentList.add(ItemAndBlockRegister.black_silk.get());


        ModCoreUrushi.urushiTabContents.add(ItemAndBlockRegister.additional_heart);
        ModCoreUrushi.urushiTabContents.add(ItemAndBlockRegister.ghost_spawn_egg);
        ModCoreUrushi.urushiTabContents.add(ItemAndBlockRegister.giant_skeleton_spawn_egg);
        ModCoreUrushi.urushiTabContents.add(ItemAndBlockRegister.kakuriyo_villager_spawn_egg);



        /**jsonファイルを自動生成するために開発環境のパスを登録*/
        if(ModCoreUrushi.isDebug) {
        FMLPaths.GAMEDIR.get();
        ModCoreUrushi.assetsDirectory = new File(FMLPaths.GAMEDIR.get().getParent().toString() + "/src/main/resources/assets/urushi/");
        ModCoreUrushi.assetsInBuildDirectory = new File(FMLPaths.GAMEDIR.get().getParent().toString() + "/build/resources/main/assets/urushi/");
        ModCoreUrushi.dataDirectory = new File(FMLPaths.GAMEDIR.get().getParent().toString() + "/src/main/resources/data/");
        ModCoreUrushi.dataInBuildDirectory = new File(FMLPaths.GAMEDIR.get().getParent().toString() + "/build/resources/main/data/");





            ModCoreUrushi.blockSelfDropList.remove("black_kakuriyo_portal_frame");
            ModCoreUrushi.blockSelfDropList.remove("earth_element_puzzle_block");
            ModCoreUrushi.blockSelfDropList.remove("element_puzzle_controller_a");
            ModCoreUrushi.blockSelfDropList.remove("element_puzzle_controller_b");
            ModCoreUrushi.blockSelfDropList.remove("element_puzzle_controller_c");
            ModCoreUrushi.blockSelfDropList.remove("fire_element_puzzle_block");
            ModCoreUrushi.blockSelfDropList.remove("ghost_black_kakuriyo_portal_frame");
            ModCoreUrushi.blockSelfDropList.remove("ghost_kakuriyo_portal_core");
            ModCoreUrushi.blockSelfDropList.remove("ghost_red_kakuriyo_portal_frame");
            ModCoreUrushi.blockSelfDropList.remove("kakuriyo_portal");
            ModCoreUrushi.blockSelfDropList.remove("kakuriyo_portal_core");
            ModCoreUrushi.blockSelfDropList.remove("metal_element_puzzle_block");
            ModCoreUrushi.blockSelfDropList.remove("random_element_puzzle_block");
            ModCoreUrushi.blockSelfDropList.remove("red_kakuriyo_portal_frame");
            ModCoreUrushi.blockSelfDropList.remove("water_element_puzzle_block");
            ModCoreUrushi.blockSelfDropList.remove("wood_element_puzzle_block");

            MineableTagGenerator.INSTANCE.registerPickaxeMineableTag(ModCoreUrushi.pickaxeList);
            MineableTagGenerator.INSTANCE.registerAxeMineableTag(ModCoreUrushi.axeList);
            MineableTagGenerator.INSTANCE.registerShovelMineableTag(ModCoreUrushi.shovelList);
            MineableTagGenerator.INSTANCE.registerHoeMineableTag(ModCoreUrushi.hoeList);
            RequiredToolMaterialTagGenerator.INSTANCE.registerWoodenToolTag(ModCoreUrushi.woodenToolList);
            RequiredToolMaterialTagGenerator.INSTANCE.registerStoneToolTag(ModCoreUrushi.stoneToolList);
            RequiredToolMaterialTagGenerator.INSTANCE.registerIronToolTag(ModCoreUrushi.ironToolList);
            RequiredToolMaterialTagGenerator.INSTANCE.registerGoldenToolTag(ModCoreUrushi.goldenToolList);
            RequiredToolMaterialTagGenerator.INSTANCE.registerDiamondToolTag(ModCoreUrushi.diamondToolList);
            RequiredToolMaterialTagGenerator.INSTANCE.registerNetheriteToolTag(ModCoreUrushi.netheriteToolList);
            for(String name:ModCoreUrushi.blockSelfDropList){
                LootTableGenerator.INSTANCE.registerSimpleBlockLootTable(name);
            }




        }
    }

}