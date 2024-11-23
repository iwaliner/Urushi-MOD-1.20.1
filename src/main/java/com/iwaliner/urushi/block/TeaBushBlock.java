package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TeaBushBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    private static final VoxelShape SHAPE = Shapes.or(Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(6.0D, 0.0D, 6.0D, 10.0D, 8.0D, 10.0D));

    public TeaBushBlock(Properties p_51021_) {
        super(p_51021_);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));

    }
    public VoxelShape getShape(BlockState p_152084_, BlockGetter p_152085_, BlockPos p_152086_, CollisionContext p_152087_) {
        return SHAPE;
    }
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        int i = state.getValue(AGE);
        boolean flag = i == 1;
        if (!flag && player.getItemInHand(hand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        } else if (i ==1) {
            int j = 1 + level.random.nextInt(2);
            popResource(level, pos.above(), new ItemStack(ItemAndBlockRegister.tea_leaf.get(), j ));
            level.playSound((Player)null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            BlockState blockstate = state.setValue(AGE, Integer.valueOf(0));
            level.setBlock(pos, blockstate, 2);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockstate));
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use(state, level, pos, player, hand, result);
        }
    }
    public boolean isValidBonemealTarget(LevelReader p_256056_, BlockPos p_57261_, BlockState p_57262_, boolean p_57263_) {
        return p_57262_.getValue(AGE) ==0;
    }

    @Override
    public boolean isBonemealSuccess(Level p_220878_, RandomSource p_220879_, BlockPos p_220880_, BlockState p_220881_) {
        return true;
    }

    public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState state) {
        level.setBlock(pos, state.setValue(AGE, state.getValue(AGE)==0? 1 : 0),2);
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_57282_) {
        p_57282_.add(AGE);
    }
    public void randomTick(BlockState p_222563_, ServerLevel p_222564_, BlockPos p_222565_, RandomSource p_222566_) {
        int i = p_222563_.getValue(AGE);
        if (i < 1 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_222564_, p_222565_, p_222563_, p_222566_.nextInt(5) == 0)) {
            BlockState blockstate = p_222563_.setValue(AGE, Integer.valueOf(i + 1));
            p_222564_.setBlock(p_222565_, blockstate, 2);
            p_222564_.gameEvent(GameEvent.BLOCK_CHANGE, p_222565_, GameEvent.Context.of(blockstate));
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_222564_, p_222565_, p_222563_);
        }

    }
}
