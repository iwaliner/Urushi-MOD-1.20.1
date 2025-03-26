package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ConfigUrushi;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ParticleRegister;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;

public class CutoutLeavesBlock extends LeavesBlock {

    public CutoutLeavesBlock(BlockBehaviour.Properties p_54422_) {
        super(p_54422_);
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
        if(UrushiUtils.isAprilFoolsDay()) {
            if (block == ItemAndBlockRegister.japanese_cedar_leaves.get() || block == ItemAndBlockRegister.cypress_leaves.get()) {
                Vec3 color = new Vec3(0.85D, 0.655D, 0D);
                for (int i = 0; i < 3; i++) {
                    RandomSource rand = level.getRandom();
                    double d0 = (double) pos.getX() + rand.nextDouble();
                    double d1 = (double) pos.getY() + rand.nextDouble();
                    double d2 = (double) pos.getZ() + rand.nextDouble();
                    level.addParticle(new DustParticleOptions(color.toVector3f(), 1.0F), d0, d1, d2, 0F, -0.1F, 0F);

                }
            }
        }
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
    public static void spawnParticleGravityBelow(Level p_273159_, BlockPos p_273452_, RandomSource p_273538_, ParticleOptions p_273419_) {
        double d0 = (double)p_273452_.getX() + p_273538_.nextDouble();
        double d1 = (double)p_273452_.getY() - 0.05D;
        double d2 = (double)p_273452_.getZ() + p_273538_.nextDouble();
        p_273159_.addParticle(p_273419_, d0, d1, d2, 0.0D, -0.1D, 0.0D);
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
