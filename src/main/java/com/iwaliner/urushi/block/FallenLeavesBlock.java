package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ConfigUrushi;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ParticleRegister;
import com.iwaliner.urushi.TagUrushi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.Fluids;

public class FallenLeavesBlock extends CarpetBlock {
    public FallenLeavesBlock(Properties p_152915_) {
        super(p_152915_);
    }
    public boolean canSurvive(BlockState p_152922_, LevelReader level, BlockPos pos) {
        if(level.getFluidState(pos.below()).is(Fluids.WATER)){
            int depth=100;
            for(int i=1;i<8;i++){
                if(level.getFluidState(pos.below(i)).is(Fluids.WATER)){
                    depth=i;
                    break;
                }
            }
            // same result with depth>4? false: !level.isEmptyBlock(pos.below());
            return depth <= 4 && !level.isEmptyBlock(pos.below());

        }else {
            return level.getBlockState(pos.below()).isSolidRender(level, pos);
        }

    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        Level level=  context.getLevel();
        BlockPos pos=context.getClickedPos();
        if (level.getBlockState(pos.below()).is(TagUrushi.GRASS_BLOCK_WITH_FALLEN_LEAVES_INGREDIENT)) {
            if (state.getBlock() == ItemAndBlockRegister.fallen_red_leaves.get()) {
                level.setBlockAndUpdate(pos.below(), ItemAndBlockRegister.grass_block_with_fallen_red_leaves.get().defaultBlockState());
            } else if (state.getBlock() == ItemAndBlockRegister.fallen_orange_leaves.get()) {
                level.setBlockAndUpdate(pos.below(), ItemAndBlockRegister.grass_block_with_fallen_orange_leaves.get().defaultBlockState());
            } else if (state.getBlock() == ItemAndBlockRegister.fallen_yellow_leaves.get()) {
                level.setBlockAndUpdate(pos.below(), ItemAndBlockRegister.grass_block_with_fallen_yellow_leaves.get().defaultBlockState());
            } else if (state.getBlock() == ItemAndBlockRegister.fallen_japanese_apricot_leaves.get()) {
                level.setBlockAndUpdate(pos.below(), ItemAndBlockRegister.grass_block_with_fallen_japanese_apricot_leaves.get().defaultBlockState());
            } else if (state.getBlock() == ItemAndBlockRegister.fallen_sakura_leaves.get()) {
                level.setBlockAndUpdate(pos.below(), ItemAndBlockRegister.grass_block_with_fallen_sakura_leaves.get().defaultBlockState());
            }
        }else if (level.getBlockState(pos.below()).getBlock()==ItemAndBlockRegister.kakuriyo_grass_block.get()) {
            if (state.getBlock() == ItemAndBlockRegister.fallen_red_leaves.get()) {
                level.setBlockAndUpdate(pos.below(), ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_red_leaves.get().defaultBlockState());
            } else if (state.getBlock() == ItemAndBlockRegister.fallen_orange_leaves.get()) {
                level.setBlockAndUpdate(pos.below(), ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_orange_leaves.get().defaultBlockState());
            } else if (state.getBlock() == ItemAndBlockRegister.fallen_yellow_leaves.get()) {
                level.setBlockAndUpdate(pos.below(), ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_yellow_leaves.get().defaultBlockState());
            } else if (state.getBlock() == ItemAndBlockRegister.fallen_japanese_apricot_leaves.get()) {
                level.setBlockAndUpdate(pos.below(), ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_japanese_apricot_leaves.get().defaultBlockState());
            } else if (state.getBlock() == ItemAndBlockRegister.fallen_sakura_leaves.get()) {
                level.setBlockAndUpdate(pos.below(), ItemAndBlockRegister.kakuriyo_grass_block_with_fallen_sakura_leaves.get().defaultBlockState());
            }
        }

        return true;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if(level.getFluidState(pos.below()).is(Fluids.WATER)) {
            boolean isLeaf=state.getBlock()!=ItemAndBlockRegister.fallen_sakura_leaves.get();
            int amount=101-(isLeaf? ConfigUrushi.fallingLeafParticleAmount.get() : ConfigUrushi.fallingSakuraParticleAmount.get());
            if(random.nextInt(amount)==0) {
                ParticleOptions particle=null;
                if (state.getBlock() == ItemAndBlockRegister.fallen_red_leaves.get()) {
                    particle= ParticleRegister.FallingRedLeaves.get();
                }else if (state.getBlock() == ItemAndBlockRegister.fallen_orange_leaves.get()) {
                    particle=ParticleRegister.FallingOrangeLeaves.get();
                }else if (state.getBlock() == ItemAndBlockRegister.fallen_yellow_leaves.get()) {
                    particle=ParticleRegister.FallingYellowLeaves.get();
                }else if (state.getBlock() == ItemAndBlockRegister.fallen_sakura_leaves.get()) {
                    particle=ParticleRegister.FallingSakuraLeaves.get();
                }
                double d0 = (double)pos.getX() + random.nextDouble();
                double d1 = (double)pos.getY() - 0.05D;
                double d2 = (double)pos.getZ() + random.nextDouble();
                if(particle!=null)
                    level.addParticle(particle, d0, d1, d2, 0.0D, 0.0D, 0.0D);

            }
        }
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
