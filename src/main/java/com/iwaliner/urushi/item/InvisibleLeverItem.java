package com.iwaliner.urushi.item;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.block.HiddenInvisibleButtonBlock;
import com.iwaliner.urushi.block.HiddenInvisibleLeverBlock;
import com.iwaliner.urushi.block.InvisibleButtonBlock;
import com.iwaliner.urushi.block.InvisibleLeverBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class InvisibleLeverItem extends BlockItem {
    public InvisibleLeverItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int a, boolean b) {
        if(entity instanceof LivingEntity){
            int range=8;
            LivingEntity player= (LivingEntity) entity;
            BlockPos pos=new BlockPos(Mth.floor(entity.getX()),Mth.floor(entity.getY()), Mth.floor(entity.getZ()));
            if (player.getOffhandItem() == stack || player.getMainHandItem() == stack) {
                for(int i=-range; i<range+1;i++) {
                    for(int j=-range; j<range+1;j++) {
                        for(int k=-range; k<range+1;k++) {
                            if( world.getBlockState(pos.offset(i,j,k)).getBlock()== ItemAndBlockRegister.hidden_invisible_lever.get()){
                                BlockState hiddenState=world.getBlockState(pos.offset(i,j,k));
                                world.setBlockAndUpdate(pos.offset(i,j,k),ItemAndBlockRegister.invisible_lever.get().defaultBlockState().setValue(InvisibleLeverBlock.POWERED,hiddenState.getValue(HiddenInvisibleLeverBlock.POWERED)).setValue(InvisibleLeverBlock.FACE,hiddenState.getValue(HiddenInvisibleLeverBlock.FACE)).setValue(InvisibleLeverBlock.FACING,hiddenState.getValue(HiddenInvisibleLeverBlock.FACING)));
                            }
                        }
                    }
                }


            }
        }
    }
}
