package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.TagUrushi;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class FermentationBarrelBlock extends Block implements WorldlyContainerHolder {

    public static final IntegerProperty LEVEL =IntegerProperty.create("level", 0, 16);
    public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 3);
    private static final VoxelShape BOX_A = Block.box(0D, 0.0D, 0D, 16D, 16D, 2D);
    private static final VoxelShape BOX_B = Block.box(0D, 0.0D, 14D, 16D, 16D, 16D);
    private static final VoxelShape BOX_C = Block.box(0D, 0.0D, 0D, 2D, 16D, 16D);
    private static final VoxelShape BOX_D = Block.box(14D, 0.0D, 0D, 16D, 16D, 16D);
    private static final VoxelShape BOX_BASE = Block.box(0D, 1.0D, 0D, 16D, 3D, 16D);



    public FermentationBarrelBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0).setValue(TYPE,0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return Shapes.or(BOX_BASE,BOX_A,BOX_B,BOX_C,BOX_D);
    }
    public void onPlace(BlockState p_51978_, Level p_51979_, BlockPos p_51980_, BlockState p_51981_, boolean p_51982_) {
        if (p_51978_.getValue(LEVEL) == 8) {
            p_51979_.scheduleTick(p_51980_, p_51978_.getBlock(), 20*10);
        }

    }
    public void tick(BlockState p_51935_, ServerLevel p_51936_, BlockPos p_51937_, RandomSource p_51938_) {
        if (p_51935_.getValue(LEVEL) == 8) {
            p_51936_.setBlock(p_51937_, p_51935_.cycle(LEVEL), 3);
            p_51936_.playSound(null, p_51937_, SoundEvents.HONEY_BLOCK_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }
    public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
        return true;
    }

    public int getAnalogOutputSignal(BlockState p_180641_1_, Level p_180641_2_, BlockPos p_180641_3_) {
        return p_180641_1_.getValue(LEVEL);
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51965_) {
        p_51965_.add(LEVEL,TYPE);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
        if(randomSource.nextInt(5)==0&&state.getValue(LEVEL)>8) {
            double d0 = (double) pos.getX() + 0.1D * randomSource.nextInt(11);
            double d1 = (double) pos.getY() + 1.0D;
            double d2 = (double) pos.getZ() + 0.1D * randomSource.nextInt(11);
            level.addParticle(ParticleTypes.EFFECT, d0, d1, d2, 0.0D, 0D, 0.0D);
        }
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        int i = state.getValue(LEVEL);
        Type type=Type.getType(state.getValue(TYPE));
        ItemStack itemstack = player.getItemInHand(hand);
        if(type==Type.MOROMI&&i>8&&itemstack.is(Items.GLASS_BOTTLE)){
            level.setBlock(pos,state.setValue(LEVEL,0),3);
            ItemStack sakeStack=new ItemStack(ItemAndBlockRegister.sake.get());
            if (!player.getInventory().add(sakeStack)) {
                player.drop(sakeStack, false);
            }
            itemstack.shrink(1);
            level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }

        if (i >=9) {
            if (type!=Type.MOROMI) {
                extractProduce(state, level, pos);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            return InteractionResult.FAIL;
        }

        boolean flag=false;
        if(itemstack.is(ItemAndBlockRegister.rice.get())&&(i==0||type==Type.RICE_MALT||type==Type.MOROMI)){
            if(type==Type.RICE_MALT) {
                flag=true;
            }else{
                flag = switch (i) {
                    case 0, 2, 3, 5 -> true;
                    default -> false;
                };
            }
        }else if(itemstack.is(ItemAndBlockRegister.shikomi_miso.get())&&(i==0||type==Type.MISO)){
            flag=true;
        }else if(itemstack.is(ItemAndBlockRegister.rice_malt.get())&&(i==0||type==Type.MOROMI||(type==Type.RICE_MALT&&i==1))){
            flag = switch (i) {
                case 0, 1, 3, 6 -> true;
                default -> false;
            };
        }else if(itemstack.is(ItemAndBlockRegister.water_bamboo_cup.get())&&(i==0||type==Type.MOROMI||(type==Type.RICE_MALT&&i==1))){
            flag = switch (i) {
                case 0, 1, 2, 4 -> true;
                default -> false;
            };

        }
        if (i < 8 && !level.isClientSide&&flag) {
                BlockState blockstate = addItem(state, level, pos, itemstack);
            level.playSound(null, pos, SoundEvents.HONEY_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

            //  level.levelEvent(1500, pos, state != blockstate ? 1 : 0);
            player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);

            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);


    }
    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
            UrushiUtils.setInfo(list,"slot_oil_extractor");
        UrushiUtils.setInfo(list, "same_as_composter");
        UrushiUtils.setInfo(list, "fermentation_barrel_1");
        UrushiUtils.setInfo(list, "fermentation_barrel_2");

    }
    public static ItemStack getFilledStack(int level,Type type){
        if(type==Type.NONE||level==0){
            return ItemStack.EMPTY;
        }else if(type==Type.RICE_MALT){
            return level>8?
                new ItemStack(ItemAndBlockRegister.rice_malt.get(),9-(level-8))
                : new ItemStack(ItemAndBlockRegister.rice.get(),level);
        }else if(type==Type.MISO){
            return level>8?
                new ItemStack(ItemAndBlockRegister.miso.get(),9-(level-8))
                : new ItemStack(ItemAndBlockRegister.shikomi_miso.get(),level);
        }
        return ItemStack.EMPTY;
    }

    public static BlockState extractProduce(BlockState state, Level p_52000_, BlockPos p_52001_) {
        if (!p_52000_.isClientSide&&state.getBlock() instanceof FermentationBarrelBlock) {
           double d0 = (double)(p_52000_.random.nextFloat() * 0.7F) + (double)0.15F;
            double d1 = (double)(p_52000_.random.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
            double d2 = (double)(p_52000_.random.nextFloat() * 0.7F) + (double)0.15F;
            ItemEntity itementity = new ItemEntity(p_52000_, (double)p_52001_.getX() + d0, (double)p_52001_.getY() + d1, (double)p_52001_.getZ() + d2, FermentationBarrelBlock.getFilledStack(state.getValue(LEVEL),FermentationBarrelBlock.Type.getType(state.getValue(TYPE))));
            itementity.setDefaultPickUpDelay();
            p_52000_.addFreshEntity(itementity);
        }

        BlockState blockstate = empty(state, p_52000_, p_52001_);
        p_52000_.playSound(null, p_52001_, SoundEvents.HONEY_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
        return blockstate;
    }
    static BlockState empty(BlockState p_52003_, LevelAccessor p_52004_, BlockPos p_52005_) {
        BlockState blockstate = p_52003_.setValue(LEVEL, 0);
        p_52004_.setBlock(p_52005_, blockstate, 3);
        return blockstate;
    }

    static BlockState addItem(BlockState state, LevelAccessor levelAccessor, BlockPos pos, ItemStack stack) {
        int currentLevel = state.getValue(LEVEL);
        Type currentType = Type.getType(state.getValue(TYPE));

        if (currentLevel >= 9) {
            return state;
        }

        Type newType = currentType;
        boolean isValid = false;
        int newLevel = currentLevel;

        if (stack.is(ItemAndBlockRegister.rice.get()) && isValidForRice(currentLevel, currentType)) {
            if (currentType == Type.RICE_MALT) {
                isValid = true;
                newLevel = currentLevel + 1;
                // newType = Type.RICE_MALT;
            } else {
                switch (currentLevel) {
                    case 0 -> { newLevel = 1; newType = Type.RICE_MALT; isValid = true; }
                    case 1 -> { newLevel = 2; newType = Type.RICE_MALT; isValid = true; }
                    case 2 -> { newLevel = 4; newType = Type.MOROMI; isValid = true; }
                    case 3 -> { newLevel = 6; newType = Type.MOROMI; isValid = true; }
                    case 5 -> { newLevel = 8; newType = Type.MOROMI; isValid = true; }
                }
            }
        } else if (stack.is(ItemAndBlockRegister.shikomi_miso.get()) && (currentLevel == 0 || currentType == Type.MISO)) {
            newType = Type.MISO;
            newLevel = currentLevel + 1;
            isValid = true;
        } else if (stack.is(ItemAndBlockRegister.rice_malt.get()) && isValidForRiceMalt(currentLevel, currentType)) {
            newType = Type.MOROMI;
            switch (currentLevel) {
                case 0 -> { newLevel = 2; isValid = true; }
                case 1 -> { newLevel = 4; isValid = true; }
                case 3 -> { newLevel = 5; isValid = true; }
                case 6 -> { newLevel = 8; isValid = true; }
            }
        } else if (stack.is(ItemAndBlockRegister.water_bamboo_cup.get()) && isValidForWaterBambooCup(currentLevel, currentType)) {
            newType = Type.MOROMI;
            switch (currentLevel) {
                case 0 -> { newLevel = 3; isValid = true; }
                case 2 -> { newLevel = 5; isValid = true; }
                case 1 -> { newLevel = 6; isValid = true; }
                case 4 -> { newLevel = 8; isValid = true; }
            }
        }

        if (isValid) {
            BlockState newState = state.setValue(LEVEL, newLevel).setValue(TYPE, newType.getID());
            levelAccessor.setBlock(pos, newState, 3);

            if (newLevel == 8) {
                levelAccessor.scheduleTick(pos, state.getBlock(), 20);
            }

            return newState;
        }

        return state;
    }

    private static boolean isValidForRice(int level, Type type) {
        return level == 0 || type == Type.RICE_MALT || type == Type.MOROMI;
    }

    private static boolean isValidForRiceMalt(int level, Type type) {
        return level == 0 || type == Type.MOROMI || (type == Type.RICE_MALT && level == 1);
    }

    private static boolean isValidForWaterBambooCup(int level, Type type) {
        return level == 0 || type == Type.MOROMI || (type == Type.RICE_MALT && level == 1);
    }

    public WorldlyContainer getContainer(BlockState state, LevelAccessor p_51957_, BlockPos p_51958_) {
        int i = state.getValue(LEVEL);
        if (i >8) {
            return new OutputContainer(state, p_51957_, p_51958_, new ItemStack(getFilledStack(i,Type.getType(state.getValue(TYPE))).getItem()));
        }

        return i < 8 ? new InputContainer(state, p_51957_, p_51958_) : new EmptyContainer();
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

        public boolean canPlaceItemThroughFace(int p_52028_, ItemStack stack, @Nullable Direction direction) {
            int stage=state.getValue(LEVEL);
            Type type=Type.getType(state.getValue(TYPE));
            if(!this.changed && direction == Direction.UP){
                Item item = stack.getItem();
                if(stage==0){
                    return item == ItemAndBlockRegister.rice.get() || item == ItemAndBlockRegister.shikomi_miso.get()
                        || item == ItemAndBlockRegister.rice_malt.get() || item == ItemAndBlockRegister.water_bamboo_cup.get();
                }else if(stage<8){
                    if(type==Type.MOROMI||(type==Type.RICE_MALT&&stage==1)){
                        switch (stage){
                            case 1 :return item==ItemAndBlockRegister.rice.get()
                                        || item==ItemAndBlockRegister.rice_malt.get()
                                        || item==ItemAndBlockRegister.water_bamboo_cup.get();

                            case 2 :return item==ItemAndBlockRegister.rice.get()
                                        || item==ItemAndBlockRegister.water_bamboo_cup.get();

                            case 3 :return item==ItemAndBlockRegister.rice.get()
                                        || item==ItemAndBlockRegister.rice_malt.get();

                            case 4 :return item==ItemAndBlockRegister.water_bamboo_cup.get();
                            case 5 :return item==ItemAndBlockRegister.rice.get();
                            case 6 :return item==ItemAndBlockRegister.rice_malt.get();
                        }
                    }else if(type==Type.RICE_MALT){
                        return item==ItemAndBlockRegister.rice.get();
                    }else if(type==Type.MISO){
                        return item==ItemAndBlockRegister.shikomi_miso.get();
                    }
                }
            }
      return false;
        }

        public boolean canTakeItemThroughFace(int p_52034_, ItemStack p_52035_, Direction p_52036_) {
            return false;
        }

        public void setChanged() {
            ItemStack itemstack = this.getItem(0);
            if (!itemstack.isEmpty()) {
                this.changed = true;
                BlockState blockstate =FermentationBarrelBlock.addItem(this.state, this.level, this.pos, itemstack);
                //this.level.levelEvent(1500, this.pos, blockstate != this.state ? 1 : 0);
                level.playSound(null, pos, SoundEvents.HONEY_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

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

        public boolean canTakeItemThroughFace(int p_52055_, ItemStack stack, Direction p_52057_) {
            return !this.changed && p_52057_ == Direction.DOWN && Type.getType(state.getValue(TYPE))!=Type.MOROMI&& (stack.is(ItemAndBlockRegister.rice_malt.get())||stack.is(ItemAndBlockRegister.miso.get()));
        }
        public void setChanged() {
            if (Type.getType(state.getValue(TYPE)) != Type.MOROMI) {
                this.changed = true;
                BlockState blockstate = this.state.cycle(LEVEL);
                level.setBlock(pos, blockstate, 3);
                //this.level.levelEvent(1500, this.pos, blockstate != this.state ? 1 : 0);
                level.playSound(null, pos, SoundEvents.HONEY_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

            }
        }

    }
    public  enum Type {
        NONE(0),
        RICE_MALT(1),
        MISO(2),
        MOROMI(3);

        private int id;

        private Type(int id) {
            this.id = id;
        }
        public static Type getType(int id){
            return switch (id) {
                case 1 -> RICE_MALT;
                case 2 -> MISO;
                case 3 -> MOROMI;
                default -> NONE;
            };

        }
        public int getID()
        {
            return this.id;
        }
    }
}
