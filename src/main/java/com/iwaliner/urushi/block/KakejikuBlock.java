package com.iwaliner.urushi.block;



import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class KakejikuBlock extends HorizonalRotateBlock{
    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
    protected static final VoxelShape SHAPEA = Block.box(15D, 2.0D, 2D, 16D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPEB = Block.box(2D, 2.0D, 15D, 14D, 16.0D, 16D);
    protected static final VoxelShape SHAPEC = Block.box(0D, 2.0D, 2D, 1D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPED = Block.box(2D, 2.0D, 0D, 14D, 16.0D, 1D);

    private Block nextBlock;
    public KakejikuBlock(Block b, Properties p_i48377_1_) {
        super(p_i48377_1_);
            nextBlock = b;

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BOTTOM, Boolean.valueOf(false)));

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
        Direction direction = context.getHorizontalDirection().getOpposite();
        BlockPos blockpos = context.getClickedPos();
        BlockPos blockpos1 = blockpos.below();
        if(context.getLevel().getBlockState(blockpos1).canBeReplaced(context) ){
            context.getLevel().setBlockAndUpdate(blockpos1,this.defaultBlockState().setValue(FACING,direction).setValue(BOTTOM,Boolean.valueOf(true)));
            return this.defaultBlockState().setValue(FACING,direction).setValue(BOTTOM,Boolean.valueOf(false));
        }else{
            return  null;
        }
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor world, BlockPos pos, BlockPos pos2) {
        if(state.getBlock() instanceof KakejikuBlock) {
            Direction facing=state.getValue(FACING);
            //  if(direction!=facing.UP&&direction!=facing.DOWN) {
            if (state.getValue(BOTTOM) == Boolean.valueOf(true)) {
                BlockState nextBlockState = world.getBlockState(pos.above());
                if (nextBlockState.getBlock() instanceof KakejikuBlock) {
                    return super.updateShape(state, direction, state2, world, pos, pos2);
                } else {
                    return Blocks.AIR.defaultBlockState();
                }
            } else {
                BlockState nextBlockState = world.getBlockState(pos.below());
                if (nextBlockState.getBlock() instanceof KakejikuBlock) {
                    return super.updateShape(state, direction, state2, world, pos, pos2);
                } else {
                    return Blocks.AIR.defaultBlockState();
                    //        }
                }
            }
        }
        return super.updateShape(state, direction, state2, world, pos, pos2);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(nextBlock==null){
            nextBlock= ItemAndBlockRegister.kakejiku_1.get();
        }
        Direction direction=state.getValue(FACING);
        if(nextBlock instanceof KakejikuBlock) {

            if (state.getValue(BOTTOM)) {
                world.setBlock(pos.above(), nextBlock.defaultBlockState().setValue(FACING, direction).setValue(BOTTOM, Boolean.valueOf(false)),2);
                world.setBlock(pos, nextBlock.defaultBlockState().setValue(FACING, direction).setValue(BOTTOM, Boolean.valueOf(true)),2);
                world.playSound((Player) null, pos, SoundEvents.ENDER_DRAGON_FLAP, SoundSource.BLOCKS, 1F, 1F);
                return InteractionResult.SUCCESS;
            } else {
                world.setBlock(pos.below(), nextBlock.defaultBlockState().setValue(FACING, direction).setValue(BOTTOM, Boolean.valueOf(true)),2);
                world.setBlock(pos, nextBlock.defaultBlockState().setValue(FACING, direction).setValue(BOTTOM, Boolean.valueOf(false)),2);
                world.playSound((Player) null, pos, SoundEvents.ENDER_DRAGON_FLAP, SoundSource.BLOCKS, 1F, 1F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING,BOTTOM);
    }
    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        list.add((Component.translatable("info.urushi.kakejiku" )).withStyle(ChatFormatting.GRAY));
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
