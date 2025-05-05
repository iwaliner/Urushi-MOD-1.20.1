package com.iwaliner.urushi.util;

import com.iwaliner.urushi.ConfigUrushi;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.block.HorizonalRotateSlabBlock;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.commands.CommandFunction;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3f;

import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.List;

public class UrushiUtils {
    public  static Direction getDirectionFromInt(int i){
        return switch (i) {
            case 0 -> Direction.DOWN;
            case 1 -> Direction.UP;
            //case 2 -> Direction.NORTH;
            default -> Direction.NORTH;
            case 3 -> Direction.SOUTH;
            case 4 -> Direction.WEST;
            case 5 -> Direction.EAST;
        };
    }
    public  static int getIntFromDirection(Direction direction){
        return switch (direction) {
            case DOWN -> 0;
            case UP -> 1;
            case NORTH -> 2;
            case SOUTH -> 3;
            case WEST -> 4;
            case EAST -> 5;
            default -> 6;
        };
    }
    public static void setInfo(List<Component> list,String string){
        list.add((Component.translatable("info.urushi."+string )).withStyle(ChatFormatting.GRAY));
    }
    public static void setInfoWithColor(List<Component> list,String string,ChatFormatting chatFormatting){
        list.add((Component.translatable("info.urushi."+string )).withStyle(chatFormatting));
    }
    public static void setBlinkingInfoWithColor(List<Component> list,String string,Level level,ChatFormatting color1,ChatFormatting color2){
        try {
            if(level==null){
                list.add((Component.translatable("info.urushi." + string)).withStyle(color1));
            }else {
                long gametime = level.getGameTime() % 20;
                if (gametime < 10) {
                    list.add((Component.translatable("info.urushi." + string)).withStyle(color1));
                } else {
                    list.add((Component.translatable("info.urushi." + string)).withStyle(color2));
                }
            }
        }catch (Exception ignored){

        }

    }
    public static void BlockChangeNeighborStateSurvey(Level level, BlockPos pos, BlockState detectBlock, BlockState placeBlock, SoundEvent soundEvent){
            if (BlockNeighborStateSurvey(level,pos,detectBlock)) {
               level.setBlock(pos, placeBlock, 2);
                level.playSound((Player) null,pos,soundEvent, SoundSource.BLOCKS, 1.0F, 1F);
            }
    }
    public static void BlockChangeNeighborFluidSurvey(Level level, BlockPos pos,Fluid fluid, BlockState placeBlock, SoundEvent soundEvent){
        if (BlockNeighborFluidSurvey(level,pos,fluid)) {
            level.setBlock(pos, placeBlock, 2);
            level.playSound((Player) null,pos,soundEvent, SoundSource.BLOCKS, 1.0F, 1F);
        }
    }
    public static boolean BlockNeighborStateSurvey(Level level, BlockPos pos, BlockState detectBlock){
        boolean flag = false;
        for (int i = 0; i < 6; i++) {
            if (level.getBlockState(pos.relative(UrushiUtils.getDirectionFromInt(i))) ==detectBlock) {
                flag = true;
            }
        }
      return flag;
    }
    public static boolean BlockNeighborFluidSurvey(Level level, BlockPos pos, Fluid fluid){
        boolean flag = false;
        for (int i = 0; i < 6; i++) {
            if (level.getFluidState(pos.relative(UrushiUtils.getDirectionFromInt(i))).is(fluid)) {
                flag = true;
            }
        }
        return flag;
    }
    public static boolean BlockNeighborBlockSurvey(Level level, BlockPos pos,Block block){
        boolean flag = false;
        for (int i = 0; i < 6; i++) {
            if (level.getBlockState(pos.relative(UrushiUtils.getDirectionFromInt(i))).getBlock() ==block) {
                flag = true;
            }
        }
        return flag;
    }
    public static boolean isShogatsu() {
        LocalDate localdate = LocalDate.now();
        int i = localdate.get(ChronoField.DAY_OF_MONTH);
        int j = localdate.get(ChronoField.MONTH_OF_YEAR);
        return j == 1 && i <=7;
    }
    public static boolean isSetsubun() {
        LocalDate localdate = LocalDate.now();
        int i = localdate.get(ChronoField.DAY_OF_MONTH);
        int j = localdate.get(ChronoField.MONTH_OF_YEAR);
        return j == 2 && i <=3;
    }
    public static boolean isHigan() {
        LocalDate localdate = LocalDate.now();
        int i = localdate.get(ChronoField.DAY_OF_MONTH);
        int j = localdate.get(ChronoField.MONTH_OF_YEAR);
        return (j == 3 && i>=17&& i <=24)||(j == 9 && i>=19&& i <=27);
    }
    public static boolean isTanabata() {
        LocalDate localdate = LocalDate.now();
        int i = localdate.get(ChronoField.DAY_OF_MONTH);
        int j = localdate.get(ChronoField.MONTH_OF_YEAR);
        return j == 7 && i>=5&& i <=7;
    }
    public static boolean isAprilFoolsDay() {
        LocalDate localdate = LocalDate.now();
        int i = localdate.get(ChronoField.DAY_OF_MONTH);
        int j = localdate.get(ChronoField.MONTH_OF_YEAR);
        return j == 4 && i ==1;
    }
    public static VoxelShape rotateSimpleBoxShapeHorizontally(VoxelShape baseShape, int angle){
        double x_min=baseShape.min(Direction.Axis.X)*16D;
        double x_max=baseShape.max(Direction.Axis.X)*16D;
        double y_min=baseShape.min(Direction.Axis.Y)*16D;
        double y_max=baseShape.max(Direction.Axis.Y)*16D;
        double z_min=baseShape.min(Direction.Axis.Z)*16D;
        double z_max=baseShape.max(Direction.Axis.Z)*16D;
        if(angle==90){
            return Block.box(16D-z_max,y_min,x_min,16D-z_min,y_max,x_max);
        }else if(angle==180){
            return Block.box(16D-x_max,y_min,16D-z_max,16D-x_min,y_max,16D-z_min);
        }else if(angle==270){
            return Block.box(z_min,y_min,16D-x_max,z_max,y_max,16D-x_min);
        }else{
            return baseShape;
        }
    }
    public static VoxelShape rotateSimpleBoxShapeHorizontally(VoxelShape baseShape,Direction direction){
        int angle;
        switch (direction){
            case EAST -> angle = 90;
            case SOUTH -> angle = 180;
            case WEST -> angle = 270;
            default -> angle = 0;
        }
        return rotateSimpleBoxShapeHorizontally(baseShape,angle);
    }
    public static boolean isMinecraftObject(String id){
        if(id.isEmpty()){
            return false;
        }
        String[] strings=id.split("\\.");
        if(strings.length<=1){
            return false;
        }
        return strings[1].equals("minecraft");
    }

    public static boolean isUrushiObject(String id){
        if(id.isEmpty()){
            return false;
        }
        String[] strings=id.split("\\.");
        if(strings.length<=1){
            return false;
        }
        return strings[1].equals("urushi");
    }
   public static boolean isSlab(Block block){
        return block instanceof SlabBlock ||block instanceof HorizonalRotateSlabBlock;
    }
    public static Map<Item,String> getRiceBallFillingItem(){
        Map<Item,String> map=new HashMap<>();
        map.put(ItemAndBlockRegister.ghost_core.get(),"levitation");
        map.put(Items.BLAZE_POWDER,"ignite");
        map.put(ItemAndBlockRegister.salmon_sashimi.get(),"strength");
        map.put(Items.GLOWSTONE_DUST,"glow");
        map.put(ItemAndBlockRegister.pickled_japanese_apricot.get(),"slow_fall");
        map.put(Items.DRIED_KELP,"water_breathing");
        map.put(Items.GUNPOWDER,"explode");
        map.put(ItemAndBlockRegister.onsen_egg.get(),"jump");
        map.put(ItemAndBlockRegister.rice_malt.get(),"nausea");
        map.put(Items.COPPER_INGOT,"slowness");
        map.put(Items.SPIDER_EYE,"poison");
        map.put(Items.SNOWBALL,"freeze");
        return map;
    }
    public static void onCraftingRiceBall(Item filling,ItemStack riceBall){
            CompoundTag tag=new CompoundTag();
            boolean flag=false;
            if(filling==ItemAndBlockRegister.ghost_core.get()){
                tag.putString("effect","levitation");
                flag=true;
            }else if(filling==Items.BLAZE_POWDER){
                tag.putString("effect","ignite");
                flag=true;
            }else if(filling==ItemAndBlockRegister.salmon_sashimi.get()){
                tag.putString("effect","strength");
                flag=true;
            }else if(filling==Items.GLOWSTONE_DUST){
                tag.putString("effect","glow");
                flag=true;
            }else if(filling==ItemAndBlockRegister.pickled_japanese_apricot.get()){
                tag.putString("effect","slow_fall");
                flag=true;
            }else if(filling==Items.DRIED_KELP){
                tag.putString("effect","water_breathing");
                flag=true;
            }else if(filling==Items.GUNPOWDER){
                tag.putString("effect","explode");
                flag=true;
            }else if(filling==ItemAndBlockRegister.onsen_egg.get()){
                tag.putString("effect","jump");
                flag=true;
            }else if(filling==ItemAndBlockRegister.rice_malt.get()){
                tag.putString("effect","nausea");
                flag=true;
            }else if(filling==Items.COPPER_INGOT){
                tag.putString("effect","slowness");
                flag=true;
            }else if(filling==Items.SPIDER_EYE){
                tag.putString("effect","poison");
                flag=true;
            }else if(filling==Items.SNOWBALL){
                tag.putString("effect","freeze");
                flag=true;
            }
            if(flag) {
                riceBall.setTag(tag);
            }
    }
    public static ItemStack getRandomRiceBall(int stackSize, RandomSource random){
        ItemStack stack=new ItemStack(ItemAndBlockRegister.rice_ball.get(),stackSize);
        if(stack.getTag()==null){
            stack.setTag(new CompoundTag());
        }
        CompoundTag tag=stack.getTag();
        int i=random.nextInt(12);
        switch (i){
            case 0 -> tag.putString("effect","levitation");
            case 1 -> tag.putString("effect","ignite");
            case 2 -> tag.putString("effect","strength");
            case 3 -> tag.putString("effect","glow");
            case 4 -> tag.putString("effect","slow_fall");
            case 5 -> tag.putString("effect","water_breathing");
            case 6 -> tag.putString("effect","explode");
            case 7 -> tag.putString("effect","jump");
            case 8 -> tag.putString("effect","nausea");
            case 9 -> tag.putString("effect","slowness");
            case 10 -> tag.putString("effect","poison");
            case 11 -> tag.putString("effect","freeze");
        }
        stack.setTag(tag);
        return stack;
    }
    public static void runFunction(Level level,BlockPos pos,String commandUserName,String functionName){
        MinecraftServer server = level.getServer();
        if(server!=null) {
            CommandFunction.CacheableFunction function = new CommandFunction.CacheableFunction(new ResourceLocation(ModCoreUrushi.ModID, functionName));
            CommandSourceStack commandSourceStack=new CommandSourceStack(CommandSource.NULL, pos.getCenter(), new Vec2(0f,0f),  (ServerLevel)level , 4, commandUserName, Component.empty(), server, null);
            function.get(server.getFunctions()).ifPresent((p_289236_) -> {
                server.getFunctions().execute(p_289236_, commandSourceStack.withSuppressedOutput().withPermission(4));
            });
        }
    }
    public static void runFunction(Level level, Player player, float rotX, float rotY, Entity entity, String functionName){
        MinecraftServer server = level.getServer();
        if(server!=null) {
            CommandFunction.CacheableFunction function = new CommandFunction.CacheableFunction(new ResourceLocation(ModCoreUrushi.ModID, functionName));
            CommandSourceStack commandSourceStack=new CommandSourceStack(player, player.position(), new Vec2(rotX,rotY),  (ServerLevel)level , 4, player.getName().toString(), player.getName(), server, entity);
            function.get(server.getFunctions()).ifPresent((p_289236_) -> {
                server.getFunctions().execute(p_289236_, commandSourceStack.withSuppressedOutput().withPermission(4));
            });
        }
    }
    public static void setTitle(ServerPlayer player,Component component){
        player.connection.send(new ClientboundSetTitleTextPacket(component));
    }
    public static void displayImage(GuiGraphics guiGraphics, String textureName, Window window){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, -90.0F);
        guiGraphics.pose().scale(0.45F,0.45F,0.45F);
        guiGraphics.blit(new ResourceLocation(ModCoreUrushi.ModID,"textures/gui/"+textureName+".png"), window.getGuiScaledWidth()+250, window.getGuiScaledHeight()-100, 0, 0, 256, 256);
        guiGraphics.pose().popPose();
    }
    public static void displayToggleKeyImage(GuiGraphics guiGraphics, String textureName, int width,int height){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((float)(width/2 ), (float)(height / 2), 0.0F);
       guiGraphics.pose().scale(0.05F,0.05F,1F);
        guiGraphics.blit(new ResourceLocation(ModCoreUrushi.ModID,"textures/gui/"+textureName+".png"), (width/2)-80, (height/2)-230, 0, 0, 256, 256);
        guiGraphics.pose().popPose();
    }
    public static void displayImage(GuiGraphics guiGraphics, String textureName, int width,int height){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(0.45F,0.45F,0.45F);
        guiGraphics.blit(new ResourceLocation(ModCoreUrushi.ModID,"textures/gui/"+textureName+".png"), width, height, 0, 0, 256, 256);
        guiGraphics.pose().popPose();
    }
    public static boolean isJapanese(){
        return   Minecraft.getInstance().getLanguageManager().getSelected().equals("ja_jp");
    }
    public static void getFuriganaList(List<Component> tooltipList,Item item,Block block){
        if(UrushiUtils.isJapanese()&& ConfigUrushi.indicateFurigana.get()) {
            if (item == ItemAndBlockRegister.cupric_oxide_kawara.get()||block == ItemAndBlockRegister.cupric_oxide_kawara_block.get()||block == ItemAndBlockRegister.cupric_oxide_kawara_slab.get()||block == ItemAndBlockRegister.cupric_oxide_kawara_stairs.get()||block == ItemAndBlockRegister.cupric_oxide_kawara_wall.get()||block == ItemAndBlockRegister.cupric_oxide_kawara_roof_45.get()||block == ItemAndBlockRegister.cupric_oxide_kawara_roof_225.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.ryokusyou");
            }else if (item == ItemAndBlockRegister.blue_kawara.get()||block == ItemAndBlockRegister.blue_kawara_block.get()||block == ItemAndBlockRegister.blue_kawara_slab.get()||block == ItemAndBlockRegister.blue_kawara_stairs.get()||block == ItemAndBlockRegister.blue_kawara_wall.get()||block == ItemAndBlockRegister.blue_kawara_roof_45.get()||block == ItemAndBlockRegister.blue_kawara_roof_225.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.seiroku");
            }else if (block == ItemAndBlockRegister.thatched_block.get()||block == ItemAndBlockRegister.thatched_slab.get()||block == ItemAndBlockRegister.thatched_stairs.get()||block == ItemAndBlockRegister.thatched_roof_45.get()||block == ItemAndBlockRegister.thatched_roof_225.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.kaya");
            }else if (block == ItemAndBlockRegister.hiwadabuki_block.get()||block == ItemAndBlockRegister.hiwadabuki_slab.get()||block == ItemAndBlockRegister.hiwadabuki_stairs.get()||block == ItemAndBlockRegister.hiwadabuki_roof_45.get()||block == ItemAndBlockRegister.hiwadabuki_roof_225.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.hiwada");
            }else if (block == ItemAndBlockRegister.green_tatami_carpet.get()||block == ItemAndBlockRegister.brown_tatami_carpet.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.goza");
            }else if (block == ItemAndBlockRegister.rough_stone.get()||block == ItemAndBlockRegister.rough_stone_slab.get()||block == ItemAndBlockRegister.rough_stone_stairs.get()||block == ItemAndBlockRegister.rough_stone_wall.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.nozura");
            }else if (block == ItemAndBlockRegister.wooden_cabinetry_slab.get()||block == ItemAndBlockRegister.wooden_cabinetry.get()||block == ItemAndBlockRegister.doubled_wooden_cabinetry.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.tansu");
            }else if (block == ItemAndBlockRegister.andon.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.andon");
            }else if (block == ItemAndBlockRegister.ariake_andon.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.ariake_andon");
            }else if (block == ItemAndBlockRegister.kasuga_lantern.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.kasuga_tourou");
            }else if (block == ItemAndBlockRegister.long_chochin.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.chochin");
            }else if (block == ItemAndBlockRegister.copper_giboshi.get()||block == ItemAndBlockRegister.iron_giboshi.get()||block == ItemAndBlockRegister.gold_giboshi.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.giboshi");
            }else if (block == ItemAndBlockRegister.blank_fusuma.get()||block == ItemAndBlockRegister.blank_antique_fusuma.get()||block == ItemAndBlockRegister.landscape_fusuma.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.fusuma");
            }else if (block == ItemAndBlockRegister.blue_sayagata_fusuma.get()||block == ItemAndBlockRegister.red_sayagata_fusuma.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.fusuma");
                UrushiUtils.setInfo(tooltipList, "furigana.sayagata");
            }else if (block == ItemAndBlockRegister.blue_seigaiha_fusuma.get()||block == ItemAndBlockRegister.red_seigaiha_fusuma.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.fusuma");
                UrushiUtils.setInfo(tooltipList, "furigana.seigaiha");
            }else if (block == ItemAndBlockRegister.cyan_noren.get()||block == ItemAndBlockRegister.magenta_noren.get()||block == ItemAndBlockRegister.red_noren.get()||block == ItemAndBlockRegister.deep_blue_noren.get()||block == ItemAndBlockRegister.sushi_noren.get()||block == ItemAndBlockRegister.men_onsen_noren.get()||block == ItemAndBlockRegister.women_onsen_noren.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.noren");
            }else if (block == ItemAndBlockRegister.rainwater_tank.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.tensuioke");
            }else if (block == ItemAndBlockRegister.silkworm.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.kaiko");
            }else if (block == ItemAndBlockRegister.silkworm_farm.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.yousan");
            }else if (item == ItemAndBlockRegister.cocoon.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.mayu");
            }else if (item == ItemAndBlockRegister.silk_thread.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.kinuito");
            }else if (item == ItemAndBlockRegister.silk.get()||block == ItemAndBlockRegister.silk_block.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.kempu");
            }else if (block == ItemAndBlockRegister.kakuriyo_dirt.get()||block == ItemAndBlockRegister.kakuriyo_grass_block.get()||block == ItemAndBlockRegister.kakuriyo_portal.get()||block == ItemAndBlockRegister.kakuriyo_portal_core.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.kakuriyo");
            }else if (block == ItemAndBlockRegister.yomi_stone.get()||block == ItemAndBlockRegister.cobbled_yomi_stone.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.yomi");
            }else if (block == ItemAndBlockRegister.jadeite_ore.get()||item == ItemAndBlockRegister.jadeite.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.hisui");
            }else if (block == ItemAndBlockRegister.lycoris.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.manjusyage");
            }else if (item == ItemAndBlockRegister.hammer.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.kanazuchi");
            }else if (block == ItemAndBlockRegister.sikkui.get()||block == ItemAndBlockRegister.sikkui_slab.get()||block == ItemAndBlockRegister.sikkui_stairs.get()||block == ItemAndBlockRegister.sikkui_bars.get()||block == ItemAndBlockRegister.vertical_sikkui_slab.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.shikkui");
            }else if (block == ItemAndBlockRegister.namako.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.namako");
            }else if (item == ItemAndBlockRegister.raw_urushi_ball.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.kiurushi");
            }else if (item == ItemAndBlockRegister.red_urushi_ball.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.syuurushi");
            }else if (item == ItemAndBlockRegister.black_urushi_ball.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.kurourushi");
            }else if (block == ItemAndBlockRegister.oak_shitamiita.get()||block == ItemAndBlockRegister.spruce_shitamiita.get()||block == ItemAndBlockRegister.birch_shitamiita.get()||block == ItemAndBlockRegister.jungle_shitamiita.get()||block == ItemAndBlockRegister.acacia_shitamiita.get()||block == ItemAndBlockRegister.dark_oak_shitamiita.get()||block == ItemAndBlockRegister.japanese_apricot_shitamiita.get()||block == ItemAndBlockRegister.sakura_shitamiita.get()||block == ItemAndBlockRegister.cypress_shitamiita.get()||block == ItemAndBlockRegister.japanese_cedar_shitamiita.get()||block == ItemAndBlockRegister.red_urushi_shitamiita.get()||block == ItemAndBlockRegister.black_urushi_shitamiita.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.shitamiita");
            }else if (block == ItemAndBlockRegister.oak_parapet.get()||block == ItemAndBlockRegister.spruce_parapet.get()||block == ItemAndBlockRegister.birch_parapet.get()||block == ItemAndBlockRegister.jungle_parapet.get()||block == ItemAndBlockRegister.acacia_parapet.get()||block == ItemAndBlockRegister.dark_oak_parapet.get()||block == ItemAndBlockRegister.japanese_apricot_parapet.get()||block == ItemAndBlockRegister.sakura_parapet.get()||block == ItemAndBlockRegister.cypress_parapet.get()||block == ItemAndBlockRegister.japanese_cedar_parapet.get()||block == ItemAndBlockRegister.red_urushi_parapet.get()||block == ItemAndBlockRegister.black_urushi_parapet.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.rankan");
            }else if (block == ItemAndBlockRegister.oak_fushiranma.get()||block == ItemAndBlockRegister.spruce_fushiranma.get()||block == ItemAndBlockRegister.birch_fushiranma.get()||block == ItemAndBlockRegister.jungle_fushiranma.get()||block == ItemAndBlockRegister.acacia_fushiranma.get()||block == ItemAndBlockRegister.dark_oak_fushiranma.get()||block == ItemAndBlockRegister.japanese_apricot_fushiranma.get()||block == ItemAndBlockRegister.sakura_fushiranma.get()||block == ItemAndBlockRegister.cypress_fushiranma.get()||block == ItemAndBlockRegister.japanese_cedar_fushiranma.get()||block == ItemAndBlockRegister.red_urushi_fushiranma.get()||block == ItemAndBlockRegister.black_urushi_fushiranma.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.takebushiramma");
            }else if (block == ItemAndBlockRegister.oak_board_window.get()||block == ItemAndBlockRegister.spruce_board_window.get()||block == ItemAndBlockRegister.birch_board_window.get()||block == ItemAndBlockRegister.jungle_board_window.get()||block == ItemAndBlockRegister.acacia_board_window.get()||block == ItemAndBlockRegister.dark_oak_board_window.get()||block == ItemAndBlockRegister.japanese_apricot_board_window.get()||block == ItemAndBlockRegister.sakura_board_window.get()||block == ItemAndBlockRegister.cypress_board_window.get()||block == ItemAndBlockRegister.japanese_cedar_board_window.get()||block == ItemAndBlockRegister.red_urushi_board_window.get()||block == ItemAndBlockRegister.black_urushi_board_window.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.musourenji");
            }else if (block == ItemAndBlockRegister.cypress_log.get()||block == ItemAndBlockRegister.cypress_sapling.get()||block == ItemAndBlockRegister.cypress_leaves.get()||block == ItemAndBlockRegister.cypress_planks.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.hinoki");
            }else if (block == ItemAndBlockRegister.lacquer_sapling.get()||block == ItemAndBlockRegister.lacquer_leaves.get()||block == ItemAndBlockRegister.lacquer_log.get()||block == ItemAndBlockRegister.chiseled_lacquer_log.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.urushi");
            }else if (block == ItemAndBlockRegister.oak_shoji_pane.get()||block == ItemAndBlockRegister.spruce_shoji_pane.get()||block == ItemAndBlockRegister.birch_shoji_pane.get()||block == ItemAndBlockRegister.jungle_shoji_pane.get()||block == ItemAndBlockRegister.acacia_shoji_pane.get()||block == ItemAndBlockRegister.dark_oak_shoji_pane.get()||block == ItemAndBlockRegister.japanese_apricot_shoji_pane.get()||block == ItemAndBlockRegister.sakura_shoji_pane.get()||block == ItemAndBlockRegister.cypress_shoji_pane.get()||block == ItemAndBlockRegister.japanese_cedar_shoji_pane.get()||block == ItemAndBlockRegister.red_urushi_shoji_pane.get()||block == ItemAndBlockRegister.black_urushi_shoji_pane.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.ramma_shoji");
            }else if (block == ItemAndBlockRegister.oak_shoji.get()||block == ItemAndBlockRegister.spruce_shoji.get()||block == ItemAndBlockRegister.birch_shoji.get()||block == ItemAndBlockRegister.jungle_shoji.get()||block == ItemAndBlockRegister.acacia_shoji.get()||block == ItemAndBlockRegister.dark_oak_shoji.get()||block == ItemAndBlockRegister.japanese_apricot_shoji.get()||block == ItemAndBlockRegister.sakura_shoji.get()||block == ItemAndBlockRegister.cypress_shoji.get()||block == ItemAndBlockRegister.japanese_cedar_shoji.get()||block == ItemAndBlockRegister.red_urushi_shoji.get()||block == ItemAndBlockRegister.black_urushi_shoji.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.shoji_do");
            }else if (block == ItemAndBlockRegister.petrified_log.get()||block == ItemAndBlockRegister.petrified_planks.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.keikaboku");
            }else if (block == ItemAndBlockRegister.petrified_log_with_wood_amber.get()||block == ItemAndBlockRegister.petrified_log_with_fire_amber.get()||block == ItemAndBlockRegister.petrified_log_with_earth_amber.get()||block == ItemAndBlockRegister.petrified_log_with_metal_amber.get()||block == ItemAndBlockRegister.petrified_log_with_water_amber.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.kohaku");
                UrushiUtils.setInfo(tooltipList, "furigana.keikaboku");
            }else if (block == ItemAndBlockRegister.shichirin.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.shichirin");
            }else if (block == ItemAndBlockRegister.sanbo_tier1.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.sanbo");
            }else if (block == ItemAndBlockRegister.wood_element_tank_tier1.get()||block == ItemAndBlockRegister.fire_element_tank_tier1.get()||block == ItemAndBlockRegister.earth_element_tank_tier1.get()||block == ItemAndBlockRegister.metal_element_tank_tier1.get()||block == ItemAndBlockRegister.water_element_tank_tier1.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.onusa");
            }else if (block == ItemAndBlockRegister.wood_element_hokora.get()||block == ItemAndBlockRegister.fire_element_hokora.get()||block == ItemAndBlockRegister.earth_element_hokora.get()||block == ItemAndBlockRegister.metal_element_hokora.get()||block == ItemAndBlockRegister.water_element_hokora.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.hokora");
            }else if (item == ItemAndBlockRegister.liana_jufu_stamp.get()||item == ItemAndBlockRegister.liana_jufu.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.tsuta");
            }else if (item == ItemAndBlockRegister.fluid_erasion_jufu_stamp.get()||item == ItemAndBlockRegister.fluid_erasion_jufu.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.kambatsu");
            }else if (item == ItemAndBlockRegister.spike_jufu_stamp.get()||item == ItemAndBlockRegister.spike_jufu.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.toge");
            }else if (item == ItemAndBlockRegister.close_wagasa.get()||item == ItemAndBlockRegister.open_wagasa.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.janomegasa");
            }else if (item == ItemAndBlockRegister.shide.get()||item == ItemAndBlockRegister.wood_element_paper.get()||item == ItemAndBlockRegister.fire_element_paper.get()||item == ItemAndBlockRegister.earth_element_paper.get()||item == ItemAndBlockRegister.metal_element_paper.get()||item == ItemAndBlockRegister.water_element_paper.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.shide");
            }else if (item == ItemAndBlockRegister.wood_element_magatama.get()||item == ItemAndBlockRegister.fire_element_magatama.get()||item == ItemAndBlockRegister.earth_element_magatama.get()||item == ItemAndBlockRegister.metal_element_magatama.get()||item == ItemAndBlockRegister.water_element_magatama.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.magatama");
            }else if (item == ItemAndBlockRegister.wood_amber.get()||item == ItemAndBlockRegister.fire_amber.get()||item == ItemAndBlockRegister.earth_amber.get()||item == ItemAndBlockRegister.metal_amber.get()||item == ItemAndBlockRegister.water_amber.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.kohaku");
            }else if (item == ItemAndBlockRegister.amber_igniter.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.hiuchigane");
            }else if (block == ItemAndBlockRegister.oil_extractor.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.assakuki");
            }else if (block == ItemAndBlockRegister.rice_crop.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.momi");
            }else if (item == ItemAndBlockRegister.kanten_powder.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.kanten");
            }else if (item == ItemAndBlockRegister.rice_malt.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.komekouji");
            }else if (item == ItemAndBlockRegister.yokan.get()||item == ItemAndBlockRegister.sakura_yokan.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.yokan");
            }else if (item == ItemAndBlockRegister.hiyayakko.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.hiyayakko");
            }else if (item == ItemAndBlockRegister.so.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.so");
            }else if (item == ItemAndBlockRegister.sweetfish.get()||item == ItemAndBlockRegister.sweetfish_with_salt.get()||item == ItemAndBlockRegister.cooked_sweetfish.get()||item == ItemAndBlockRegister.cooked_sweetfish_with_salt.get()) {
                UrushiUtils.setInfo(tooltipList, "furigana.ayu");
            }
        }
    }

}
