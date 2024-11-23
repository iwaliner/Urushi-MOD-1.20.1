package com.iwaliner.urushi.block;



import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.TagUrushi;
import com.iwaliner.urushi.util.UrushiUtils;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.RandomSource;

public class OilExtractorBlock extends Block implements WorldlyContainerHolder {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_COMPOSTER;
    private static final VoxelShape OUTER_SHAPE = Shapes.block();
   // public static final Object2FloatMap<ItemLike> COMPOSTABLES = new Object2FloatOpenHashMap<>();

    private static final float value=0.85f;

    private static final VoxelShape[] SHAPES = Util.make(new VoxelShape[9], (p_51967_) -> {
        for(int i = 0; i < 8; ++i) {
            p_51967_[i] = Shapes.join(OUTER_SHAPE, Block.box(2.0D, (double)Math.max(2, 1 + i * 2), 2.0D, 14.0D, 16.0D, 14.0D), BooleanOp.ONLY_FIRST);
        }

        p_51967_[8] = p_51967_[7];
    });


/*
    private static void add(float p_51921_, ItemLike p_51922_) {
        COMPOSTABLES.put(p_51922_.asItem(), p_51921_);
    }*/

    public OilExtractorBlock(Properties p_i49986_1_) {
        super(p_i49986_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(0)));
     //   COMPOSTABLES.defaultReturnValue(-1.0F);
    //    add(value,Items.WHEAT_SEEDS);
    //   add(value,Items.BEETROOT_SEEDS);
   //    add(value,Items.MELON_SEEDS);
   //    add(value,Items.PUMPKIN_SEEDS);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPES[state.getValue(LEVEL)];
    }


    public VoxelShape getInteractionShape(BlockState p_51969_, BlockGetter p_51970_, BlockPos p_51971_) {
        return OUTER_SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState p_51990_, BlockGetter p_51991_, BlockPos p_51992_, CollisionContext p_51993_) {
        return SHAPES[0];
    }

    public void onPlace(BlockState p_51978_, Level p_51979_, BlockPos p_51980_, BlockState p_51981_, boolean p_51982_) {
        if (p_51978_.getValue(LEVEL) == 7) {
            p_51979_.scheduleTick(p_51980_, p_51978_.getBlock(), 20);
        }

    }
    public InteractionResult use(BlockState p_51949_, Level p_51950_, BlockPos p_51951_, Player p_51952_, InteractionHand p_51953_, BlockHitResult p_51954_) {
        int i = p_51949_.getValue(LEVEL);
        ItemStack itemstack = p_51952_.getItemInHand(p_51953_);
        if (i < 8 && itemstack.is(TagUrushi.OIL_EXTRACTOR_INSERTALE)) {
            if (i < 7 && !p_51950_.isClientSide) {
                BlockState blockstate = addItem(p_51949_, p_51950_, p_51951_, itemstack);
                p_51950_.levelEvent(1500, p_51951_, p_51949_ != blockstate ? 1 : 0);
                p_51952_.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
                if (!p_51952_.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            }

            return InteractionResult.sidedSuccess(p_51950_.isClientSide);
        } else if (i == 8) {
            extractProduce(p_51949_, p_51950_, p_51951_);
            return InteractionResult.sidedSuccess(p_51950_.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    public static BlockState insertItem(BlockState p_51930_, ServerLevel p_51931_, ItemStack p_51932_, BlockPos p_51933_) {
        int i = p_51930_.getValue(LEVEL);
        if (i < 7 && p_51932_.is(TagUrushi.OIL_EXTRACTOR_INSERTALE)) {
            BlockState blockstate = addItem(p_51930_, p_51931_, p_51933_, p_51932_);
            p_51932_.shrink(1);
            return blockstate;
        } else {
            return p_51930_;
        }
    }
    public static BlockState extractProduce(BlockState p_51999_, Level p_52000_, BlockPos p_52001_) {
        if (!p_52000_.isClientSide) {
            float f = 0.7F;
            double d0 = (double)(p_52000_.random.nextFloat() * 0.7F) + (double)0.15F;
            double d1 = (double)(p_52000_.random.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
            double d2 = (double)(p_52000_.random.nextFloat() * 0.7F) + (double)0.15F;
            ItemEntity itementity = new ItemEntity(p_52000_, (double)p_52001_.getX() + d0, (double)p_52001_.getY() + d1, (double)p_52001_.getZ() + d2, new ItemStack(ItemAndBlockRegister.vegetable_oil.get()));
            itementity.setDefaultPickUpDelay();
            p_52000_.addFreshEntity(itementity);
        }

        BlockState blockstate = empty(p_51999_, p_52000_, p_52001_);
        p_52000_.playSound((Player)null, p_52001_, SoundEvents.HONEY_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
        return blockstate;
    }
    static BlockState empty(BlockState p_52003_, LevelAccessor p_52004_, BlockPos p_52005_) {
        BlockState blockstate = p_52003_.setValue(LEVEL, Integer.valueOf(0));
        p_52004_.setBlock(p_52005_, blockstate, 3);
        return blockstate;
    }

    static BlockState addItem(BlockState p_51984_, LevelAccessor p_51985_, BlockPos p_51986_, ItemStack p_51987_) {
        int i = p_51984_.getValue(LEVEL);
        float f = value;
        if ((i != 0 || !(f > 0.0F)) && !(p_51985_.getRandom().nextDouble() < (double)f)) {
            return p_51984_;
        } else {
            int j = i + 1;
            BlockState blockstate = p_51984_.setValue(LEVEL, Integer.valueOf(j));
            p_51985_.setBlock(p_51986_, blockstate, 3);
            if (j == 7) {
                p_51985_.scheduleTick(p_51986_, p_51984_.getBlock(), 20);
            }

            return blockstate;
        }
    }
    public void tick(BlockState p_51935_, ServerLevel p_51936_, BlockPos p_51937_, RandomSource p_51938_) {
        if (p_51935_.getValue(LEVEL) == 7) {
            p_51936_.setBlock(p_51937_, p_51935_.cycle(LEVEL), 3);
            p_51936_.playSound((Player)null, p_51937_, SoundEvents.HONEY_BLOCK_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

    }



    public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState p_180641_1_, Level p_180641_2_, BlockPos p_180641_3_) {
        return p_180641_1_.getValue(LEVEL);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51965_) {
        p_51965_.add(LEVEL);
    }

    public boolean isPathfindable(BlockState p_51940_, BlockGetter p_51941_, BlockPos p_51942_, PathComputationType p_51943_) {
        return false;
    }
    public WorldlyContainer getContainer(BlockState p_51956_, LevelAccessor p_51957_, BlockPos p_51958_) {
        int i = p_51956_.getValue(LEVEL);
        if (i == 8) {
            return new OilExtractorBlock.OutputContainer(p_51956_, p_51957_, p_51958_, new ItemStack(ItemAndBlockRegister.vegetable_oil.get()));
        } else {
            return (WorldlyContainer)(i < 7 ? new OilExtractorBlock.InputContainer(p_51956_, p_51957_, p_51958_) : new OilExtractorBlock.EmptyContainer());
        }
    }
    static class EmptyContainer extends SimpleContainer implements WorldlyContainer {
        public EmptyContainer() {
            super(0);
        }

        public int[] getSlotsForFace(Direction p_52012_) {
            return new int[0];
        }

        public boolean canPlaceItemThroughFace(int p_52008_, ItemStack p_52009_, @Nullable Direction p_52010_) {
            return false;
        }

        public boolean canTakeItemThroughFace(int p_52014_, ItemStack p_52015_, Direction p_52016_) {
            return false;
        }
    }

    static class InputContainer extends SimpleContainer implements WorldlyContainer {
        private final BlockState state;
        private final LevelAccessor level;
        private final BlockPos pos;
        private boolean changed;

        public InputContainer(BlockState p_52022_, LevelAccessor p_52023_, BlockPos p_52024_) {
            super(1);
            this.state = p_52022_;
            this.level = p_52023_;
            this.pos = p_52024_;
        }

        public int getMaxStackSize() {
            return 1;
        }

        public int[] getSlotsForFace(Direction p_52032_) {
            return p_52032_ == Direction.UP ? new int[]{0} : new int[0];
        }

        public boolean canPlaceItemThroughFace(int p_52028_, ItemStack p_52029_, @Nullable Direction p_52030_) {
            return !this.changed && p_52030_ == Direction.UP && p_52029_.is(TagUrushi.OIL_EXTRACTOR_INSERTALE);
        }

        public boolean canTakeItemThroughFace(int p_52034_, ItemStack p_52035_, Direction p_52036_) {
            return false;
        }

        public void setChanged() {
            ItemStack itemstack = this.getItem(0);
            if (!itemstack.isEmpty()) {
                this.changed = true;
                BlockState blockstate = OilExtractorBlock.addItem(this.state, this.level, this.pos, itemstack);
                this.level.levelEvent(1500, this.pos, blockstate != this.state ? 1 : 0);
                this.removeItemNoUpdate(0);
            }

        }
    }

    static class OutputContainer extends SimpleContainer implements WorldlyContainer {
        private final BlockState state;
        private final LevelAccessor level;
        private final BlockPos pos;
        private boolean changed;

        public OutputContainer(BlockState p_52042_, LevelAccessor p_52043_, BlockPos p_52044_, ItemStack p_52045_) {
            super(p_52045_);
            this.state = p_52042_;
            this.level = p_52043_;
            this.pos = p_52044_;
        }

        public int getMaxStackSize() {
            return 1;
        }

        public int[] getSlotsForFace(Direction p_52053_) {
            return p_52053_ == Direction.DOWN ? new int[]{0} : new int[0];
        }

        public boolean canPlaceItemThroughFace(int p_52049_, ItemStack p_52050_, @Nullable Direction p_52051_) {
            return false;
        }

        public boolean canTakeItemThroughFace(int p_52055_, ItemStack p_52056_, Direction p_52057_) {
            return !this.changed && p_52057_ == Direction.DOWN && p_52056_.is(ItemAndBlockRegister.vegetable_oil.get());
        }

        public void setChanged() {
            OilExtractorBlock.empty(this.state, this.level, this.pos);
            this.changed = true;
        }

    }
    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        UrushiUtils.setInfo(list,"slot_oil_extractor");
    }
}
