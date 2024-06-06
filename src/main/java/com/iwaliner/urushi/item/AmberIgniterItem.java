package com.iwaliner.urushi.item;

import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.block.FallenLeavesBlock;
import com.iwaliner.urushi.block.ShichirinBlock;
import com.iwaliner.urushi.blockentity.ShichirinBlockEntity;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.ElementItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AmberIgniterItem extends Item implements ElementItem {
    public AmberIgniterItem(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemStack=context.getItemInHand();
        BlockPos pos=context.getClickedPos();
        Level level=context.getLevel();
        Player player=context.getPlayer();
        InteractionHand hand=context.getHand();
        BlockState state=level.getBlockState(pos);
        BlockEntity blockEntity=level.getBlockEntity(pos);
        ItemStack magatama=ElementUtils.getMagatamaInInventory(player, ElementType.FireElement);
        int consumeAmount=10;
if (!CampfireBlock.canLight(state) && !CandleBlock.canLight(state) && !CandleCakeBlock.canLight(state)) {
                BlockPos blockpos1 = pos.relative(context.getClickedFace());
                if (BaseFireBlock.canBePlacedAt(level, blockpos1, context.getHorizontalDirection())) {
                    level.playSound(player, blockpos1, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                    BlockState blockstate1 = BaseFireBlock.getState(level, blockpos1);
                    level.setBlock(blockpos1, blockstate1, 11);
                    level.gameEvent(player, GameEvent.BLOCK_PLACE, pos);
                    ItemStack itemstack = context.getItemInHand();
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos1, itemstack);
                        itemstack.hurtAndBreak(1, player, (p_41300_) -> {
                            p_41300_.broadcastBreakEvent(context.getHand());
                        });
                    }

                    return InteractionResult.sidedSuccess(level.isClientSide());
                } else {
                    return InteractionResult.FAIL;
                }
            } else {
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                level.setBlock(pos, state.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
                level.gameEvent(player, GameEvent.BLOCK_PLACE, pos);
                if (player != null) {
                    context.getItemInHand().hurtAndBreak(1, player, (p_41303_) -> {
                        p_41303_.broadcastBreakEvent(context.getHand());
                    });
                }

                return InteractionResult.sidedSuccess(level.isClientSide());
            }



    }
    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        UrushiUtils.setInfo(list,"amber_igniter1");
    }

    @Override
    public ElementType getElementType() {
        return ElementType.FireElement;
    }
}
