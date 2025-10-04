package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class TwoDirectionShapedBlock extends HorizonalRotateBlock{
    public final   VoxelShape SHAPEA;
    public final   VoxelShape SHAPEB;
    private final boolean canSurvive;

    public TwoDirectionShapedBlock(double d1,double d2,double d3,double d4,double d5,double d6,boolean canSurvive,Properties p_i48377_1_) {
        super(p_i48377_1_);
       SHAPEA= Block.box(d1, d2, d3, d4, d5, d6);
       SHAPEB= Block.box(d3, d2, d1, d6, d5, d4);
       this.canSurvive=canSurvive;
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if(state.getValue(FACING)== Direction.NORTH||state.getValue(FACING)==Direction.SOUTH){
            return SHAPEB;
        }else{
            return SHAPEA;
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable BlockGetter getter, List<Component> list, TooltipFlag flag) {
        if(stack.getItem().equals(Item.byBlock(ItemAndBlockRegister.udon.get()))){
            UrushiUtils.setInfo(list, "udon");
        }else  if(stack.getItem().equals(Item.byBlock(ItemAndBlockRegister.alkaline_noodles.get()))){
            UrushiUtils.setInfo(list, "alkaline_noodles");
        }else  if(stack.getItem().equals(Item.byBlock(ItemAndBlockRegister.silkworm.get()))){
            UrushiUtils.setInfo(list, "silkworm");
        }

    }
    public BlockState updateShape(BlockState p_152926_, Direction p_152927_, BlockState p_152928_, LevelAccessor p_152929_, BlockPos p_152930_, BlockPos p_152931_) {
        return !p_152926_.canSurvive(p_152929_, p_152930_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_152926_, p_152927_, p_152928_, p_152929_, p_152930_, p_152931_);
    }

    public boolean canSurvive(BlockState p_152922_, LevelReader p_152923_, BlockPos p_152924_) {
        return canSurvive || !p_152923_.isEmptyBlock(p_152924_.below());
    }
}
