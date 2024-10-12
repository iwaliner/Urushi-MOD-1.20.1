package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class DoubleSlabBlock extends HorizonalRotateBlock {
    public static final IntegerProperty UNDER = IntegerProperty.create("under", 0, 140);
    public static final IntegerProperty UPPER = IntegerProperty.create("upper", 0, 140);
    public static List<Block> slabList=new ArrayList<>();

    public DoubleSlabBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(UNDER, 0).setValue(UPPER,0).setValue(FACING, Direction.NORTH));

    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING,UNDER,UPPER);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return target.getLocation().y- pos.getY() <= 0.5D? new ItemStack( slabList.get(state.getValue(UNDER)) ): new ItemStack(slabList.get(state.getValue(UPPER)));
    }

    public static int getIDFromBlock(Block block){
        return slabList.contains(block)? slabList.indexOf(block) : 0;
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_287732_, LootParams.Builder p_287596_) {
        return super.getDrops(p_287732_, p_287596_);
    }
}
