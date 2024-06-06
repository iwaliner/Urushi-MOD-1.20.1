package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ConfigUrushi;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.TagUrushi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.Nullable;

import net.minecraft.util.RandomSource;

public class JapaneseTimberBambooBlock extends Block implements net.minecraftforge.common.IPlantable{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);




    public JapaneseTimberBambooBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    };


    @Override
    public void tick(BlockState p_225534_1_, ServerLevel p_225534_2_, BlockPos p_225534_3_, RandomSource p_60465_) {
        if (!p_225534_1_.canSurvive(p_225534_2_, p_225534_3_)) {
            p_225534_2_.destroyBlock(p_225534_3_, true);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        if (level.isEmptyBlock(pos.above())) {
            int i;
            for(i = 1; level.getBlockState(pos.below(i)).is(this); ++i) {
            }

            if (i < ConfigUrushi.maxHightBamboo.get()) {
                int j = state.getValue(AGE);
                if(state.getValue(AGE)==Integer.valueOf(3)){
                    level.setBlockAndUpdate(pos, this.defaultBlockState().setValue(AGE,Integer.valueOf(0)));
                }
                if (j ==0||j ==1) {

                    if(i<ConfigUrushi.maxHightBamboo.get()-6){
                        level.setBlockAndUpdate(pos.above(), this.defaultBlockState().setValue(AGE,Integer.valueOf(0)));

                    }else{
                        if(random.nextInt(3)==0){
                            level.setBlockAndUpdate(pos.above(), this.defaultBlockState().setValue(AGE,Integer.valueOf(1)));
                        }else {
                            if(j==0) {
                                level.setBlockAndUpdate(pos.above(), this.defaultBlockState().setValue(AGE, Integer.valueOf(0)));
                            }else{
                                level.setBlockAndUpdate(pos.above(), this.defaultBlockState().setValue(AGE, Integer.valueOf(2)));

                            }
                        }
                    }
                }
            }else if(state.getValue(AGE)!=Integer.valueOf(2)){
                level.setBlockAndUpdate(pos, this.defaultBlockState().setValue(AGE, Integer.valueOf(2)));
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
       if( context.getLevel().getBlockState(context.getClickedPos().below()).getBlock() instanceof JapaneseTimberBambooBlock){
           if(context.getLevel().getBlockState(context.getClickedPos().below()).getValue(AGE)==Integer.valueOf(3)){
               context.getLevel().setBlockAndUpdate(context.getClickedPos().below(), this.defaultBlockState());
           }
          return this.defaultBlockState();
       }else{
           return  this.defaultBlockState().setValue(AGE,Integer.valueOf(3));
       }
    }

    @Override
    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, LevelAccessor p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        if (!p_196271_1_.canSurvive(p_196271_4_, p_196271_5_)) {
            p_196271_4_.scheduleTick(p_196271_5_, this, 1);
        }

        return super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader iWorldReader, BlockPos pos) {
        BlockState soil = iWorldReader.getBlockState(pos.below());
        if (soil.canSustainPlant(iWorldReader, pos.below(), Direction.UP, this)) return true;
        BlockState blockstate = iWorldReader.getBlockState(pos.below());

        if (blockstate.getBlock() == this) {
            return true;
        } else {

            if (blockstate.is(TagUrushi.JAPANESE_TIMBER_BAMBOO_PLACEABLE) ) {
                return true;
            }

            return false;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(AGE);
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.PLAINS;
    }

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return defaultBlockState();
    }



    public BlockBehaviour.OffsetType getOffsetType() {
        return BlockBehaviour.OffsetType.XZ;
    }


    @Override
    public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220071_2_, BlockPos p_220071_3_, CollisionContext p_60575_) {
        Vec3 vector3d = p_220053_1_.getOffset(p_220071_2_, p_220071_3_);
        return SHAPE.move(vector3d.x, vector3d.y, vector3d.z);
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
