package com.iwaliner.urushi.item;

import com.iwaliner.urushi.MenuRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.block.SlideDoorBlock;
import com.iwaliner.urushi.blockentity.menu.TranslatableBookMenu;
import com.iwaliner.urushi.mixin.BlockDisplayMixin;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class BakedMochochoItem extends Item {
    public BakedMochochoItem(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
       try {
           long gametime= level.getGameTime()%100;
           if (gametime<20) {
               UrushiUtils.setInfoWithColor(list, "obanyaki", ChatFormatting.WHITE);
           } else if(gametime<40){
               UrushiUtils.setInfoWithColor(list, "kaitenyaki", ChatFormatting.WHITE);
           }else if(gametime<60){
               UrushiUtils.setInfoWithColor(list, "imagawayaki", ChatFormatting.WHITE);
           }else if(gametime<80){
               UrushiUtils.setInfoWithColor(list, "oyaki", ChatFormatting.WHITE);
           }else {
               UrushiUtils.setInfoWithColor(list, "gozasourou", ChatFormatting.WHITE);
           }
       }catch (Exception e){

       }


    }

    /*@Override
    public InteractionResult useOn(UseOnContext context) {
        Level level=context.getLevel();
        BlockPos pos=context.getClickedPos();
       *//* BlockState blockstate = Blocks.MOVING_PISTON.defaultBlockState().setValue(MovingPistonBlock.FACING, Direction.UP).setValue(MovingPistonBlock.TYPE,  PistonType.STICKY);
        level.setBlock(pos, blockstate, 20);
        level.setBlockEntity(MovingPistonBlock.newMovingBlockEntity(pos, blockstate,Blocks.PISTON.defaultBlockState().setValue(PistonBaseBlock.FACING, Direction.UP), Direction.UP, false, true));
        level.blockUpdated(pos, blockstate.getBlock());*//*
       // level.setBlock(pos.below(), Blocks.STONE.defaultBlockState(), 68);
        //level.setBlockAndUpdate(pos.below(), level.getBlockState(pos.below()));
        BlockState state=level.getBlockState(pos);
        level.setBlockAndUpdate(pos,Blocks.AIR.defaultBlockState());
        BlockState blockstate8 = Blocks.MOVING_PISTON.defaultBlockState().setValue(MovingPistonBlock.FACING, Direction.DOWN).setValue(MovingPistonBlock.TYPE,PistonType.DEFAULT);
        level.setBlock(pos.above(), blockstate8, 68);
        level.setBlockEntity(MovingPistonBlock.newMovingBlockEntity(pos.above(), blockstate8,state, Direction.DOWN, false, false));


        return InteractionResult.SUCCESS;
    }*/

    /*@Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i0, boolean b0) {
        super.inventoryTick(stack, level, entity, i0, b0);
        if(entity.getPassengers().isEmpty()) {
            BlockState state = Blocks.POLISHED_ANDESITE.defaultBlockState();
            Display.BlockDisplay blockDisplay = new Display.BlockDisplay(EntityType.BLOCK_DISPLAY, level);
            SynchedEntityData entityData = blockDisplay.getEntityData();
            entityData.set(BlockDisplayMixin.getData(), state);
            blockDisplay.moveTo(entity.position());
            if(!level.isClientSide()) {
                level.addFreshEntity(blockDisplay);
            }
            blockDisplay.startRiding(entity);
        }
    }*/

}
