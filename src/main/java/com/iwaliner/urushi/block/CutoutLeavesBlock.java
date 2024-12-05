package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ConfigUrushi;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ParticleRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.OptionalInt;

public class CutoutLeavesBlock extends LeavesBlock {

    public CutoutLeavesBlock(BlockBehaviour.Properties p_54422_) {
        super(p_54422_);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(DISTANCE, 7)).setValue(PERSISTENT, false)).setValue(WATERLOGGED, false));
    }

    private static BlockState updateDistance(BlockState p_54436_, LevelAccessor p_54437_, BlockPos p_54438_) {
        int i = 7;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(Direction direction : Direction.values()) {
            blockpos$mutableblockpos.setWithOffset(p_54438_, direction);
            i = Math.min(i, getDistanceAt(p_54437_.getBlockState(blockpos$mutableblockpos)) + 1);
            if (i == 1) {
                break;
            }
        }

        return p_54436_.setValue(DISTANCE, Integer.valueOf(i));
    }

    private static int getDistanceAt(BlockState p_54464_) {
        return getOptionalDistanceAt(p_54464_).orElse(7);
    }

    public static @NotNull OptionalInt getOptionalDistanceAt(BlockState p_277868_) {
        if (p_277868_.is(BlockTags.LOGS)) {
            return OptionalInt.of(0);
        } else {
            return p_277868_.hasProperty(DISTANCE) ? OptionalInt.of((Integer)p_277868_.getValue(DISTANCE)) : OptionalInt.empty();
        }
    }

    public void animateTick(@NotNull BlockState state, Level level, BlockPos pos, @NotNull RandomSource random) {
        if (level.isRainingAt(pos.above())) {
            if (random.nextInt(15) == 1) {
                BlockPos blockpos = pos.below();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                    ParticleUtils.spawnParticleBelow(level, pos, random, ParticleTypes.DRIPPING_WATER);
                }
            }
        }
        Block block = state.getBlock();
        if(level.getBlockState(pos.below()).isAir()) {
            boolean isLeaf=(block!=ItemAndBlockRegister.sakura_leaves.get()&&block!=ItemAndBlockRegister.glowing_sakura_leaves.get());
            int amount=101-(isLeaf? ConfigUrushi.fallingLeafParticleAmount.get() : ConfigUrushi.fallingSakuraParticleAmount.get());
            if(random.nextInt(amount)==0) {
                ParticleOptions particle=null;
                if (block == ItemAndBlockRegister.red_leaves.get()) {
                    particle=ParticleRegister.FallingRedLeaves.get();
                }else if (block == ItemAndBlockRegister.orange_leaves.get()) {
                    particle=ParticleRegister.FallingOrangeLeaves.get();
                }else if (block == ItemAndBlockRegister.yellow_leaves.get()) {
                    particle=ParticleRegister.FallingYellowLeaves.get();
                }else if (block == ItemAndBlockRegister.sakura_leaves.get()||block == ItemAndBlockRegister.glowing_sakura_leaves.get()) {
                    particle=ParticleRegister.FallingSakuraLeaves.get();
                }
                if(particle!=null) {
                    ParticleUtils.spawnParticleBelow(level, pos, random, particle);
                }

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
