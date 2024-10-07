package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.block.DoubleSlabBlock;
import com.iwaliner.urushi.block.HorizonalRotateSlabBlock;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlabBlock.class)

public abstract class SlabBlockMixin {
    private boolean isSlab(Block block){
        return block instanceof SlabBlock||block instanceof HorizonalRotateSlabBlock;
    }
    @Inject(method = "getStateForPlacement",at = @At("HEAD"), cancellable = true)
    private void getStateForPlacementInject(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir){
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = context.getLevel().getBlockState(blockpos);
        SlabBlock slabBlock=(SlabBlock)(Object)this;
        if(!blockstate.is(slabBlock)&&isSlab(blockstate.getBlock())&& (UrushiUtils.isMinecraftObject(blockstate.getBlock().getDescriptionId())||UrushiUtils.isUrushiObject(blockstate.getBlock().getDescriptionId()))&&(UrushiUtils.isMinecraftObject(slabBlock.getDescriptionId())||UrushiUtils.isUrushiObject(slabBlock.getDescriptionId())) ){
            Direction direction = context.getClickedFace();
            if(direction == Direction.UP || !(context.getClickLocation().y - (double) blockpos.getY() <= 0.5D)) {
                cir.setReturnValue( ItemAndBlockRegister.double_slab.get().defaultBlockState().setValue(DoubleSlabBlock.FACING, context.getHorizontalDirection().getOpposite()).setValue(DoubleSlabBlock.UPPER, DoubleSlabBlock.getIDFromBlock(slabBlock)).setValue(DoubleSlabBlock.UNDER,DoubleSlabBlock.getIDFromBlock(blockstate.getBlock())));
            }else{
                cir.setReturnValue( ItemAndBlockRegister.double_slab.get().defaultBlockState().setValue(DoubleSlabBlock.FACING, context.getHorizontalDirection().getOpposite()).setValue(DoubleSlabBlock.UNDER, DoubleSlabBlock.getIDFromBlock(slabBlock)).setValue(DoubleSlabBlock.UPPER,DoubleSlabBlock.getIDFromBlock(blockstate.getBlock())));
            }
        }
    }
    @Inject(method = "canBeReplaced",at = @At("HEAD"), cancellable = true)
    private void canBeReplacedInject(BlockState state, BlockPlaceContext context, CallbackInfoReturnable<Boolean> cir){
        ItemStack itemstack = context.getItemInHand();
        SlabType slabtype = state.getValue(SlabBlock.TYPE);
        if (slabtype != SlabType.DOUBLE &&isSlab(Block.byItem(itemstack.getItem()))&&(UrushiUtils.isMinecraftObject(itemstack.getItem().getDescriptionId())||UrushiUtils.isUrushiObject(itemstack.getItem().getDescriptionId()))&&(UrushiUtils.isMinecraftObject(state.getBlock().getDescriptionId())||UrushiUtils.isUrushiObject(state.getBlock().getDescriptionId()))) {
            if (context.replacingClickedOnBlock()) {
                boolean flag = context.getClickLocation().y - (double)context.getClickedPos().getY() > 0.5D;
                Direction direction = context.getClickedFace();
                if (slabtype == SlabType.BOTTOM) {
                    cir.setReturnValue( direction == Direction.UP || flag && direction.getAxis().isHorizontal());
                } else {
                    cir.setReturnValue( direction == Direction.DOWN || !flag && direction.getAxis().isHorizontal());
                }
            } else {
                cir.setReturnValue(true);
            }
        }
    }
}
