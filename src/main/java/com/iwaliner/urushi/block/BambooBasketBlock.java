package com.iwaliner.urushi.block;

import com.iwaliner.urushi.blockentity.BambooBasketBlockEntity;
import com.iwaliner.urushi.blockentity.PlateBlockEntity;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Objects;

public class BambooBasketBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;


    private static final VoxelShape SHAPE = Block.box(1D, 0D, 1D, 15D, 3D, 15D);

    public BambooBasketBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }
    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        UrushiUtils.setInfo(list, "bamboo_basket");
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return   new BambooBasketBlockEntity(pos,state);
    }

    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(world.getBlockEntity(pos)instanceof BambooBasketBlockEntity) {
            if(!player.isSuppressingBounce()&&world.getBlockEntity(pos) instanceof  BambooBasketBlockEntity tileEntity) {
                 ItemStack heldStack = player.getItemInHand(hand);
                ItemStack insertStack = heldStack.copy();
                insertStack.setCount(1);
                boolean isFull=true;
                for (int i = 0; i < 5; i++) {
                    if (tileEntity.canPlaceItem(i)) {
                        isFull=false;
                        break;
                    }
                }
                if(!heldStack.isEmpty()&&!isFull){
                for (int i = 0; i < 5; i++) {
                    if (tileEntity.canPlaceItem(i)) {
                        tileEntity.setItem(i, insertStack);
                        tileEntity.markUpdated();
                        heldStack.shrink(1);
                        world.playSound(player, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1F, 1F);
                        return InteractionResult.SUCCESS;
                    }
                }
                } else {
                    for (int i = 0; i < 5; i++) {
                        ItemStack pickedStack = Objects.requireNonNull(tileEntity).pickItem(i).copy();
                            if (!player.getInventory().add(pickedStack)) {
                            tileEntity.markUpdated();
                            player.drop(pickedStack, false);
                            world.playSound(player, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1F, 1F);

                        }
                    }
                    return InteractionResult.SUCCESS;
                    }
                }


        }
        return InteractionResult.SUCCESS;
    }



    @Override
    public BlockState rotate(BlockState state, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }




    public void onRemove(BlockState p_48713_, Level p_48714_, BlockPos p_48715_, BlockState p_48716_, boolean p_48717_) {
        if (!p_48713_.is(p_48716_.getBlock())) {
            BlockEntity blockentity = p_48714_.getBlockEntity(p_48715_);
            if (blockentity instanceof BambooBasketBlockEntity) {
                if (p_48714_ instanceof ServerLevel) {
                    Containers.dropContents(p_48714_, p_48715_, (BambooBasketBlockEntity)blockentity);
                }

                p_48714_.updateNeighbourForOutputSignal(p_48715_, this);
            }

            super.onRemove(p_48713_, p_48714_, p_48715_, p_48716_, p_48717_);
        }
    }
}
