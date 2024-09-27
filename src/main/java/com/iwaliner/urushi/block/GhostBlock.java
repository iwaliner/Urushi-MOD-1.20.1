package com.iwaliner.urushi.block;

import com.iwaliner.urushi.util.IGhostBlock;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GhostBlock extends Block implements IGhostBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public final boolean canChange;
    public GhostBlock(boolean b,Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, Boolean.valueOf(false)));
        this.canChange=b;
    }
    @Override
    public VoxelShape getVisualShape(BlockState p_60479_, BlockGetter p_60480_, BlockPos p_60481_, CollisionContext p_60482_) {
        return Shapes.empty();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getShadeBrightness(BlockState p_60472_, BlockGetter p_60473_, BlockPos p_60474_) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
        return true;
    }

    @Override
    public boolean skipRendering(BlockState p_60532_, BlockState p_60533_, Direction p_60534_) {
        return p_60533_.getBlock() instanceof IGhostBlock ? true : super.skipRendering(p_60532_, p_60533_, p_60534_);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_55659_) {
        return this.defaultBlockState().setValue(POWERED, Boolean.valueOf(canChange? p_55659_.getLevel().hasNeighborSignal(p_55659_.getClickedPos()) : false));
    }
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos2, boolean p_55671_) {
        if (!level.isClientSide&&canChange) {
            boolean flag = state.getValue(POWERED);

            if (flag != level.hasNeighborSignal(pos)) {
                // level.setBlockAndUpdate(pos, state.cycle(POWERED));
            }
            boolean flagX=false;
            for(int i=-1;i<2;i++){
                for(int j=-1;j<2;j++) {
                    for (int k = -1; k < 2; k++) {
                        if(level.getBlockState(pos.offset(i,j,k)).getBlock() instanceof GhostBlock&&level.hasNeighborSignal(pos.offset(i,j,k))){
                            flagX=true;
                            break;
                        }
                    }
                }
            }
               // level.setBlockAndUpdate(pos, state.cycle(POWERED));
                    for(int i=-1;i<2;i++){
                        for(int j=-1;j<2;j++){
                            for(int k=-1;k<2;k++){
                                BlockState stateX=level.getBlockState(pos.offset(i,j,k));
                                if(stateX.getBlock() instanceof GhostBlock){
                                    level.setBlock(pos.offset(i,j,k), stateX.setValue(POWERED,flagX),2);
                                }
                            }
                        }
            }
        }
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55673_) {
        p_55673_.add(POWERED);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if(!canChange){
            return Shapes.empty();
        }
        if(!state.getValue(POWERED)){
            return Shapes.block();
        }else{
            return Shapes.empty();
        }
    }
    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
       if(canChange) {
           UrushiUtils.setInfo(list, "ghost_block");
           UrushiUtils.setInfo(list, "ghost_block2");
       }
    }

    /**falseだとモブが足場として誤認*/
    @Override
    public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
        return false;
    }
}
