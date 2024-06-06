package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.TagUrushi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import net.minecraft.util.RandomSource;
import java.util.function.ToIntFunction;

public class LanternPlantBlock extends BushBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final ToIntFunction<BlockState> LIGHT_EMISSION = (state) -> {
        if(state.getValue(LIT)){
            return 15;
        }else if(state.getValue(AGE)==1){
            return 4;
        }else {
            return 0;
        }
    };
    public LanternPlantBlock(Properties p_51021_) {
        super(p_51021_);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue(LIT,Boolean.valueOf(false)));
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
       if(random.nextInt(10)==0){
           if(state.getValue(AGE)==Integer.valueOf(0)){
               level.setBlockAndUpdate(pos,state.setValue(AGE,Integer.valueOf(1)));
           }
       }else if(random.nextInt(10)==0){
           if(level.getBlockState(pos.below()).getBlock() instanceof LanternPlantBlock||level.getBlockState(pos.above()).getBlock() instanceof LanternPlantBlock){

           }else if(level.getBlockState(pos.above()).isAir()){
               level.setBlockAndUpdate(pos.above(),this.defaultBlockState());
           }
       }
    }
    public ItemStack getCloneItemStack(BlockGetter p_152966_, BlockPos p_152967_, BlockState p_152968_) {
        return new ItemStack(ItemAndBlockRegister.lantern_plant_fruit.get());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
     if(state.getValue(AGE)==Integer.valueOf(1)){
         level.setBlockAndUpdate(pos,state.setValue(LIT,Boolean.valueOf(true)));
         return InteractionResult.SUCCESS;
     }
     return InteractionResult.FAIL;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(AGE,LIT);
    }
    protected boolean mayPlaceOn(BlockState state, BlockGetter p_51043_, BlockPos p_51044_) {
        return state.getBlock() ==Blocks.SNOW_BLOCK||state.getBlock() ==Blocks.SNOW||state.is(BlockTags.DIRT) || state.is(Blocks.FARMLAND)||state.is(Blocks.STONE)||state.is(TagUrushi.YOMI_STONE)||state.getBlock() instanceof LanternPlantBlock;
    }
    public boolean canSurvive(BlockState p_51028_, LevelReader p_51029_, BlockPos p_51030_) {
        BlockPos blockpos = p_51030_.below();
        if (p_51028_.getBlock() == this) //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return p_51029_.getBlockState(blockpos).canSustainPlant(p_51029_, blockpos, Direction.UP, this);
        return this.mayPlaceOn(p_51029_.getBlockState(blockpos), p_51029_, blockpos);
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
