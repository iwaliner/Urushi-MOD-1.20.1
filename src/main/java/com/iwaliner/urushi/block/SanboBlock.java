package com.iwaliner.urushi.block;


import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.blockentity.AbstractFryerBlockEntity;
import com.iwaliner.urushi.blockentity.RiceCauldronBlockEntity;
import com.iwaliner.urushi.blockentity.SanboBlockEntity;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.Tiered;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import net.minecraft.util.RandomSource;

public class SanboBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;


    private static final VoxelShape MIDDLE = Block.box(1.5D, 9.0D, 1.5D, 14.5D, 10D, 14.5D);
    private static final VoxelShape UNDER = Block.box(4D, 0.0D, 4D, 12D, 9D, 12D);
    private static final VoxelShape UPPER1 = Block.box(2D, 10.0D, 2D, 3D, 12D, 14D);
    private static final VoxelShape UPPER2 = Block.box(13D, 10.0D, 2D, 14D, 12D, 14D);
    private static final VoxelShape UPPER3 = Block.box(2D, 10.0D, 2D, 14D, 12D, 3D);
    private static final VoxelShape UPPER4 = Block.box(2D, 10.0D, 13D, 14D, 12D, 14D);
    private static final VoxelShape SHAPE = Shapes.or(UNDER, MIDDLE, UPPER1, UPPER2,UPPER3,UPPER4);

    public SanboBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return   new SanboBlockEntity(pos,state);
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
        if(world.getBlockEntity(pos)instanceof SanboBlockEntity) {
            if(!player.isSuppressingBounce()){
            SanboBlockEntity  tileEntity= (SanboBlockEntity) world.getBlockEntity(pos);
            ItemStack heldStack=player.getItemInHand(hand);
            ItemStack insertStack=heldStack.copy();
            insertStack.setCount(1);
            if(tileEntity.canPlaceItem(0,insertStack)){
                tileEntity.setItem(0,insertStack);
                tileEntity.markUpdated();
                heldStack.shrink(1);
                world.playSound((Player) null,pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS,30F,10F);
                return InteractionResult.SUCCESS;
            }else {
                ItemStack pickedStack = tileEntity.pickItem().copy();
                if (heldStack.isEmpty()) {
                    tileEntity.markUpdated();
                    player.setItemInHand(hand, pickedStack);
                    world.playSound((Player) null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 30F, 10F);
                    return InteractionResult.SUCCESS;
                } else if (!player.getInventory().add(pickedStack)) {
                    tileEntity.markUpdated();
                    player.drop(pickedStack, false);
                    world.playSound((Player) null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 30F, 10F);
                    return InteractionResult.SUCCESS;
                }
            }
            }else{
                SanboBlockEntity  tileEntity= (SanboBlockEntity) world.getBlockEntity(pos);
                     tileEntity.moveItemToExportSlot();
            }

        }
        return InteractionResult.SUCCESS;
    }



    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }


    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        UrushiUtils.setInfo(list,"sanbo");
   }
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152160_, BlockState p_152161_, BlockEntityType<T> p_152162_) {
        return createTickerHelper(p_152162_, BlockEntityRegister.Sanbo.get(), SanboBlockEntity::tick);
    }


    public void onRemove(BlockState p_48713_, Level p_48714_, BlockPos p_48715_, BlockState p_48716_, boolean p_48717_) {
        if (!p_48713_.is(p_48716_.getBlock())) {
            BlockEntity blockentity = p_48714_.getBlockEntity(p_48715_);
            if (blockentity instanceof SanboBlockEntity) {
                if (p_48714_ instanceof ServerLevel) {
                    Containers.dropContents(p_48714_, p_48715_, (SanboBlockEntity)blockentity);
                 }

                p_48714_.updateNeighbourForOutputSignal(p_48715_, this);
            }

            super.onRemove(p_48713_, p_48714_, p_48715_, p_48716_, p_48717_);
        }
    }
}
