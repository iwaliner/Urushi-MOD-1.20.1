package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class UrushiCropBlock extends CropBlock {

    public UrushiCropBlock( Properties p_52247_) {
        super(p_52247_);
    }
    protected ItemLike getBaseSeedId() {
        return this;
    }
    protected boolean mayPlaceOn(BlockState state, BlockGetter p_52303_, BlockPos p_52304_) {
        return state.getBlock() instanceof FarmBlock;
    }
    public void randomTick(BlockState p_221050_, ServerLevel p_221051_, BlockPos p_221052_, RandomSource p_221053_) {
        if (!p_221051_.isAreaLoaded(p_221052_, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (p_221051_.getRawBrightness(p_221052_, 0) >= 9) {
            int i = this.getAge(p_221050_);
            if (i < this.getMaxAge()) {
                float f = getGrowthSpeed(this, p_221051_, p_221052_)*1.5f;
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_221051_, p_221052_, p_221050_, p_221053_.nextInt((int)(25.0F / f) + 1) == 0)) {
                    p_221051_.setBlock(p_221052_, this.getStateForAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_221051_, p_221052_, p_221050_);
                }
            }
        }

    }
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(state.getBlock() instanceof CropBlock){
            if(world.getBlockState(pos).getValue(AGE)==Integer.valueOf(7)) {
                world.destroyBlock(pos, true);
                world.setBlockAndUpdate(pos,this.defaultBlockState());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        UrushiUtils.setInfo(list, "crop");
    }
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }
}
