package com.iwaliner.urushi.world.dimension;

import com.iwaliner.urushi.DimensionRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.TagUrushi;
import com.iwaliner.urushi.block.KakuriyoPortalBlock;
import com.iwaliner.urushi.block.KasugaLanternBlock;
import com.iwaliner.urushi.block.ParapetBlock;
import com.iwaliner.urushi.block.SimpleShapedBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
 
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.SlabType;

import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class KakuriyoTeleporter implements ITeleporter {

    public KakuriyoTeleporter() {

    }

    @Override
    public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
        return false;
    }

    @Nullable
    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerLevel level, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        BlockPos pos=entity.blockPosition();
        BlockPos center=pos.offset(0,0,1);


    //    if(level==level.getServer().getLevel( DimensionRegister.KakuriyoKey )){
            if(level.dimension()==DimensionRegister.KakuriyoKey){
                entity.teleportTo(center.getX()+0.5D,center.getY()+0.5D,center.getZ()-2.5D);
            createPortalInKakuriyo(level,center);
            if(!level.getFluidState(center.offset(0,1,-3)).isEmpty()){
                if(entity instanceof LivingEntity){
                    LivingEntity livingEntity= (LivingEntity) entity;
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20 * 20, 0), entity);
                }
            }
ModCoreUrushi.logger.info("A1");
        }else{
                entity.teleportTo(center.getX()+0.5D,center.getY()+0.5D,center.getZ()-4.5D);
            int toSurfaceEach=0;
            boolean b=false;
            BlockPos pos2=new BlockPos(entity.blockPosition().getX(),318,entity.blockPosition().getZ());
            for(int s=0;s<400;s++) {
                if (level.getBlockState(pos2.offset(0,-s,0)).entityCanStandOn(level,pos2.offset(0,-s,0),entity) ) {
                    toSurfaceEach = s;
                    break;
                }else if (!level.getFluidState(pos2.offset(0,-s,0)).isEmpty() ) {
                    toSurfaceEach = s;
                    b=true;
                    break;
                }
            }
            if(b){
                level.setBlockAndUpdate(pos2.offset(0,-toSurfaceEach,0),ItemAndBlockRegister.rough_stone.get().defaultBlockState());
            }
            entity.teleportTo(entity.getX(),pos2.getY()-(double)toSurfaceEach+2.5D,entity.getZ());
ModCoreUrushi.logger.info("A2");
        }

        return new PortalInfo(entity.position(), Vec3.ZERO, -180f, entity.getXRot());
    }

    public static void createPortalInKakuriyo(Level level,BlockPos pos) {

        for(int i=-4;i<5;i++){
            for(int j=-4;j<4;j++) {
                //一番上の足場
                for (int k = 0; k < 7; k++) {
                    destroyBlock(level,pos.offset(i, k, j));
                }
                int toSurfaceEach=0;
                for(int s=0;s<370;s++) {
                    if (level.getBlockState(pos.offset(i,-s,j)).is(BlockTags.DIRT) || level.getBlockState(pos.offset(i,-s,j)).is(TagUrushi.YOMI_STONE)) {
                        toSurfaceEach = s;
                        break;
                    }
                }
                for (int k = 1; k < toSurfaceEach; k++) {
                    level.setBlockAndUpdate(pos.offset(i, -k, j), ItemAndBlockRegister.rough_stone.get().defaultBlockState());
                }
                if(toSurfaceEach<2){
                    level.setBlockAndUpdate(pos.offset(i, -1, j), ItemAndBlockRegister.rough_stone.get().defaultBlockState());
                }
            }
        }

        //周囲の欄干
        placeParapet(level,pos.offset(3,0,-4),Direction.NORTH);
        placeParapet(level,pos.offset(-3,0,-4),Direction.NORTH);
        level.setBlockAndUpdate(pos.offset(2,0,-4), ItemAndBlockRegister.smooth_red_urushi_planks.get().defaultBlockState());
        level.setBlockAndUpdate(pos.offset(-2,0,-4), ItemAndBlockRegister.smooth_red_urushi_planks.get().defaultBlockState());
        level.setBlockAndUpdate(pos.offset(4,0,-4), ItemAndBlockRegister.smooth_red_urushi_planks.get().defaultBlockState());
        level.setBlockAndUpdate(pos.offset(-4,0,-4), ItemAndBlockRegister.smooth_red_urushi_planks.get().defaultBlockState());
        level.setBlockAndUpdate(pos.offset(4,0,3), ItemAndBlockRegister.smooth_red_urushi_planks.get().defaultBlockState());
        level.setBlockAndUpdate(pos.offset(-4,0,3), ItemAndBlockRegister.smooth_red_urushi_planks.get().defaultBlockState());
        placeGiboshi(level,pos.offset(2,1,-4));
        placeGiboshi(level,pos.offset(-2,1,-4));
        placeGiboshi(level,pos.offset(4,1,-4));
        placeGiboshi(level,pos.offset(-4,1,-4));
        placeGiboshi(level,pos.offset(4,1,3));
        placeGiboshi(level,pos.offset(-4,1,3));
        for(int i=-3;i<4;i++){
            placeParapet(level,pos.offset(i,0,3),Direction.NORTH);
        }
        for(int i=-3;i<3;i++){
            placeParapet(level,pos.offset(4,0,i),Direction.WEST);
            placeParapet(level,pos.offset(-4,0,i),Direction.WEST);
        }

        //鳥居の基礎
        level.setBlockAndUpdate(pos.offset(2,0,1), ItemAndBlockRegister.black_kakuriyo_portal_frame.get().defaultBlockState());
        level.setBlockAndUpdate(pos.offset(-2,0,1), ItemAndBlockRegister.black_kakuriyo_portal_frame.get().defaultBlockState());

        //鳥居の左右の柱
        for(int i=1;i<6;i++){
            level.setBlockAndUpdate(pos.offset(2,i,1), ItemAndBlockRegister.red_kakuriyo_portal_frame.get().defaultBlockState());
            level.setBlockAndUpdate(pos.offset(-2,i,1), ItemAndBlockRegister.red_kakuriyo_portal_frame.get().defaultBlockState());
        }

        //鳥居の横の柱
        for(int i=-1;i<2;i++){
            level.setBlockAndUpdate(pos.offset(i,4,1), ItemAndBlockRegister.red_kakuriyo_portal_frame.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
         }
        //鳥居の横の柱
        for(int i=-3;i<4;i++){
            level.setBlockAndUpdate(pos.offset(i,6,1), ItemAndBlockRegister.red_kakuriyo_portal_frame.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
        }

        //鳥居の他パーツ
        placePortal(level,pos.offset(1,5,1));
        placePortal(level,pos.offset(-1,5,1));
        level.setBlockAndUpdate(pos.offset(0,5,1), ItemAndBlockRegister.kakuriyo_portal_core.get().defaultBlockState());
        level.setBlockAndUpdate(pos.offset(3,4,1), ItemAndBlockRegister.red_kakuriyo_portal_frame.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
        level.setBlockAndUpdate(pos.offset(4,6,1), ItemAndBlockRegister.red_kakuriyo_portal_frame.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
        level.setBlockAndUpdate(pos.offset(-3,4,1), ItemAndBlockRegister.red_kakuriyo_portal_frame.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
        level.setBlockAndUpdate(pos.offset(-4,6,1), ItemAndBlockRegister.red_kakuriyo_portal_frame.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));

        for(int i=-1;i<2;i++){
            for(int j=0;j<4;j++){
                placePortal(level,pos.offset(i,j,1));
            }
        }
        //階段の繰り返し
        if(level.getBlockState(pos.offset(0, -1, -5)).getBlock()!=ItemAndBlockRegister.rough_stone.get()) {
            int toSurface = 0;
            for (int s = 0; s < 370; s++) {
                if (level.getBlockState(pos.offset(0, -s, -s * 2)).is(BlockTags.DIRT) || level.getBlockState(pos.offset(0, -s, -s * 2)).is(TagUrushi.YOMI_STONE)) {
                    toSurface = s;
                    break;
                }
            }
            for (int n = 0; n < toSurface; n++) {
                int toSurfaceEach = 0;
                for (int s = 0; s < 370; s++) {
                    if (level.getBlockState(pos.offset(0, -s, -5 - n * 2)).is(BlockTags.DIRT) || level.getBlockState(pos.offset(0, -s, -5 - n * 2)).is(TagUrushi.YOMI_STONE)) {
                        toSurfaceEach = s;
                        break;
                    }
                }
                for (int k = 1 + n; k <= toSurfaceEach; k++) {
                    for (int i = -2; i < 3; i++) {
                        level.setBlockAndUpdate(pos.offset(i, -k, -5 - n * 2), ItemAndBlockRegister.rough_stone.get().defaultBlockState());
                        level.setBlockAndUpdate(pos.offset(i, -k, -6 - n * 2), ItemAndBlockRegister.rough_stone.get().defaultBlockState());
                    }
                }
                for (int i = -1; i < 2; i++) {
                    destroyBlock(level, pos.offset(i, -1 - n + 1, -5 - n * 2));
                    destroyBlock(level, pos.offset(i, -1 - n + 2, -5 - n * 2));
                    destroyBlock(level, pos.offset(i, -1 - n + 3, -5 - n * 2));
                    destroyBlock(level, pos.offset(i, -1 - n + 1, -6 - n * 2));
                    destroyBlock(level, pos.offset(i, -1 - n + 2, -6 - n * 2));
                    destroyBlock(level, pos.offset(i, -1 - n + 3, -6 - n * 2));


                    //階段本体
                    level.setBlockAndUpdate(pos.offset(i, -1 - n, -5 - n * 2), ItemAndBlockRegister.rough_stone.get().defaultBlockState());
                    placeRoughStoneSlab(level, pos.offset(i, -1 - n, -6 - n * 2));
                    if (n + 1 == toSurface) {
                        level.setBlockAndUpdate(pos.offset(i, -1 - n, -6 - n * 2), ItemAndBlockRegister.rough_stone.get().defaultBlockState());
                    }
                }
                //階段脇
                placeRoughStoneSlab(level, pos.offset(-2, -1 - n + 1, -5 - n * 2));
                placeRoughStoneSlab(level, pos.offset(2, -1 - n + 1, -5 - n * 2));
                level.setBlockAndUpdate(pos.offset(-2, -1 - n, -6 - n * 2), ItemAndBlockRegister.rough_stone.get().defaultBlockState());
                level.setBlockAndUpdate(pos.offset(2, -1 - n, -6 - n * 2), ItemAndBlockRegister.rough_stone.get().defaultBlockState());
                level.setBlockAndUpdate(pos.offset(-2, -1 - n + 1, -6 - n * 2), ItemAndBlockRegister.red_urushi_fence.get().defaultBlockState());
                level.setBlockAndUpdate(pos.offset(2, -1 - n + 1, -6 - n * 2), ItemAndBlockRegister.red_urushi_fence.get().defaultBlockState());
                level.setBlockAndUpdate(pos.offset(-2, -1 - n + 2, -6 - n * 2), ItemAndBlockRegister.red_urushi_fence.get().defaultBlockState());
                level.setBlockAndUpdate(pos.offset(2, -1 - n + 2, -6 - n * 2), ItemAndBlockRegister.red_urushi_fence.get().defaultBlockState());
                placeKasugaLantern(level, pos.offset(-2, -1 - n + 3, -6 - n * 2));
                placeKasugaLantern(level, pos.offset(2, -1 - n + 3, -6 - n * 2));

                destroyBlock(level, pos.offset(-2, -1 - n + 2, -5 - n * 2));
                destroyBlock(level, pos.offset(2, -1 - n + 2, -5 - n * 2));
                destroyBlock(level, pos.offset(-2, -1 - n + 3, -5 - n * 2));
                destroyBlock(level, pos.offset(2, -1 - n + 3, -5 - n * 2));

            }
        }

    }

    public static boolean shouldDestroy(Level level,BlockPos pos) {
        List<Block> blockList=new ArrayList<>();
        blockList.add(ItemAndBlockRegister.smooth_red_urushi_planks.get());
        blockList.add(ItemAndBlockRegister.smooth_red_urushi_slab.get());
        blockList.add(ItemAndBlockRegister.smooth_red_urushi_stairs.get());
        blockList.add(ItemAndBlockRegister.black_urushi_planks.get());
        blockList.add(ItemAndBlockRegister.red_urushi_parapet.get());
        blockList.add(ItemAndBlockRegister.gold_giboshi.get());
        blockList.add(ItemAndBlockRegister.red_urushi_fence.get());
        blockList.add(ItemAndBlockRegister.kasuga_lantern.get());
        blockList.add(ItemAndBlockRegister.rough_stone.get());
        blockList.add(ItemAndBlockRegister.rough_stone_slab.get());
        blockList.add(ItemAndBlockRegister.kakuriyo_portal.get());
        blockList.add(Blocks.YELLOW_STAINED_GLASS_PANE);
        return !blockList.contains(level.getBlockState(pos).getBlock());
    }
    public static void destroyBlock(Level level,BlockPos pos){
        if(level.getFluidState(pos).is(Fluids.WATER)) {
            level.setBlockAndUpdate(pos,Blocks.WATER.defaultBlockState());
        }else{
            if (shouldDestroy(level, pos)) {
                level.destroyBlock(pos, true);
            }
        }
    }
    public static void placeParapet(Level level,BlockPos pos,Direction direction){
        if(level.getFluidState(pos).is(Fluids.WATER)){
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.red_urushi_parapet.get().defaultBlockState().setValue(ParapetBlock.FACING,direction).setValue(ParapetBlock.WATERLOGGED,Boolean.valueOf(true)));
        }else{
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.red_urushi_parapet.get().defaultBlockState().setValue(ParapetBlock.FACING,direction));
        }
    }
    public static void placeGiboshi(Level level,BlockPos pos){
        if(level.getFluidState(pos).is(Fluids.WATER)){
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.gold_giboshi.get().defaultBlockState().setValue(SimpleShapedBlock.WATERLOGGED,Boolean.valueOf(true)));
        }else{
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.gold_giboshi.get().defaultBlockState());
        }
    }
    public static void placePortal(Level level,BlockPos pos){
        if(level.getFluidState(pos).is(Fluids.WATER)||level.getFluidState(pos.north()).is(Fluids.WATER)||level.getFluidState(pos.south()).is(Fluids.WATER)){
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.kakuriyo_portal.get().defaultBlockState().setValue(KakuriyoPortalBlock.WATERLOGGED,Boolean.valueOf(true)));
        }else{
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.kakuriyo_portal.get().defaultBlockState());
        }
    }
    public static void placeKasugaLantern(Level level,BlockPos pos){
        if(level.getFluidState(pos).is(Fluids.WATER)){
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.kasuga_lantern.get().defaultBlockState().setValue(KasugaLanternBlock.WATERLOGGED,Boolean.valueOf(true)));
        }else{
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.kasuga_lantern.get().defaultBlockState());
        }
    }
    public static void placeRoughStoneSlab(Level level,BlockPos pos){
        if(level.getFluidState(pos).is(Fluids.WATER)||level.getFluidState(pos.above()).is(Fluids.WATER)){
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.rough_stone_slab.get().defaultBlockState().setValue(SlabBlock.WATERLOGGED,Boolean.valueOf(true)));
        }else{
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.rough_stone_slab.get().defaultBlockState());
        }
    }
}
