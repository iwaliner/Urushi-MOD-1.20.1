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
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class GhostRotatedPillarBlock extends RotatedPillarBlock  implements IGhostBlock {
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public final boolean canChange;
    public GhostRotatedPillarBlock(boolean b,Properties p_55926_) {
        super(p_55926_);
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
        return this.defaultBlockState().setValue(POWERED, Boolean.valueOf(canChange? p_55659_.getLevel().hasNeighborSignal(p_55659_.getClickedPos()) : false)).setValue(AXIS, p_55659_.getClickedFace().getAxis());
    }
    public void neighborChanged(BlockState p_55666_, Level p_55667_, BlockPos p_55668_, Block p_55669_, BlockPos p_55670_, boolean p_55671_) {
        if (!p_55667_.isClientSide&&canChange) {
            boolean flag = p_55666_.getValue(POWERED);
            if (flag != p_55667_.hasNeighborSignal(p_55668_)) {
                //   if (flag) {
                //       p_55667_.scheduleTick(p_55668_, this, 4);
                //   } else {
                p_55667_.setBlock(p_55668_, p_55666_.cycle(POWERED), 2);
                // }
            }
        }
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55673_) {
        p_55673_.add(POWERED,AXIS);
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
