package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.RecipeTypeRegister;
import com.iwaliner.urushi.recipe.RainwaterTankRecipe;
import com.iwaliner.urushi.recipe.SenbakokiRecipe;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class RainwaterTankBlock extends AbstractHorizontalRotateHighBlock{
    public static final BooleanProperty FILLED = BooleanProperty.create("filled");

    public RainwaterTankBlock(Properties p_49795_) {
        super( p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, Direction.NORTH).setValue(FILLED, Boolean.valueOf(false)));
    }
    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Block.box(1D, 0.0D, 1D, 15D, 16.0D, 15.0D);
    }
    public BlockState updateShape(BlockState p_52894_, Direction p_52895_, BlockState p_52896_, LevelAccessor p_52897_, BlockPos p_52898_, BlockPos p_52899_) {
        DoubleBlockHalf doubleblockhalf = p_52894_.getValue(HALF);
        if (p_52895_.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (p_52895_ == Direction.UP) || p_52896_.is(this) && p_52896_.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && p_52895_ == Direction.DOWN && !p_52894_.canSurvive(p_52897_, p_52898_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_52894_, p_52895_, p_52896_, p_52897_, p_52898_, p_52899_);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_52863_) {
        BlockPos blockpos = p_52863_.getClickedPos();
        Level level = p_52863_.getLevel();
        return blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(p_52863_) ? super.getStateForPlacement(p_52863_).setValue(FACING, p_52863_.getHorizontalDirection().getOpposite()): null;
    }
    public void setPlacedBy(Level level, BlockPos pos, BlockState p_52874_, LivingEntity p_52875_, ItemStack p_52876_) {
        BlockPos blockpos = pos.above();
        BlockState state2 = level.getBlockState(pos);
        if (state2.getBlock() instanceof RainwaterTankBlock) {
            level.setBlock(blockpos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(FACING, state2.getValue(FACING)), 3);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_52901_) {
        p_52901_.add(HALF,FACING,FILLED);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
       if(level.isRaining()&&level.canSeeSky(pos)&&state.getValue(HALF)==DoubleBlockHalf.UPPER&&!state.getValue(FILLED)){
           if(randomSource.nextInt(5)==0){
               level.setBlockAndUpdate(pos.below(),level.getBlockState(pos.below()).setValue(FILLED, Boolean.TRUE));
               level.setBlockAndUpdate(pos,state.setValue(FILLED,Boolean.TRUE));
               level.playSound((Player) null,(double) pos.getX()+0.5D,(double) pos.getY()+0.5D,(double) pos.getZ()+0.5D, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS,1F,1F);

           }
       }
    }
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
           ItemStack stack=player.getItemInHand(hand);
            boolean isLower=state.getValue(HALF)==DoubleBlockHalf.LOWER;
            BlockPos anotherPos=isLower? pos.above() : pos.below();
            BlockState anotherState=level.getBlockState(anotherPos);
            BlockState lowerState= isLower? state : anotherState;
            if(anotherState.getBlock() instanceof RainwaterTankBlock) {
                Optional<RainwaterTankRecipe> recipe = Optional.of(level.getRecipeManager())
                        .flatMap(manager -> manager.getRecipeFor(RecipeTypeRegister.RainwaterTankRecipe, new SimpleContainer(stack), level));
                if (lowerState.getValue(FILLED)) {
                    if (recipe.isPresent()) {
                        ItemStack resultStack=recipe.get().getResultItem().copy();
                        resultStack.setCount(stack.getCount());
                        player.setItemInHand(hand,resultStack);
                        level.playSound((Player) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1F, 1F);
                        return InteractionResult.SUCCESS;
                    }
                    if (stack.getItem() == Items.BUCKET) {
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
                        } else if (!player.getInventory().add(new ItemStack(Items.WATER_BUCKET))) {
                            player.drop(new ItemStack(Items.WATER_BUCKET), false);
                        }
                        level.playSound((Player) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1F, 1F);
                        return InteractionResult.SUCCESS;

                    } else if (stack.getItem() == Items.GLASS_BOTTLE) {
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            player.setItemInHand(hand, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER));
                        } else if (!player.getInventory().add(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER))) {
                            player.drop(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER), false);
                        }
                        level.playSound((Player) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1F, 1F);
                        return InteractionResult.SUCCESS;
                    }
                } else {
                    if (stack.getItem() == Items.WATER_BUCKET) {
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                        } else if (!player.getInventory().add(new ItemStack(Items.BUCKET))) {
                            player.drop(new ItemStack(Items.BUCKET), false);
                        }
                        level.setBlockAndUpdate(pos, state.setValue(FILLED, Boolean.TRUE));
                        level.setBlockAndUpdate(anotherPos, anotherState.setValue(FILLED, Boolean.TRUE));
                        level.playSound((Player) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1F, 1F);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        return InteractionResult.FAIL;
    }
    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        UrushiUtils.setInfo(list,"rainwater_tank1");
        UrushiUtils.setInfo(list,"rainwater_tank2");
    }
}
