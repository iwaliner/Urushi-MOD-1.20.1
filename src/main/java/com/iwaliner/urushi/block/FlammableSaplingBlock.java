package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FlammableSaplingBlock extends SaplingBlock {
    public FlammableSaplingBlock(AbstractTreeGrower p_55978_, Properties p_55979_) {
        super(p_55978_, p_55979_);
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource rand) {
        if(UrushiUtils.isAprilFoolsDay()) {
            if(state.getBlock()== ItemAndBlockRegister.cypress_sapling.get()||state.getBlock()==ItemAndBlockRegister.japanese_cedar_sapling.get()) {
                Vec3 color = new Vec3(0.85D, 0.655D, 0D);
                for (int i = 0; i < 10; i++) {
                    double d0 = (double) pos.getX() + rand.nextDouble();
                    double d1 = (double) pos.getY() + rand.nextDouble();
                    double d2 = (double) pos.getZ() + rand.nextDouble();
                    world.addParticle(new DustParticleOptions(color.toVector3f(), 1.0F), d0, d1, d2, 0F, -0.1F, 0F);
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
