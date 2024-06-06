package com.iwaliner.urushi.block;

import com.iwaliner.urushi.TagUrushi;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.util.RandomSource;

public class ShiitakeBlock extends BushBlock {
    protected static final float AABB_OFFSET = 3.0F;
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);

    public ShiitakeBlock(Properties p_51021_) {
        super(p_51021_);
    }
    public VoxelShape getShape(BlockState p_54889_, BlockGetter p_54890_, BlockPos p_54891_, CollisionContext p_54892_) {
        return SHAPE;
    }

    public void randomTick(BlockState p_54884_, ServerLevel p_54885_, BlockPos p_54886_, RandomSource p_54887_) {
        if (p_54887_.nextInt(15) == 0) {
            int i = 5;
            int j = 4;

            for(BlockPos blockpos : BlockPos.betweenClosed(p_54886_.offset(-4, -1, -4), p_54886_.offset(4, 1, 4))) {
                if (p_54885_.getBlockState(blockpos).is(this)) {
                    --i;
                    if (i <= 0) {
                        return;
                    }
                }
            }

            BlockPos blockpos1 = p_54886_.offset(p_54887_.nextInt(3) - 1, p_54887_.nextInt(2) - p_54887_.nextInt(2), p_54887_.nextInt(3) - 1);

            for(int k = 0; k < 4; ++k) {
                if (p_54885_.isEmptyBlock(blockpos1) && p_54884_.canSurvive(p_54885_, blockpos1)) {
                    p_54886_ = blockpos1;
                }

                blockpos1 = p_54886_.offset(p_54887_.nextInt(3) - 1, p_54887_.nextInt(2) - p_54887_.nextInt(2), p_54887_.nextInt(3) - 1);
            }

            if (p_54885_.isEmptyBlock(blockpos1) && p_54884_.canSurvive(p_54885_, blockpos1)) {
                p_54885_.setBlock(blockpos1, p_54884_, 2);
            }
        }

    }

    protected boolean mayPlaceOn(BlockState p_54894_, BlockGetter p_54895_, BlockPos p_54896_) {
        return p_54894_.isSolidRender(p_54895_, p_54896_);
    }

    public boolean canSurvive(BlockState p_54880_, LevelReader p_54881_, BlockPos p_54882_) {
        BlockPos blockpos = p_54882_.below();
        BlockState blockstate = p_54881_.getBlockState(blockpos);
        if (blockstate.is(TagUrushi.SHIITAKE_GROW_BLOCK)) {
            return true;
        } else {
           return false;
        }
    }


}
