package com.iwaliner.urushi.block;


import com.iwaliner.urushi.ItemAndBlockRegister;
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
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
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

public class DirtFurnaceBlock extends HorizonalRotateBlock {
    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    private static final VoxelShape Shape1 = Block.box(0D, 0.0D, 0D, 4D, 16D, 16D);
    private static final VoxelShape Shape2 = Block.box(12D, 0.0D, 0D, 16D, 16D, 16D);
    private static final VoxelShape Shape3 = Block.box(0D, 0.0D, 0D, 16D, 16D, 4D);
    private static final VoxelShape Shape4 = Block.box(0D, 0.0D, 12D, 16D, 16D, 16D);
    private static final VoxelShape AABB = Shapes.or(Shape1, Shape2, Shape3, Shape4);

    public DirtFurnaceBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.valueOf(false)));
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return AABB;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING,LIT);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(state.getValue(LIT)&&player.getItemInHand(hand).getItem()!= Item.byBlock(ItemAndBlockRegister.rice_cauldron.get())){
            world.setBlockAndUpdate(pos,state.setValue(LIT,Boolean.valueOf(false)));
            world.playSound((Player) null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1F);
            return InteractionResult.SUCCESS;
        }else{
            if(player.getItemInHand(hand).is(TagUrushi.IGNITER)){
                world.playSound((Player) null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);
                world.setBlockAndUpdate(pos,state.setValue(LIT,Boolean.valueOf(true)));
                return InteractionResult.SUCCESS;
            }else{
                return InteractionResult.FAIL;
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos neighbor, boolean boo) {
        if(!state.getValue(LIT)){
            if(world.getBlockState(neighbor).getBlock() instanceof FireBlock){
                world.setBlockAndUpdate(pos,state.setValue(LIT,Boolean.valueOf(true)));
                world.playSound((Player) null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, world.random.nextFloat() * 0.4F + 0.8F);

            }   }




    }
    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        UrushiUtils.setInfo(list,"dirtfurnace");
         }



}
