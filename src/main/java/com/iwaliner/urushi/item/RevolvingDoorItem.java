package com.iwaliner.urushi.item;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.block.RevolvingDoorBlock;
import com.iwaliner.urushi.block.RopeBlock;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RevolvingDoorItem extends Item {
    public RevolvingDoorItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockpos = context.getClickedPos().relative(context.getClickedFace());
        if (world.getBlockState(blockpos).canBeReplaced()) {

            Direction facing = context.getPlayer().getDirection();
            Direction neighborFacing = facing.getClockWise();
            BlockPos posUnderLeft = blockpos;
            BlockPos posUnderRight = posUnderLeft.relative(neighborFacing);
            BlockPos posUpperLeft = posUnderLeft.above();
            BlockPos posUpperRight = posUnderRight.above();
            if (posUnderLeft.getY() < world.getMaxBuildHeight() - 1 && context.getLevel().getBlockState(posUnderLeft).canBeReplaced() && context.getLevel().getBlockState(posUpperLeft).canBeReplaced() && context.getLevel().getBlockState(posUnderRight).canBeReplaced()&& context.getLevel().getBlockState(posUpperRight).canBeReplaced()) {
                world.playSound((Player) null, context.getPlayer().getX(), context.getPlayer().getY(), context.getPlayer().getZ(), SoundEvents.LADDER_PLACE, SoundSource.PLAYERS, 1F, 1F);
                BlockState state=ItemAndBlockRegister.plaster_revolving_door.get().defaultBlockState().setValue(RevolvingDoorBlock.FACING,facing).setValue(RevolvingDoorBlock.PART, RevolvingDoorBlock.RevolvingDoorPart.UnderLeft.getID()).setValue(RevolvingDoorBlock.POWERED, false);
                world.setBlockAndUpdate(blockpos, state);
                world.setBlock(posUnderRight, state.setValue(RevolvingDoorBlock.PART, RevolvingDoorBlock.RevolvingDoorPart.UnderRight.getID()), 3);
                world.setBlock(posUpperLeft, state.setValue(RevolvingDoorBlock.PART, RevolvingDoorBlock.RevolvingDoorPart.UpperLeft.getID()), 3);
                world.setBlock(posUpperRight, state.setValue(RevolvingDoorBlock.PART, RevolvingDoorBlock.RevolvingDoorPart.UpperRight.getID()), 3);
                context.getItemInHand().shrink(1);
                return InteractionResult.SUCCESS;
            }

        }
            return  InteractionResult.FAIL;

    }
    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        UrushiUtils.setInfo(list,"revolving_door");
    }

}
