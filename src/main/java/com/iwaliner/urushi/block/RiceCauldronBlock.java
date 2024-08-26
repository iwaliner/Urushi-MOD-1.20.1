package com.iwaliner.urushi.block;


import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.TagUrushi;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.blockentity.RiceCauldronBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import net.minecraft.util.RandomSource;

public class RiceCauldronBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final IntegerProperty VARIANT = IntegerProperty.create("variant",0,4);

    private static final VoxelShape BOX = Block.box(2D, 0.0D, 2D, 14D, 5D, 14D);

    public RiceCauldronBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(VARIANT,Integer.valueOf(0)).setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return BOX;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return   new RiceCauldronBlockEntity(pos,state);
    }

    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(VARIANT,FACING);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(world.getBlockEntity(pos)instanceof RiceCauldronBlockEntity) {
            RiceCauldronBlockEntity  tileEntity= (RiceCauldronBlockEntity) world.getBlockEntity(pos);
            if(state.getValue(VARIANT)==0){
                world.setBlockAndUpdate(pos,this.defaultBlockState().setValue(VARIANT,Integer.valueOf(1)));
                world.playSound((Player) null,pos, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS,1F,1F);
                return InteractionResult.SUCCESS;
            }else if(state.getValue(VARIANT)==1){
                if(player.getItemInHand(hand).is(TagUrushi.RICE)){
                    tileEntity.setItem(0, new ItemStack(ItemAndBlockRegister.rice.get(),player.getItemInHand(hand).getCount()));
                    player.setItemInHand(hand,ItemStack.EMPTY);
                    world.playSound((Player) null,pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1F,1F);
                  return InteractionResult.SUCCESS;
                }else{
                    world.playSound((Player) null,pos,SoundEvents.BARREL_CLOSE,SoundSource.BLOCKS,1F,1F);
                    world.setBlockAndUpdate(pos,this.defaultBlockState().setValue(VARIANT,Integer.valueOf(0)));
                    return InteractionResult.SUCCESS;
                }
            }else if(state.getValue(VARIANT)==3){
                world.setBlockAndUpdate(pos,this.defaultBlockState().setValue(VARIANT,Integer.valueOf(4)));
                world.playSound((Player) null,pos,SoundEvents.BARREL_CLOSE,SoundSource.BLOCKS,1F,1F);

                return InteractionResult.SUCCESS;
            }else if(state.getValue(VARIANT)==4){
                ItemStack stack=player.getItemInHand(hand);
                if (stack.isEmpty()) {
                    player.setItemInHand(hand, tileEntity.getItem(1));
                } else if (!player.getInventory().add(tileEntity.getItem(1))) {
                    player.drop(tileEntity.getItem(1), false);
                }
                world.playSound((Player) null,pos,SoundEvents.ITEM_PICKUP,SoundSource.BLOCKS,1F,1F);
                tileEntity.setItem(1,ItemStack.EMPTY);
                return InteractionResult.SUCCESS;
            }

        }
        return InteractionResult.FAIL;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if(state.getValue(VARIANT)==3||state.getValue(VARIANT)==4) {
            double d0 = (double) pos.getX() + random.nextInt(10) * 0.1D;
            double d1 = (double) pos.getY() + random.nextInt(10) * 0.1D;
            double d2 = (double) pos.getZ() + random.nextInt(10) * 0.1D;
            double d3 = (double) pos.getX() + random.nextInt(10) * 0.1D;
            double d4 = (double) pos.getY() + random.nextInt(10) * 0.1D;
            double d5 = (double) pos.getZ() + random.nextInt(10) * 0.1D;
            world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.1D, 0.0D);
            world.addParticle(ParticleTypes.CLOUD, d3, d4, d5, 0.0D, 0.1D, 0.0D);
        }
    }

    public BlockState rotate(BlockState p_52716_, Rotation p_52717_) {
        return p_52716_.setValue(FACING, p_52717_.rotate(p_52716_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_52713_, Mirror p_52714_) {
        return p_52713_.rotate(p_52714_.getRotation(p_52713_.getValue(FACING)));
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }


    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        UrushiUtils.setInfo(list,"ricecauldron");
   }
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152160_, BlockState p_152161_, BlockEntityType<T> p_152162_) {
        return createTickerHelper(p_152162_, BlockEntityRegister.RiceCauldronBlockEntity.get(), RiceCauldronBlockEntity::tick);
    }
}
