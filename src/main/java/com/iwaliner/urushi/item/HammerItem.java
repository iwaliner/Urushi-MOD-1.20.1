package com.iwaliner.urushi.item;



import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.block.AbstractFramedBlock;
import com.iwaliner.urushi.block.FramedPaneBlock;
import com.iwaliner.urushi.block.HotIronIngotBlock;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
 
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HammerItem extends Item {
    public HammerItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player playerentity = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();
        Level level=context.getLevel();
        BlockPos pos=context.getClickedPos();
        BlockState clickedState=level.getBlockState(pos);
        assert playerentity != null;
        ItemStack heldStack=playerentity.getItemInHand(context.getHand());

        playerentity.getCooldowns().addCooldown(this, 15);
        playerentity.startUsingItem(context.getHand());
        if(clickedState.getBlock() ==ItemAndBlockRegister.hot_ironsand.get()){

            return temper(level,pos,ItemAndBlockRegister.hot_iron_plate.get().defaultBlockState(),heldStack,playerentity,context.getHand(),level.getRandom(),10);

        }else if(clickedState.getBlock() ==ItemAndBlockRegister.hot_iron_ingot_1.get()){

            Direction direction=clickedState.getValue(HotIronIngotBlock.FACING);
            return temper(level,pos,ItemAndBlockRegister.hot_iron_ingot_2.get().defaultBlockState().setValue(HotIronIngotBlock.FACING,direction),heldStack,playerentity,context.getHand(),level.getRandom(),10);

        }else if(clickedState.getBlock() ==ItemAndBlockRegister.hot_iron_ingot_2.get()){

            Direction direction=clickedState.getValue(HotIronIngotBlock.FACING);
            return temper(level,pos,ItemAndBlockRegister.hot_iron_ingot_3.get().defaultBlockState().setValue(HotIronIngotBlock.FACING,direction),heldStack,playerentity,context.getHand(),level.getRandom(),10);

        }else if(clickedState.getBlock() ==ItemAndBlockRegister.hot_iron_ingot_3.get()){

            Direction direction=clickedState.getValue(HotIronIngotBlock.FACING);
            return temper(level,pos,ItemAndBlockRegister.hot_iron_ingot_4.get().defaultBlockState().setValue(HotIronIngotBlock.FACING,direction),heldStack,playerentity,context.getHand(),level.getRandom(),10);

        }else if(clickedState.getBlock() ==ItemAndBlockRegister.hot_iron_ingot_4.get()){

            Direction direction=clickedState.getValue(HotIronIngotBlock.FACING);
            return temper(level,pos,ItemAndBlockRegister.hot_iron_ingot_5.get().defaultBlockState().setValue(HotIronIngotBlock.FACING,direction),heldStack,playerentity,context.getHand(),level.getRandom(),10);

        }else if(clickedState.getBlock() ==ItemAndBlockRegister.hot_iron_blade_1.get()){

            return temper(level,pos,ItemAndBlockRegister.hot_iron_blade_2.get().defaultBlockState(),heldStack,playerentity,context.getHand(),level.getRandom(),10);

        }else if(context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof FramedPaneBlock ||context.getLevel().getBlockState(context.getClickedPos()).getBlock() instanceof AbstractFramedBlock){
            BlockState state=context.getLevel().getBlockState(context.getClickedPos());
            context.getLevel().setBlockAndUpdate(context.getClickedPos(), state.setValue(AbstractFramedBlock.VARIANT, !state.getValue(AbstractFramedBlock.VARIANT)));
            itemstack.hurtAndBreak(1, playerentity, (x) -> {
                x.broadcastBreakEvent(context.getHand());
            });
            context.getLevel().playSound((Player) null, context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1F, 1F);
            playerentity.getCooldowns().removeCooldown(this);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
    private InteractionResult temper(Level level, BlockPos pos, BlockState resultState, ItemStack hammer, Player player, InteractionHand hand, RandomSource random, int probability){
        if(!level.isClientSide()) {
            int i = random.nextInt(probability);
            if (i == 0) {
                level.setBlockAndUpdate(pos, resultState);
                //  context.getLevel().addParticle(ParticleTypes.FLAME,  d0,d1, d2, 0.0D, 0D, 0.0D);
                level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 0.3F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                hammer.hurtAndBreak(1, player, (x) -> {
                    x.broadcastBreakEvent(hand);
                });
                return InteractionResult.SUCCESS;
            } else {
                //   context.getLevel().addParticle(ParticleTypes.FLAME,  d0,d1, d2, 0.0D, 0D, 0.0D);
                level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 0.3F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                hammer.hurtAndBreak(1, player, (x) -> {
                    x.broadcastBreakEvent(hand);
                });
                return InteractionResult.SUCCESS;
            }
        }else{
            return InteractionResult.SUCCESS;
        }
    }
    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        UrushiUtils.setInfo(list,"hammer");
    }

}
