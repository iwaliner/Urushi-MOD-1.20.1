package com.iwaliner.urushi.block;


import com.iwaliner.urushi.TagUrushi;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class KotatsuBlock extends Block {
  /*   private static final VoxelShape Shape1 = Block.box(1D, 0.0D, 1D, 4D, 16D, 4D);
    private static final VoxelShape Shape2 = Block.box(1D, 0.0D, 12D, 4D, 16D, 15D);
    private static final VoxelShape Shape3 = Block.box(12D, 0.0D, 1D, 15D, 16D, 4D);
    private static final VoxelShape Shape4 = Block.box(12D, 0.0D, 12D, 15D, 16D, 15D);
    */private static final VoxelShape Shape5 = Block.box(0D, 14.0D, 0D, 16D, 16D, 16D);
  //  private static final VoxelShape AABB = Shapes.or(Shape1, Shape2, Shape3, Shape4,Shape5);

    public KotatsuBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
     }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shape5;
    }





}
