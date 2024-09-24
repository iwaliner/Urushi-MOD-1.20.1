package com.iwaliner.urushi;

import com.iwaliner.urushi.block.*;
import com.iwaliner.urushi.blockentity.ShichirinBlockEntity;

import com.iwaliner.urushi.network.FramedBlockTextureConnectionData;
import com.iwaliner.urushi.network.FramedBlockTextureConnectionProvider;
import com.iwaliner.urushi.recipe.SenbakokiRecipe;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.ElementBlock;
import com.iwaliner.urushi.util.interfaces.ElementItem;
import com.iwaliner.urushi.util.interfaces.Tiered;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.*;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;

@Mod("urushi")
public class ModCoreUrushi {
    public static final String ModID = "urushi";
    public static File dataDirectory;
    public static File dataInBuildDirectory;
    public static File assetsDirectory;
    public static File assetsInBuildDirectory;

    public static List<String> pickaxeList=new ArrayList<>();
    public static List<RegistryObject<Block>> blockSelfDropList=new ArrayList<>();
    public static List<String> axeList=new ArrayList<>();
    public static List<String> shovelList=new ArrayList<>();
    public static List<String> hoeList=new ArrayList<>();
    public static List<String> woodenToolList=new ArrayList<>();
    public static List<String> stoneToolList=new ArrayList<>();
    public static List<String> ironToolList=new ArrayList<>();
    public static List<String> goldenToolList=new ArrayList<>();
    public static List<String> diamondToolList=new ArrayList<>();
    public static List<String> netheriteToolList=new ArrayList<>();
    public static List<Item> underDevelopmentList=new ArrayList<>();
    public static List<RegistryObject<Item>> redstoneTabContents=new ArrayList<>();
    public static List<RegistryObject<Item>> urushiTabContents=new ArrayList<>();
    public static List<RegistryObject<Item>> urushiPlasterTabContents=new ArrayList<>();
    public static List<RegistryObject<Item>> urushiWoodTabContents=new ArrayList<>();
    public static List<RegistryObject<Item>> urushiFoodTabContents=new ArrayList<>();
    public static List<RegistryObject<Item>> urushiMagicTabContents=new ArrayList<>();

    public static boolean isDebug=FMLPaths.GAMEDIR.get().toString().contains("イワライナー")&&FMLPaths.GAMEDIR.get().toString().contains("run");
    public static Logger logger = LogManager.getLogger("urushi");


    public ModCoreUrushi() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        /**コンフィグを登録*/
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,ConfigUrushi.spec,"urushi.toml");

        /**アイテムとブロックを登録*/
        ItemAndBlockRegister.register(modEventBus);

        /**ブロックエンティティ(旧タイルエンティティ)を登録*/
        BlockEntityRegister.register(modEventBus);

        /**エンティティを登録*/
        EntityRegister.register(modEventBus);

        /**流体を登録*/
        FluidRegister.register(modEventBus);
        FluidTypeRegister.register(modEventBus);

        /**バイオームを登録*/
       // BiomeRegister.register(modEventBus);

        /**レシピタイプを登録*/
        RecipeTypeRegister.register(modEventBus);

        /**メニュー(旧コンテナ)を登録*/
        MenuRegister.register(modEventBus);

        /**ディメンションを登録*/
        DimensionRegister.register();

        /**パーティクルを登録*/
       ParticleRegister.register(modEventBus);

        /**サウンドを登録*/
        SoundRegister.register(modEventBus);


        FeatureRegister.register(modEventBus);

       // PlacedFeatureRegister.register(modEventBus);

//        ConfiguredFeatureRegister.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

    }
    private void CreativeTabEvent(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
               event.accept(ItemAndBlockRegister.auto_crafting_table.get());
            event.accept(ItemAndBlockRegister.advanced_auto_crafting_table.get());
            event.accept(ItemAndBlockRegister.urushi_hopper.get());
            event.accept(ItemAndBlockRegister.fox_hopper.get());
            event.accept(ItemAndBlockRegister.invisible_button_item.get());
            event.accept(ItemAndBlockRegister.invisible_lever_item.get());
            event.accept(ItemAndBlockRegister.invisible_pressure_plate_item.get());
            event.accept(ItemAndBlockRegister.sandpaper_block.get());
            event.accept(ItemAndBlockRegister.ghost_dirt.get());
            event.accept(ItemAndBlockRegister.ghost_stone.get());
            event.accept(ItemAndBlockRegister.ghost_concrete.get());
            event.accept(ItemAndBlockRegister.ghost_plaster.get());
            event.accept(ItemAndBlockRegister.ghost_wattle_and_daub.get());
            event.accept(ItemAndBlockRegister.ghost_sand_coast.get());
            event.accept(ItemAndBlockRegister.wooden_cabinetry_slab.get());
            event.accept(ItemAndBlockRegister.wooden_cabinetry.get());
            event.accept(ItemAndBlockRegister.doubled_wooden_cabinetry.get());
        }
    }
    /**燃料を登録*/
    @SubscribeEvent
    public void FuelEvent(FurnaceFuelBurnTimeEvent event) {

        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.japanese_timber_bamboo.get().asItem(),0.5F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.straw.get().asItem(),0.65F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.rice_crop.get().asItem(),0.65F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.azuki_crop.get().asItem(),0.65F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.soy_crop.get().asItem(),0.65F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.green_onion_crop.get().asItem(),0.65F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.lycoris.get().asItem(),0.65F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.lantern_plant.get().asItem(),0.5F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.shiitake_item.get().asItem(),0.65F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.matured_japanese_apricot_fruit.get().asItem(),0.65F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.eulalia.get().asItem(),0.5F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.lacquer_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.japanese_apricot_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.glowing_japanese_apricot_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.sakura_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.glowing_japanese_apricot_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.cypress_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.japanese_cedar_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.red_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.orange_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.yellow_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.lacquer_sapling.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.japanese_apricot_sapling.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.sakura_sapling.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.glowing_japanese_apricot_sapling.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.big_sakura_sapling.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.glowing_sakura_sapling.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.glowing_big_sakura_sapling.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.cypress_sapling.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.japanese_cedar_sapling.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.red_sapling.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.orange_sapling.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.yellow_sapling.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.raw_rice.get().asItem(),0.7F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.ajisai.get().asItem(),0.65F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.yomotsuhegui_fruit.get().asItem(),0.65F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.fallen_japanese_apricot_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.fallen_sakura_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.fallen_red_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.fallen_orange_leaves.get().asItem(),0.3F);
        ComposterBlock.COMPOSTABLES.put(ItemAndBlockRegister.fallen_yellow_leaves.get().asItem(),0.3F);



        Item burnItem = event.getItemStack().getItem();
        if (burnItem==ItemAndBlockRegister.bamboo_charcoal.get()) {
            event.setBurnTime(1600);
        }else if(burnItem==ItemAndBlockRegister.japanese_apricot_bark.get()) {
            event.setBurnTime(200);
        }else if(burnItem==ItemAndBlockRegister.sakura_bark.get()) {
            event.setBurnTime(200);
        }else if(burnItem==ItemAndBlockRegister.cypress_bark.get()) {
            event.setBurnTime(200);
        }else if(burnItem==ItemAndBlockRegister.wood_chip.get()) {
            event.setBurnTime(200);
        }else if(burnItem== Item.byBlock(ItemAndBlockRegister.bamboo_charcoal_block.get())) {
            event.setBurnTime(16000);
        }
    }

    /**プレイヤーの移動速度を上昇*/
    @SubscribeEvent
    public void PlayerSpeedEvent(EntityEvent.EnteringSection event) {
        if(ConfigUrushi.TurnOnSpeedUp.get()) {
            if (event.getEntity() instanceof Player) {

                Player entityPlayer = (Player) event.getEntity();
                if(!entityPlayer.isPassenger()) {
                    Objects.requireNonNull(entityPlayer.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.116D);
                    entityPlayer.getAttributes().save();

                }
            }
        }

    }


    /**草を壊して種が出るように*/
    @SubscribeEvent
    public void GrassDropEvent(BlockEvent.BreakEvent event) {
        if (!event.getPlayer().isCreative() && (event.getLevel().getBlockState(event.getPos()).getBlock()==Blocks.FERN || event.getLevel().getBlockState(event.getPos()).getBlock()==Blocks.TALL_GRASS || event.getLevel().getBlockState(event.getPos()).getBlock()==Blocks.GRASS) ) {
            float rand =( event.getLevel()).getRandom().nextFloat();
            if(rand >= 0.3F){
                return;
            }
            Block given_item = null;
            if(rand < 0.075F){
                given_item = ItemAndBlockRegister.rice_crop.get();
            } else if(rand < 0.15F){
                given_item = ItemAndBlockRegister.soy_crop.get();
            } else if(rand < 0.225F){
                given_item = ItemAndBlockRegister.azuki_crop.get();
            } else if(rand < 0.3F){
                given_item = ItemAndBlockRegister.green_onion_crop.get();
            }

            ItemEntity entity = new ItemEntity((Level) event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(given_item));
            event.getLevel().addFreshEntity(entity);

        }
    }

    /**玉鋼作るときに右クリックおしっぱだとブロックがドロップして壊れる*/
    @SubscribeEvent
    public void HammerCancelEvent(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().getBlockState(event.getPos()).getBlock() instanceof IronIngotBlock){
            {
                if(event.getEntity() instanceof Player) {
                    if( event.getEntity().getCooldowns().isOnCooldown(ItemAndBlockRegister.hammer.get())) {
                        event.getLevel().destroyBlock(event.getPos(),true);
                        event.setCanceled(true);
                    }
                }
            }
        }

        if(isDebug){
            LevelAccessor level=event.getLevel();
            BlockPos pos=event.getPos();
            BlockState currentState=event.getLevel().getBlockState(pos);
            BlockState sojoState=ElementUtils.getSojoBlock(ElementUtils.getSojoBlock(event.getLevel().getBlockState(event.getPos())));
            if(sojoState!=null){
                event.getLevel().setBlockAndUpdate(event.getPos(),sojoState);
            }

        }
    }

    /**孫の手で手の届く範囲を広げる*/
  /*  @SubscribeEvent
    public void MagonoteEvent(PlayerInteractEvent event) {

                if(event.getEntity() instanceof Player) {

                    int amount=0;

                    for (int i = 0; i < event.getPlayer().getInventory().getContainerSize(); ++i)
                    {
                        ItemStack stack =  event.getPlayer().getInventory().getItem(i);
                        if(stack.getItem()==ItemAndBlockRegister.magonote.get()){
                            amount+=stack.getCount();
                            if(event instanceof PlayerInteractEvent.RightClickBlock||event instanceof PlayerInteractEvent.LeftClickBlock) {
                                event.getPlayer().getInventory().getItem(i).hurtAndBreak(1, event.getPlayer(), (x) -> {
                                });
                            }
                        }
                    }
                      Objects.requireNonNull(event.getPlayer().getAttribute(ForgeMod.REACH_DISTANCE.get())).setBaseValue(5D+amount*1D);


                }


    }*/

    /**葉の上に落下したとき落下ダメージを受けないように*/
    @SubscribeEvent
    public void LeavesDamageEvent(LivingHurtEvent event) {
        if(event.getSource()==event.getEntity().damageSources().fall()){
            Entity entity = event.getEntity();
            if(entity.level().getBlockState(entity.blockPosition().below()).getBlock() instanceof LeavesBlock){
                event.setCanceled(true);
            }
        }
    }

    /**砂が海岸や海系のバイオーム内で水に接すると塩を含んだ砂になる*/
    @SubscribeEvent
    public void SaltEvent(BlockEvent.NeighborNotifyEvent event) {
        LevelAccessor level = event.getLevel();
        BlockPos pos = event.getPos();
        Holder<Biome> biome = level.getBiome(pos);
        BlockState blockState = event.getState();

        if(!( biome.is(BiomeTags.IS_BEACH) || biome.is(BiomeTags.IS_OCEAN) || biome.is(BiomeTags.IS_DEEP_OCEAN) )){
            return;
        }

        if (blockState.getFluidState().is(Fluids.WATER)||blockState.getFluidState().is(Fluids.FLOWING_WATER)) {

            if(pos.getY()>62&&level.getBlockState(pos.below()).getBlock() == Blocks.SAND){
                level.setBlock(pos.below(), ItemAndBlockRegister.salt_and_sand.get().defaultBlockState(), 2);
                level.playSound((Player) null, pos.below(), SoundEvents.SAND_BREAK, SoundSource.BLOCKS, 1.0F, 1F);
            }
        }
    }

    /**油揚げを狐が食べると狐火に*/
    @SubscribeEvent
    public void FoxEvent(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Fox &&event.getFrom().getItem()== ItemAndBlockRegister.aburaage.get()) {
            event.getEntity().setItemSlot(EquipmentSlot.MAINHAND,new ItemStack(ItemAndBlockRegister.kitsunebiItem.get()));
        }

    }
    /**アイテムに説明書きを追加*/
    @SubscribeEvent
    public void ItemTooltipEvent(ItemTooltipEvent event) {

        ItemStack stack = event.getItemStack();
        Item item = event.getItemStack().getItem();
        Block block = Block.byItem(item);
        List<Component> tooltipList = event.getToolTip();
        if (item instanceof ElementItem elementItem) {
            ElementType elementType = elementItem.getElementType();
            String item_id;
            ChatFormatting chatformat_id;
            switch (elementType){
                case WoodElement -> {
                    item_id = "info.urushi.wood_element_item";
                    chatformat_id = ChatFormatting.DARK_GREEN;
                }
                case FireElement -> {
                    item_id = "info.urushi.fire_element_item";
                    chatformat_id = ChatFormatting.DARK_RED;
                }
                case EarthElement -> {
                    item_id = "info.urushi.earth_element_item";
                    chatformat_id = ChatFormatting.GOLD;
                }
                case MetalElement -> {
                    item_id = "info.urushi.metal_element_item";
                    chatformat_id = ChatFormatting.AQUA;
                }
                case WaterElement -> {
                    item_id = "info.urushi.water_element_item";
                    chatformat_id = ChatFormatting.DARK_PURPLE;
                }
                default -> {
                    return;
                }
            }
            tooltipList.add((Component.translatable(item_id)).withStyle(chatformat_id));
        }
        if (block instanceof Tiered tiered) {
            int tier = tiered.getTier();
            String tier_id = "info.urushi.tier" + tier;
            tooltipList.add((Component.translatable(tier_id)).withStyle(ChatFormatting.GRAY));

        }
        if(block instanceof ElementBlock){
            CompoundTag tag=BlockItem.getBlockEntityData(stack);
            if(tag==null){
                return;
            }

            if(tag.contains("storedReiryoku")){
                ElementType elementType = ((ElementBlock) block).getElementType();
                String item_id;
                ChatFormatting chatformat_id;
                switch (elementType){
                    case WoodElement -> {
                        chatformat_id = ChatFormatting.DARK_GREEN;
                    }
                    case FireElement -> {
                        chatformat_id = ChatFormatting.DARK_RED;
                    }
                    case EarthElement -> {
                        chatformat_id = ChatFormatting.GOLD;
                    }
                    case MetalElement -> {
                        chatformat_id = ChatFormatting.AQUA;
                    }
                    case WaterElement -> {
                        chatformat_id = ChatFormatting.DARK_PURPLE;
                    }
                    default -> {
                        return;
                    }
                }
                tooltipList.add((Component.translatable("info.urushi.stored_reiryoku_amount").append(" "+tag.getInt("storedReiryoku"))).withStyle(chatformat_id));
            }
        }
        if(!ConfigUrushi.disableBlockElementDisplaying.get()){
            if (block != Blocks.AIR) {
                BlockState state = block.defaultBlockState();
                if (ElementUtils.isWoodElement(state)) {
                    tooltipList.add((Component.translatable("info.urushi.wood_element_block"))
                            .withStyle(ChatFormatting.DARK_GREEN));
                }
                if (ElementUtils.isFireElement(state)) {
                    tooltipList.add((Component.translatable("info.urushi.fire_element_block"))
                            .withStyle(ChatFormatting.DARK_RED));
                }
                if (ElementUtils.isEarthElement(state)) {
                    tooltipList.add((Component.translatable("info.urushi.earth_element_block"))
                            .withStyle(ChatFormatting.GOLD));
                }
                if (ElementUtils.isMetalElement(state)) {
                    tooltipList.add((Component.translatable("info.urushi.metal_element_block"))
                            .withStyle(ChatFormatting.AQUA));
                }
                if (ElementUtils.isWaterElement(state)) {
                    tooltipList.add((Component.translatable("info.urushi.water_element_block"))
                            .withStyle(ChatFormatting.DARK_PURPLE));
                }
            }
        }

        if(stack.getTag()!=null){
            CompoundTag tag=stack.getTag();
            if(tag.contains("cookingEnum")){
                int i=tag.getInt("cookingEnum");
                int level=ShichirinBlockEntity.getCookingLevel(i);
                ChatFormatting color=ChatFormatting.WHITE;
                if(i<5){
                    color= ChatFormatting.AQUA;
                }else if(i<14){
                    color= ChatFormatting.YELLOW;
                }else{
                    color= ChatFormatting.RED;
                }
                if(ShichirinBlockEntity.getCookingType(i).equals("undercooked")){
                    tooltipList.add((Component.translatable("info.urushi.undercooked" ).append(" "+level)).withStyle(color));
                }else if(ShichirinBlockEntity.getCookingType(i).equals("wellcooked")){
                    tooltipList.add((Component.translatable("info.urushi.wellcooked" ).append(" "+level)).withStyle(color));
                }else{
                    tooltipList.add((Component.translatable("info.urushi.overcooked" ).append(" "+level)).withStyle(color));
                }
            }
        }
        if(underDevelopmentList.contains(stack.getItem())){
            UrushiUtils.setInfoWithColor(event.getToolTip(),"under_development",ChatFormatting.YELLOW);
        }
        if(stack.getItem()==Item.byBlock(ItemAndBlockRegister.cypress_sapling.get())){
            UrushiUtils.setInfo(event.getToolTip(),"cypress_sapling");
        }else if(stack.getItem()==Item.byBlock(ItemAndBlockRegister.japanese_cedar_sapling.get())){
            UrushiUtils.setInfo(event.getToolTip(),"japanese_cedar_sapling");
        }else if(stack.getItem()==Item.byBlock(ItemAndBlockRegister.lacquer_sapling.get())){
            UrushiUtils.setInfo(event.getToolTip(),"lacquer_sapling");
        }
        if(block instanceof AbstractFramedBlock||block instanceof FramedPaneBlock){
            tooltipList.add((Component.translatable("info.urushi.framed_block1" )).withStyle(ChatFormatting.GRAY));
            String keyString= ClientSetUp. connectionKey.getKey().getName();
            String begin=".";
            int beginIndex = keyString.indexOf(begin);
            String preExtractedKey = keyString.substring(beginIndex+1);
            int beginIndex2 = preExtractedKey.indexOf(begin);
            String extractedKey = preExtractedKey.substring(beginIndex2+1);
            tooltipList.add((Component.translatable("info.urushi.framed_block2")
                    .append(" '"+extractedKey+"' ").append(Component.translatable("info.urushi.framed_block3")))
                    .withStyle(ChatFormatting.GRAY));

        }
    }

    /**食べた後の処理*/
    @SubscribeEvent
    public void FoodEatEvent(LivingEntityUseItemEvent.Finish event) {
        LivingEntity livingEntity=event.getEntity();
        ItemStack stack=event.getResultStack();
        CompoundTag tag=stack.getTag();
        if(tag==null || !tag.contains("cookingEnum")){
            return;
        }

        int ID=tag.getInt("cookingEnum");
        int level=ShichirinBlockEntity.getCookingLevel(ID);
        String cookingtype = ShichirinBlockEntity.getCookingType(ID);
        if(cookingtype.equals("undercooked")){
            livingEntity.addEffect(new MobEffectInstance(MobEffects.HUNGER,300+60*level,level+15));
        }else if(cookingtype.equals("wellcooked")){
            livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,40+level*10, 1));
            if(ID==9){
                livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,200,0));
            }
        }else{
            livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON,10+10*level,1));
        }

    }

    /**バニラのルートテーブルに内容を追加*/
    @SubscribeEvent
    public void LoottableEvent(LootTableLoadEvent event) {
        if(event.getName().equals(BuiltInLootTables.FISHING_FISH)){
            event.getTable().addPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemAndBlockRegister.sweetfish.get()).setWeight(25))
                    .add(LootItem.lootTableItem(ItemAndBlockRegister.tsuna.get()).setWeight(25)).build());
        }else if(event.getName().equals(BuiltInLootTables.SIMPLE_DUNGEON)||
                event.getName().equals(BuiltInLootTables.SPAWN_BONUS_CHEST)||
                event.getName().equals(BuiltInLootTables.VILLAGE_PLAINS_HOUSE)){
            event.getTable().addPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(ItemAndBlockRegister.lacquer_sapling.get()).setWeight(30)).build());
        }
    }
    /**バニラのルートテーブルに内容を追加*/
    @SubscribeEvent
    public void EntityDropItemEvent(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        if(entity instanceof Squid){
       event.getDrops().add(new ItemEntity(entity.level(),entity.getX(),entity.getY(),entity.getZ(),
               new ItemStack(ItemAndBlockRegister.squid_sashimi.get())));
        }
    }

    @SubscribeEvent
    public void ElementPaperCraftEvent(PlayerInteractEvent.RightClickBlock event) {

        if(event.getEntity() == null){
            return;
        }

        BlockPos pos = event.getPos();
        BlockState blockState=event.getLevel().getBlockState(pos);
        Block block=blockState.getBlock();
        LivingEntity livingEntity = (LivingEntity)event.getEntity();

        if(!event.getEntity().isSuppressingBounce()&&block!=ItemAndBlockRegister.sanbo_tier1.get()&&!blockState.hasBlockEntity()&&block!=Blocks.CRAFTING_TABLE) {
            InteractionHand hand = event.getHand();
            Item itemGet = null;
            if (livingEntity.getItemInHand(hand).getItem() == ItemAndBlockRegister.shide.get()) {
                if (ElementUtils.isWoodElement(blockState)) {
                    itemGet = ItemAndBlockRegister.wood_element_paper.get();
                } else if (ElementUtils.isFireElement(blockState)) {
                    itemGet = ItemAndBlockRegister.fire_element_paper.get();
                } else if (ElementUtils.isEarthElement(blockState)) {
                    itemGet = ItemAndBlockRegister.earth_element_paper.get();
                } else if (ElementUtils.isMetalElement(blockState)) {
                    itemGet = ItemAndBlockRegister.metal_element_paper.get();
                } else if (ElementUtils.isWaterElement(blockState)) {
                    itemGet = ItemAndBlockRegister.water_element_paper.get();
                }
                if(itemGet == null){
                    return;
                }
                livingEntity.setItemInHand(hand, new ItemStack(itemGet, livingEntity.getItemInHand(hand).getCount()));
                event.getLevel().playSound((Player) null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1F, 1F);
            }
        }
    }
    @SubscribeEvent
    public void MorningEvent(PlayerWakeUpEvent event) {
        if(event.getEntity().level().getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
            for (ServerLevel serverlevel : Objects.requireNonNull(event.getEntity().level().getServer()).getAllLevels()) {
                serverlevel.setDayTime((long) 24000);
            }
        }
    }
    @SubscribeEvent
    public void AttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(FramedBlockTextureConnectionProvider.FRAMED_BLOCK_TEXTURE_CONNECTION).isPresent()) {
                event.addCapability(new ResourceLocation(ModID, "properties"), new FramedBlockTextureConnectionProvider());
            }
        }
    }
    @SubscribeEvent
    public void RegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(FramedBlockTextureConnectionData.class);
    }



}
