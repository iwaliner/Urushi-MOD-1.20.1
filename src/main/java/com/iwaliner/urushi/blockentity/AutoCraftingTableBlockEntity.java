package com.iwaliner.urushi.blockentity;



import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.MenuRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.block.AutoCraftingTableBlock;
import com.iwaliner.urushi.block.FoxHopperBlock;
import com.iwaliner.urushi.blockentity.menu.AutoCraftingTableMenu;
import com.iwaliner.urushi.util.UrushiUtils;
import com.mojang.authlib.GameProfile;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.stream.IntStream;

public class AutoCraftingTableBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible, RecipeHolder, MenuProvider {
    public int litTime;
    public String savedRecipe;
    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int p_58431_) {
            switch(p_58431_) {
                case 0:
                    return AutoCraftingTableBlockEntity.this.litTime;


                default:
                    return 0;
            }
        }

        public void set(int p_58433_, int p_58434_) {
            switch(p_58433_) {
                case 0:
                    AutoCraftingTableBlockEntity.this.litTime = p_58434_;
                    break;



            }

        }

        public int getCount() {
            return 1;
        }
    };
    private static final int[] INGREDIENT_SLOTS = new int[]{11,12,13,14,15,16,17,18,19};
    private static final int[] RESULT_SLOT = new int[]{10};
    private final ItemStackHandler ingredients = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }


        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            //ここでは、ホッパーなどでスロットに搬入できるかどうかを決める。
            ItemStack stack1=getIngredientsSample().getStackInSlot(slot);
            return ItemStack.isSameItemSameTags(stack, stack1)&&getIngredients().getStackInSlot(slot).isEmpty();   }

        @Override
        public int getSlots() {
            return super.getSlots();
        }
    };
    private final ItemStackHandler result = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return false;
        }


    };
    public final ItemStackHandler ingredientsSample = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return false;
        }
    };
    private final ItemStackHandler resultSample = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return false;
        }

    };
    private LazyOptional<ItemStackHandler> resultOptional = LazyOptional.empty();

    private LazyOptional<ItemStackHandler> ingredientsOptional = LazyOptional.empty();
    private LazyOptional<ItemStackHandler> resultSampleOptional = LazyOptional.empty();

    private LazyOptional<ItemStackHandler> ingredientsSampleOptional = LazyOptional.empty();
    private final NonNullList<ItemStack> slotList = NonNullList.of(resultSample.getStackInSlot(0),
        ingredientsSample.getStackInSlot(0),ingredientsSample.getStackInSlot(1),ingredientsSample.getStackInSlot(2),
        ingredientsSample.getStackInSlot(3),ingredientsSample.getStackInSlot(4),ingredientsSample.getStackInSlot(5),
        ingredientsSample.getStackInSlot(6),ingredientsSample.getStackInSlot(7),ingredientsSample.getStackInSlot(8),
        result.getStackInSlot(0),
        ingredients.getStackInSlot(0),ingredients.getStackInSlot(1),ingredients.getStackInSlot(2),
        ingredients.getStackInSlot(3),ingredients.getStackInSlot(4),ingredients.getStackInSlot(5),
        ingredients.getStackInSlot(6),ingredients.getStackInSlot(7),ingredients.getStackInSlot(8));
    protected final RecipeType<? extends CraftingRecipe> recipeType;
    private int processTime;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();

    public AutoCraftingTableBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.AutoCraftingTable.get(), p_155052_, p_155053_);
        this.recipeType = RecipeType.CRAFTING;
    }

    public LazyOptional<ItemStackHandler> getIngredientsOptional() {
        return ingredientsOptional;
    }

    public LazyOptional<ItemStackHandler> getIngredientsSampleOptional() {
        return ingredientsSampleOptional;
    }

    public LazyOptional<ItemStackHandler> getResultOptional() {
        return resultOptional;
    }

    public LazyOptional<ItemStackHandler> getResultSampleOptional() {
        return resultSampleOptional;
    } public ItemStackHandler getIngredientsSample() {
        return ingredientsSample;
    }

    public ItemStackHandler getIngredients() {
        return ingredients;
    }

    public ItemStackHandler getResultSample() {
        return resultSample;
    }

    public ItemStackHandler getResult() {
        return result;
    }


    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {


        if(slot<11){
            return false;
        }else{
            if(ItemStack.isSameItemSameTags(this.getItem(slot-10), stack)){

                return this.getItem(slot).isEmpty();

            }
        }
        return false;
    }
    @Override
    public void onLoad() {
        super.onLoad();
        resultOptional = LazyOptional.of(() -> result);
        ingredientsOptional = LazyOptional.of(() -> ingredients);
        resultSampleOptional = LazyOptional.of(() -> resultSample);
        ingredientsSampleOptional = LazyOptional.of(() -> ingredientsSample);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        CompoundTag data = tag.getCompound(ModCoreUrushi.ModID);
        if(data.contains("ingredients", Tag.TAG_COMPOUND)) {
            this.ingredients.deserializeNBT(data.getCompound("ingredients"));
        }
        if(data.contains("result", Tag.TAG_COMPOUND)) {
            this.result.deserializeNBT(data.getCompound("result"));
        }
        if(data.contains("ingredientsSample", Tag.TAG_COMPOUND)) {
            this.ingredientsSample.deserializeNBT(data.getCompound("ingredientsSample"));
        }
        if(data.contains("resultSample", Tag.TAG_COMPOUND)) {
            this.resultSample.deserializeNBT(data.getCompound("resultSample"));
        }
        this.litTime = tag.getInt("BurnTime");
        CompoundTag compoundtag = tag.getCompound("RecipesUsed");

        for(String s : compoundtag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(s), compoundtag.getInt(s));
        }
        this.savedRecipe=tag.getString("savedRecipe");
    }

    protected void saveAdditional(CompoundTag p_187452_) {
        super.saveAdditional(p_187452_);
        var data=new CompoundTag();
        data.put("ingredients",this.ingredients.serializeNBT());
        data.put("result",this.result.serializeNBT());
        data.put("ingredientsSample",this.ingredientsSample.serializeNBT());
        data.put("resultSample",this.resultSample.serializeNBT());
        p_187452_.put(ModCoreUrushi.ModID,data);
        p_187452_.putInt("BurnTime", this.litTime);

        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((p_187449_, p_187450_) -> {
            compoundtag.putInt(p_187449_.toString(), p_187450_);
        });
        p_187452_.put("RecipesUsed", compoundtag);
        if(savedRecipe!=null) {
            p_187452_.putString("savedRecipe", this.savedRecipe);
        }
    }
    public boolean isLit() {
        return this.litTime > 0;
    }
    private void doCraft(Level level,ItemStack itemstack,AutoCraftingTableBlockEntity blockEntity){
        Direction facing=level.getBlockState(blockEntity.getBlockPos()).getValue(AutoCraftingTableBlock.FACING);
        BlockPos pos2=blockEntity.getBlockPos().relative(facing);
        for (int i = 11; i < 20; i++) {
            ItemStack slotStack=blockEntity.getItem(i);
            if(slotStack.hasCraftingRemainingItem()){
                if (level.getBlockState(blockEntity.getBlockPos()).getBlock()
                    instanceof AutoCraftingTableBlock && level.getBlockEntity(pos2) instanceof BaseContainerBlockEntity) {
                    BaseContainerBlockEntity baseContainerBlockEntity = (BaseContainerBlockEntity) level.getBlockEntity(
                        blockEntity.getBlockPos().relative(
                            level.getBlockState(blockEntity.getBlockPos()).getValue(AutoCraftingTableBlock.FACING)));
                    if (isExportable(blockEntity, baseContainerBlockEntity, slotStack.getCraftingRemainingItem(), facing.getOpposite())) {
                        addItem(blockEntity, baseContainerBlockEntity, slotStack.getCraftingRemainingItem(), facing.getOpposite());
                        blockEntity.getItem(10).shrink(1);
                    }else{
                        ItemEntity itemEntity=new ItemEntity(level,(double) pos2.getX()+0.5D,(double) pos2.getY()+0.5D,pos2.getZ()+0.5D,slotStack.getCraftingRemainingItem());
                        if(!level.isClientSide){
                            level.addFreshEntity(itemEntity);
                        }
                    }
                }else {
                    ItemEntity itemEntity = new ItemEntity(level, (double) pos2.getX() + 0.5D, (double) pos2.getY() + 0.5D, pos2.getZ() + 0.5D, slotStack.getCraftingRemainingItem());
                    if (!level.isClientSide) {
                        level.addFreshEntity(itemEntity);
                    }
                }
            }
                slotStack.shrink(1);
        }

        if (ItemStack.isSameItemSameTags(blockEntity.getItem(0), blockEntity.getItem(10))) {
            ItemStack newStack = itemstack.copy();
            newStack.setCount(itemstack.getCount() + blockEntity.getItem(10).getCount());
           if(level.getBlockState(blockEntity.getBlockPos()).getBlock() instanceof AutoCraftingTableBlock&&!level.isEmptyBlock(pos2)){
               blockEntity.setItem(10, newStack);
           }else{
               ItemEntity itemEntity=new ItemEntity(level,(double) pos2.getX()+0.5D,(double) pos2.getY()+0.5D,pos2.getZ()+0.5D,newStack);
               if(!level.isClientSide){
                   level.addFreshEntity(itemEntity);
               }
           }
            blockEntity.setChanged();

        }
        else if (blockEntity.getItem(10).isEmpty()) {

            if(level.getBlockState(blockEntity.getBlockPos()).getBlock() instanceof AutoCraftingTableBlock&&!level.isEmptyBlock(pos2)){
                blockEntity.setItem(10, itemstack);
            }else{
                ItemEntity itemEntity=new ItemEntity(level,(double) pos2.getX()+0.5D,(double) pos2.getY()+0.5D,pos2.getZ()+0.5D,itemstack);
                if(!level.isClientSide){
                    level.addFreshEntity(itemEntity);
                }
            }


            blockEntity.setChanged();
        }
    }
    @Nullable
    private static Container getAttachedContainer(Level p_155593_, BlockPos p_155594_, BlockState p_155595_) {
        Direction direction = p_155595_.getValue(AutoCraftingTableBlock.FACING);
        return FoxHopperBlockEntity.getContainerAt(p_155593_, p_155594_.relative(direction));
    }
    private static boolean ejectItems(Level p_155563_, BlockPos p_155564_, BlockState p_155565_, AutoCraftingTableBlockEntity p_155566_) {

        Container container = getAttachedContainer(p_155563_, p_155564_, p_155565_);
        if (container == null) {
            return false;
        }
        Direction direction = p_155565_.getValue(AutoCraftingTableBlock.FACING).getOpposite();
        if (isFullContainer(container, direction)) {
            return false;
        }

        if (!p_155566_.getItem(10).isEmpty()) {
            ItemStack itemstack = p_155566_.getItem(10).copy();
            ItemStack itemstack1 = addItem(p_155566_, container, p_155566_.removeItem(10, 1), direction);
            if (itemstack1.isEmpty()) {
                container.setChanged();
                return true;
            }

            p_155566_.setItem(10, itemstack);
        }


        return false;


    }
    private static ItemStack tryMoveInItem(@Nullable Container p_59321_, Container container, ItemStack p_59323_, int p_59324_, @Nullable Direction p_59325_) {
        ItemStack itemstack = container.getItem(p_59324_);
        if (FoxHopperBlockEntity.canPlaceItemInContainer(container, p_59323_, p_59324_, p_59325_)) {
            boolean flag = false;
            boolean flag1 = container.isEmpty();
            if (itemstack.isEmpty()) {
                container.setItem(p_59324_, p_59323_);
                p_59323_ = ItemStack.EMPTY;
                flag = true;
            } else if (FoxHopperBlockEntity.canMergeItems(itemstack, p_59323_)) {
                int i = p_59323_.getMaxStackSize() - itemstack.getCount();
                int j = Math.min(p_59323_.getCount(), i);
                p_59323_.shrink(j);
                itemstack.grow(j);
                flag = j > 0;
            }

            if (flag) {
                container.setChanged();
            }
        }

        return p_59323_;
    }
    public static ItemStack addItem(@Nullable Container p_59327_, Container p_59328_, ItemStack p_59329_, @Nullable Direction p_59330_) {
        if (p_59328_ instanceof WorldlyContainer && p_59330_ != null) {
            WorldlyContainer worldlycontainer = (WorldlyContainer)p_59328_;
            int[] aint = worldlycontainer.getSlotsForFace(p_59330_);

            for(int k = 0; k < aint.length && !p_59329_.isEmpty(); ++k) {
                p_59329_ = tryMoveInItem(p_59327_, p_59328_, p_59329_, aint[k], p_59330_);
            }
        } else {
            int i = p_59328_.getContainerSize();

            for(int j = 0; j < i && !p_59329_.isEmpty(); ++j) {
                p_59329_ = tryMoveInItem(p_59327_, p_59328_, p_59329_, j, p_59330_);
            }
        }

        return p_59329_;
    }
    private static IntStream getSlots(Container p_59340_, Direction p_59341_) {
        return p_59340_ instanceof WorldlyContainer ? IntStream.of(((WorldlyContainer)p_59340_).getSlotsForFace(p_59341_)) : IntStream.range(0, p_59340_.getContainerSize());
    }

    public static boolean isFullContainer(Container p_59386_, Direction p_59387_) {
        return getSlots(p_59386_, p_59387_).allMatch((p_59379_) -> {
            ItemStack itemstack = p_59386_.getItem(p_59379_);
            return itemstack.getCount() >= itemstack.getMaxStackSize();
        });
    }

    private static boolean canInsertIntoSlot(Container container, ItemStack resultStack,
                                             int slot, Direction direction) {
        if (!FoxHopperBlockEntity.canPlaceItemInContainer(container, resultStack, slot, direction)) {
            return false;
        }

        ItemStack existingStack = container.getItem(slot);

        if (existingStack.isEmpty()) {
            return true;
        }

        if (FoxHopperBlockEntity.canMergeItems(existingStack, resultStack)) {
            int availableSpace = existingStack.getMaxStackSize() - existingStack.getCount();
            return availableSpace >= resultStack.getCount();
        }

        return false;
    }

    public static boolean isExportable(AutoCraftingTableBlockEntity autoCratingTable,BaseContainerBlockEntity baseContainer,ItemStack craftResultStack,Direction direction) {
        if (baseContainer instanceof WorldlyContainer worldlycontainer && direction != null) {
            int[] slots = worldlycontainer.getSlotsForFace(direction);
            for (int i : slots) {
                ItemStack itemstack = worldlycontainer.getItem(i);
                if(canInsertIntoSlot(worldlycontainer, itemstack, i, direction)){
                    return true;
                }
            }
        } else {
            int containerSize = baseContainer.getContainerSize();

            for(int i = 0; i < containerSize ; ++i) {
                ItemStack itemstack = baseContainer.getItem(i);
                if(canInsertIntoSlot(baseContainer, itemstack, i, direction)){
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean canTakeItemFromContainer(Container p_59381_, ItemStack p_59382_, int p_59383_, Direction p_59384_) {
        return !(p_59381_ instanceof WorldlyContainer) || ((WorldlyContainer)p_59381_).canTakeItemThroughFace(p_59383_, p_59382_, p_59384_);
    }

    private static CraftingRecipe findAndSaveRecipe(Level level, CraftingContainer container, AutoCraftingTableBlockEntity blockEntity) {
        CraftingRecipe recipe = level.getRecipeManager()
            .getRecipeFor(RecipeType.CRAFTING, container, level)
            .orElse(null);

        if (recipe != null) {
            blockEntity.savedRecipe = recipe.getId().toString();
        }

        return recipe;
    }

    private static boolean hasInventoryChanged(CraftingContainer craftingcontainer, AutoCraftingTableBlockEntity blockEntity) {
        for (int i = 0; i < 9; i++) {
            ItemStack containerStack = craftingcontainer.getItem(i);
            ItemStack entityStack = blockEntity.ingredientsSample.getStackInSlot(i);

            if (!ItemStack.matches(containerStack, entityStack)) {
                return true;
            }
        }
        return false;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AutoCraftingTableBlockEntity blockEntity) {
        //ejectItems(level,pos,state,blockEntity);
        Block block= state.getBlock();
        if (!(block instanceof AutoCraftingTableBlock)) {
            return;
        }
        Direction facing = state.getValue(AutoCraftingTableBlock.FACING);
        BlockPos pos2 = pos.relative(facing);
        BlockEntity baseContainerBlockEntity = level.getBlockEntity(pos2);
        if (baseContainerBlockEntity instanceof BaseContainerBlockEntity) {
            ItemStack itemStack = blockEntity.getItem(10).copy();
            itemStack.setCount(1);
            if (isExportable(blockEntity, (BaseContainerBlockEntity) baseContainerBlockEntity, itemStack, facing.getOpposite())) {
                addItem(blockEntity, (BaseContainerBlockEntity) baseContainerBlockEntity, itemStack, facing.getOpposite());
                blockEntity.getItem(10).shrink(1);
            }
        }
        if(blockEntity.isMatrixEmpty()) {
            return;
        }

        CraftingContainer craftingcontainer = new TransientCraftingContainer(new AbstractContainerMenu((MenuType) MenuRegister.AutoCraftingTableMenu.get(), -1) {
            public ItemStack quickMoveStack(Player p_218264_, int p_218265_) {
                return ItemStack.EMPTY;
            }

            public boolean stillValid(Player p_29888_) {
                return false;
            }
        }, 3, 3);
        if(hasInventoryChanged(craftingcontainer, blockEntity)) {
            for (int i = 0; i < 9; i++) {
                craftingcontainer.setItem(i, blockEntity.ingredientsSample.getStackInSlot(i));
            }
        }
        CraftingRecipe craftingrecipe = null;
        ItemStack itemstack = ItemStack.EMPTY;
        if (blockEntity.savedRecipe == null) {
            craftingrecipe = findAndSaveRecipe(level, craftingcontainer, blockEntity);
        } else {
            Optional<? extends Recipe<?>> savedRecipe = level.getRecipeManager()
                .byKey(Objects.requireNonNull(ResourceLocation.tryParse(blockEntity.savedRecipe)));

            craftingrecipe = savedRecipe
                .map(recipe -> (CraftingRecipe) recipe)
                .filter(recipe -> recipe.matches(craftingcontainer, level))
                .orElseGet(() -> findAndSaveRecipe(level, craftingcontainer, blockEntity));
        }

        if (craftingrecipe != null) {
            itemstack = craftingrecipe.assemble(craftingcontainer,level.registryAccess());

        }
        blockEntity.setItem(0, itemstack);
        if (!level.isClientSide && !blockEntity.isEmpty() && blockEntity.litTime == 0) {

            if (!itemstack.isEmpty()) {

                boolean flag = true;
                for (int i = 1; i < 10; i++) {
                    if (!blockEntity.getItem(i).getItem().equals(blockEntity.getItem(i + 10).getItem())) {
                        flag = false;
                    }

                }
                ItemStack resultSlotStack = blockEntity.getItem(10);
                if (flag && resultSlotStack.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize()) {
                    //Player player=new FakePlayer((ServerLevel) level, new GameProfile(null,"urushiautocrafting"));
                    if (block == ItemAndBlockRegister.auto_crafting_table.get()) {

                        if (blockEntity.litTime == 0 && resultSlotStack.isEmpty()) {

                            net.minecraftforge.event.ForgeEventFactory.firePlayerCraftingEvent(null, itemstack, craftingcontainer);
                            blockEntity.doCraft(level, itemstack, blockEntity);
                            blockEntity.litTime = 60;
                        }
                    } else if (block == ItemAndBlockRegister.advanced_auto_crafting_table.get() && resultSlotStack.isEmpty()) {
                        net.minecraftforge.event.ForgeEventFactory.firePlayerCraftingEvent(null, itemstack, craftingcontainer);
                        blockEntity.doCraft(level, itemstack, blockEntity);
                        blockEntity.litTime = 0;
                    }
                }

            }
        }
        if (blockEntity.isLit() && block == ItemAndBlockRegister.auto_crafting_table.get()) {
            --blockEntity.litTime;
        }


    }


    protected Component getDefaultName() {
        return Component.translatable("container.urushi_auto_crafting_table");
    }



    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new AutoCraftingTableMenu(i, inventory,this);
    }


    @Override
    public int getContainerSize() {
        return 20;
    }

    @Override
    public boolean isEmpty() {
        for(int i=0;i<9;i++) {
            if (!this.ingredients.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return this.result.getStackInSlot(0).isEmpty();
    }

    public boolean isMatrixEmpty() {
        for(int i=0;i<9;i++) {
            if (!this.ingredientsSample.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return this.resultSample.getStackInSlot(0).isEmpty();
    }

    public ItemStack getItem(int slot) {
        if(slot==0){
            return this.resultSample.getStackInSlot(0);
        }else if(slot<10){
            return this.ingredientsSample.getStackInSlot(slot-1);
        }else if(slot==10){
            return this.result.getStackInSlot(0);
        }else{
            return this.ingredients.getStackInSlot(slot-11);
        }

    }

    public ItemStack removeItemNoUpdate(int slot) {

        return ContainerHelper.takeItem(this.slotList, slot);
    }

    public ItemStack removeItem(int slot, int amount) {

        return ContainerHelper.removeItem(this.slotList, slot,amount);
    }

    public void setItem(int slot, ItemStack stack) {

        ItemStack itemstack = this.getItem(slot);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameTags(stack, itemstack);
        if(slot==0){
            this.resultSample.setStackInSlot(0,stack.copy());
        }else if(slot<10){
            this.ingredientsSample.setStackInSlot(slot-1,stack.copy());
        }else if(slot==10){
            this.result.setStackInSlot(0,stack);
        }else{

            this.ingredients.setStackInSlot(slot-11,stack);

        }
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (slot != 0 && !flag) {
            this.setChanged();
        }

    }

    public void setChanged() {
        level.blockUpdated(this.worldPosition,this.getBlockState().getBlock());
    }

    public boolean stillValid(Player p_70300_1_) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return p_70300_1_.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }



    public void clearContent() {

    }



    public void setRecipeUsed(@Nullable Recipe<?> p_193056_1_) {
        if (p_193056_1_ != null) {
            ResourceLocation resourcelocation = p_193056_1_.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }
    }
    @Nullable
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    public void awardUsedRecipes(Player p_201560_1_) {
    }
    public void fillStackedContents(StackedContents p_194018_1_) {
        for(int i=0;i<9;i++) {
            p_194018_1_.accountStack(this.ingredients.getStackInSlot(i));
        }
        p_194018_1_.accountStack(this.result.getStackInSlot(0));
        for(int i=0;i<9;i++) {
            p_194018_1_.accountStack(this.ingredientsSample.getStackInSlot(i));
        }
        p_194018_1_.accountStack(this.resultSample.getStackInSlot(0));
    }


    private Direction getExportFacing(){
        if(this.getBlockState().getBlock() instanceof AutoCraftingTableBlock){
            return this.getBlockState().getValue(BlockStateProperties.FACING);
        }
        return Direction.DOWN;
    }
    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if(capability == ForgeCapabilities.ITEM_HANDLER) {
            if(facing == this.getExportFacing())
                return this.resultOptional.cast();


            return this.ingredientsOptional.cast();
        }

        return super.getCapability(capability, facing);
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.ingredientsOptional.invalidate();
        this.resultOptional.invalidate();
        this.ingredientsSampleOptional.invalidate();
        this.resultSampleOptional.invalidate();
    }




    @Override
    public int @NotNull [] getSlotsForFace(Direction direction) {

        return direction==getExportFacing()? RESULT_SLOT : INGREDIENT_SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @org.jetbrains.annotations.Nullable Direction direction) {
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {

        return  slot==10;

    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

}