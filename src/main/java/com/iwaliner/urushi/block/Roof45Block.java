package com.iwaliner.urushi.block;


import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class Roof45Block extends HorizonalRotateBlock {
   // private static final VoxelShape ROOT = Block.box(0D, 0.0D, 0D, 16D, 8D, 16D);
   private static final VoxelShape EXTEND = Block.box(0D, -8D, 0D, 16D, 16D, 16D);

    private static final VoxelShape BASE = Block.box(0D, -16D, 0D, 16D, -8D, 16D);
    private static final VoxelShape UPPER_A = Block.box(0D, -8D, 0D, 8D, 0D, 16D);
    private static final VoxelShape UPPER_B = Block.box(0D, -8D, 0D, 16D, 0D, 8D);
    private static final VoxelShape UPPER_C = Block.box(8D, -8D, 0D, 16D, 0D, 16D);
    private static final VoxelShape UPPER_D = Block.box(0D, -8D, 8D, 16D, 0D, 16D);
    private static final VoxelShape A_AXIS_AABB = Shapes.join(BASE, UPPER_A, BooleanOp.ONLY_FIRST);
    private static final VoxelShape B_AXIS_AABB = Shapes.join(BASE, UPPER_B, BooleanOp.ONLY_FIRST);
    private static final VoxelShape C_AXIS_AABB = Shapes.join(BASE, UPPER_C, BooleanOp.ONLY_FIRST);
    private static final VoxelShape D_AXIS_AABB = Shapes.join(BASE, UPPER_D, BooleanOp.ONLY_FIRST);

    public Roof45Block(Properties p_i48377_1_) {
        super(p_i48377_1_);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        if (state.getValue(FACING) == Direction.NORTH) {
            return D_AXIS_AABB;
        } else if (state.getValue(FACING) == Direction.SOUTH) {
            return B_AXIS_AABB;
        } else if (state.getValue(FACING) == Direction.WEST) {
            return C_AXIS_AABB;
        } else {
            return A_AXIS_AABB;
        }
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return EXTEND;
    }

    public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
       UrushiUtils.setInfo(list,"roof_45");
    }
}
