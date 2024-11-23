package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HiddenKitsunebiBlock extends Block {

    public HiddenKitsunebiBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any());
    }

    public ItemStack getCloneItemStack(BlockGetter p_152966_, BlockPos p_152967_, BlockState p_152968_) {
        return new ItemStack(ItemAndBlockRegister.kitsunebiItem.get());
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shapes.empty();
    }
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.INVISIBLE;
    }

}
