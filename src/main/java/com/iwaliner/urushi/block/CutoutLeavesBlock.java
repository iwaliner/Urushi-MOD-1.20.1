package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ConfigUrushi;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ParticleRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.util.RandomSource;

public class CutoutLeavesBlock extends Block implements net.minecraftforge.common.IForgeShearable {
    public static final int DECAY_DISTANCE = 7;
    public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE;
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    private static final int TICK_DELAY = 1;

    public CutoutLeavesBlock(BlockBehaviour.Properties p_54422_) {
        super(p_54422_);
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, Integer.valueOf(7)).setValue(PERSISTENT, Boolean.valueOf(false)));
    }

    public VoxelShape getBlockSupportShape(BlockState p_54456_, BlockGetter p_54457_, BlockPos p_54458_) {
        return Shapes.empty();
    }

    public boolean isRandomlyTicking(BlockState p_54449_) {
        return p_54449_.getValue(DISTANCE) == 7 && !p_54449_.getValue(PERSISTENT);
    }

    public void randomTick(BlockState p_54451_, ServerLevel p_54452_, BlockPos p_54453_, RandomSource p_54454_) {
        if (!p_54451_.getValue(PERSISTENT) && p_54451_.getValue(DISTANCE) == 7) {
            dropResources(p_54451_, p_54452_, p_54453_);
            p_54452_.removeBlock(p_54453_, false);
        }

    }

    public void tick(BlockState p_54426_, ServerLevel p_54427_, BlockPos p_54428_, RandomSource p_54429_) {
        p_54427_.setBlock(p_54428_, updateDistance(p_54426_, p_54427_, p_54428_), 3);
    }

    public int getLightBlock(BlockState p_54460_, BlockGetter p_54461_, BlockPos p_54462_) {
        return 1;
    }

    public BlockState updateShape(BlockState p_54440_, Direction p_54441_, BlockState p_54442_, LevelAccessor p_54443_, BlockPos p_54444_, BlockPos p_54445_) {
        int i = getDistanceAt(p_54442_) + 1;
        if (i != 1 || p_54440_.getValue(DISTANCE) != i) {
            p_54443_.scheduleTick(p_54444_, this, 1);
        }

        return p_54440_;
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
        if (p_54464_.is(BlockTags.LOGS)) {
            return 0;
        } else {
            return p_54464_.getBlock() instanceof CutoutLeavesBlock ? p_54464_.getValue(DISTANCE) : 7;
        }
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.isRainingAt(pos.above())) {
            if (random.nextInt(15) == 1) {
                BlockPos blockpos = pos.below();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                    double d0 = (double)pos.getX() + random.nextDouble();
                    double d1 = (double)pos.getY() - 0.05D;
                    double d2 = (double)pos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        if(level.getBlockState(pos.below()).isAir()) {
            boolean isLeaf=(state.getBlock()!=ItemAndBlockRegister.sakura_leaves.get()&&state.getBlock()!=ItemAndBlockRegister.glowing_sakura_leaves.get());
            int amount=101-(isLeaf? ConfigUrushi.fallingLeafParticleAmount.get() : ConfigUrushi.fallingSakuraParticleAmount.get());
            if(random.nextInt(amount)==0) {
                ParticleOptions particle=null;
                if (state.getBlock() == ItemAndBlockRegister.red_leaves.get()) {
                    particle=ParticleRegister.FallingRedLeaves.get();
                }else if (state.getBlock() == ItemAndBlockRegister.orange_leaves.get()) {
                    particle=ParticleRegister.FallingOrangeLeaves.get();
                }else if (state.getBlock() == ItemAndBlockRegister.yellow_leaves.get()) {
                    particle=ParticleRegister.FallingYellowLeaves.get();
                }else if (state.getBlock() == ItemAndBlockRegister.sakura_leaves.get()||state.getBlock() == ItemAndBlockRegister.glowing_sakura_leaves.get()) {
                    particle=ParticleRegister.FallingSakuraLeaves.get();
                }
                double d0 = (double)pos.getX() + random.nextDouble();
                double d1 = (double)pos.getY() - 0.05D;
                double d2 = (double)pos.getZ() + random.nextDouble();
                if(particle!=null)
                level.addParticle(particle, d0, d1, d2, 0D, 0.0D, 0D);

            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54447_) {
        p_54447_.add(DISTANCE, PERSISTENT);
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_54424_) {
        return updateDistance(this.defaultBlockState().setValue(PERSISTENT, Boolean.valueOf(true)), p_54424_.getLevel(), p_54424_.getClickedPos());
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
