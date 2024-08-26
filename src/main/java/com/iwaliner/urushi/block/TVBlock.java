package com.iwaliner.urushi.block;


import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class TVBlock extends HorizonalRotateBlock{

    protected static final VoxelShape SHAPEA = Block.box(4D, 0.0D, 1D, 16D, 13.0D, 15.0D);
    protected static final VoxelShape SHAPEB = Block.box(1D, 0.0D, 4D, 15D, 13.0D, 16D);
    protected static final VoxelShape SHAPEC = Block.box(0D, 0.0D, 1D, 12D, 13.0D, 15.0D);
    protected static final VoxelShape SHAPED = Block.box(1D, 0.0D, 0D, 15D, 13.0D, 12D);

    private Block nextBlock;
    public TVBlock(Block b, Properties p_i48377_1_) {
        super(p_i48377_1_);
            nextBlock = b;

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));

    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(ItemAndBlockRegister.tv_idle.get());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if(state.getValue(FACING)== Direction.NORTH){
            return SHAPEB;
        }else if(state.getValue(FACING)== Direction.SOUTH){
            return SHAPED;
        }else if(state.getValue(FACING)== Direction.EAST){
            return SHAPEC;
        }else{
            return SHAPEA;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
       Block next=this.nextBlock==null? ItemAndBlockRegister.tv_idle.get():nextBlock;
       if(state.getBlock() instanceof TVBlock) {
           level.setBlockAndUpdate(pos, next.defaultBlockState().setValue(FACING, state.getValue(FACING)));
           level.playSound(player,pos, SoundEvents.LEVER_CLICK,SoundSource.BLOCKS,1F,1F);
           return InteractionResult.SUCCESS;
       }
       return InteractionResult.FAIL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING);
    }
    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        list.add((Component.translatable("info.urushi.tv" )).withStyle(ChatFormatting.GRAY));
    }

}
