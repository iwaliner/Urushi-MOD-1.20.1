package com.iwaliner.urushi.block;


import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public class StrippableLogBlock extends RotatedPillarBlock {
    private Block block;
   // private Item item;
    private int type;
    public StrippableLogBlock(Block b, int i, Properties p_i48339_1_) {
        super(p_i48339_1_);
        block=b;
        type=i;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        Item item=null;
        if(ItemAndBlockRegister.japanese_apricot_bark.isPresent()&&type==0){
            item= ItemAndBlockRegister.japanese_apricot_bark.get();
        }
        else if(ItemAndBlockRegister.sakura_bark.isPresent()&&type==1){
            item= ItemAndBlockRegister.sakura_bark.get();
        }else if(ItemAndBlockRegister.cypress_bark.isPresent()&&type==2){
            item= ItemAndBlockRegister.cypress_bark.get();
        }else if(ItemAndBlockRegister.cypress_bark.isPresent()&&type==3){
            item= ItemAndBlockRegister.japanese_cedar_bark.get();
        }

        if(player.getItemInHand(hand).getItem() instanceof AxeItem){
            EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
            world.setBlock(pos,block.defaultBlockState().setValue(AXIS,state.getValue(AXIS)),4);
            world.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            //if(!world.isClientSide){
            ItemEntity itemEntity=new ItemEntity(world,pos.relative(player.getDirection().getOpposite()).getX()+0.5D,pos.relative(player.getDirection().getOpposite()).getY()+0.5D,pos.relative(player.getDirection().getOpposite()).getZ()+0.5D,new ItemStack(item,8));
            world.addFreshEntity(itemEntity);
            // }
            return InteractionResult.SUCCESS;
        }else{
            return InteractionResult.FAIL;
        }
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
