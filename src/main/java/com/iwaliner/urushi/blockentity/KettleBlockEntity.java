package com.iwaliner.urushi.blockentity;


import com.google.common.collect.Lists;
import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.MenuRegister;
import com.iwaliner.urushi.RecipeTypeRegister;
import com.iwaliner.urushi.block.HibachiBlock;
import com.iwaliner.urushi.blockentity.menu.KettleMenu;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class KettleBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    public int litTime;
    public int litDuration;
    public int cookingProgress;
    public int cookingTotalTime;
    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int p_58431_) {
            switch(p_58431_) {
                case 0:
                    return KettleBlockEntity.this.litTime;
                case 1:
                    return KettleBlockEntity.this.litDuration;
                case 2:
                    return KettleBlockEntity.this.cookingProgress;
                case 3:
                    return KettleBlockEntity.this.cookingTotalTime;
                default:
                    return 0;
            }
        }

        public void set(int p_58433_, int p_58434_) {
            switch(p_58433_) {
                case 0:
                    KettleBlockEntity.this.litTime = p_58434_;
                    break;
                case 1:
                    KettleBlockEntity.this.litDuration = p_58434_;
                    break;
                case 2:
                    KettleBlockEntity.this.cookingProgress = p_58434_;
                    break;
                case 3:
                    KettleBlockEntity.this.cookingTotalTime = p_58434_;
            }

        }

        public int getCount() {
            return 4;
        }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();

    public KettleBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.Kettle.get(), p_155052_,p_155053_);
    }
    protected Component getDefaultName() {
        return Component.translatable("container.urushi.kettle");
    }


    protected AbstractContainerMenu createMenu(int p_59293_, Inventory p_59294_) {
        return new KettleMenu(MenuRegister.KettleMenu.get(), RecipeTypeRegister.KettleRecipe,p_59293_, p_59294_, this, this.dataAccess);
    }

    private static void add(Map<Item, Integer> p_58375_, ItemLike p_58376_, int p_58377_) {
        Item item = p_58376_.asItem();
            p_58375_.put(item, p_58377_);

    }
    public boolean isLit() {
        return this.litTime > 0;
    }
    public void load(CompoundTag p_155025_) {
        super.load(p_155025_);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(p_155025_, this.items);
        this.litTime = p_155025_.getInt("BurnTime");
        this.cookingProgress = p_155025_.getInt("CookTime");
        this.cookingTotalTime = p_155025_.getInt("CookTimeTotal");
        this.litDuration = this.getBurnDuration();
        CompoundTag compoundtag = p_155025_.getCompound("RecipesUsed");

        for(String s : compoundtag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(s), compoundtag.getInt(s));
        }

    }

    protected void saveAdditional(CompoundTag p_187452_) {
        super.saveAdditional(p_187452_);
        p_187452_.putInt("BurnTime", this.litTime);
        p_187452_.putInt("CookTime", this.cookingProgress);
        p_187452_.putInt("CookTimeTotal", this.cookingTotalTime);
        ContainerHelper.saveAllItems(p_187452_, this.items);
        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((p_187449_, p_187450_) -> {
            compoundtag.putInt(p_187449_.toString(), p_187450_);
        });
        p_187452_.put("RecipesUsed", compoundtag);
    }
    private boolean isOnHeatSource(BlockState underState){
        if(underState.getBlock() instanceof CampfireBlock&&underState.getValue(CampfireBlock.LIT)){
            return true;
        }else if(underState.getBlock() instanceof AbstractFurnaceBlock&&underState.getValue(AbstractFurnaceBlock.LIT)){
            return true;
        }else if(underState.getBlock() instanceof HibachiBlock){
            return true;
        }
        return false;
    }
    public static void serverTick(Level level, BlockPos pos, BlockState state, KettleBlockEntity blockEntity) {
        boolean flag1 = false;
        if (blockEntity.isLit()) {
            --blockEntity.litTime;
        }

        if (!level.isClientSide&&blockEntity.isOnHeatSource(level.getBlockState(pos.below()))) {
            Recipe<?> recipe = level.getRecipeManager().getRecipeFor((RecipeTypeRegister.KettleRecipe), blockEntity, level).orElse(null);
            ItemStack creatureSlotItem = blockEntity.items.get(0);
            ItemStack feedSlotItem = blockEntity.items.get(1);
            ItemStack resultSlotItem = blockEntity.items.get(2);
            if(recipe!=null) {
                ItemStack result=recipe.getResultItem(level.registryAccess());
                if (blockEntity.isLit() || !result.isEmpty() && !creatureSlotItem.isEmpty()&&!feedSlotItem.isEmpty()) {
                    if (!blockEntity.isLit() && blockEntity.canBurn(level.registryAccess(),recipe,blockEntity.items,blockEntity.getMaxStackSize())) {
                        blockEntity.litTime = blockEntity.getBurnDuration();
                        blockEntity.litDuration = blockEntity.litTime;
                        if (blockEntity.isLit()) {
                            flag1 = true;

                        }
                    }

                    if (blockEntity.isLit() && blockEntity.canBurn(level.registryAccess(),recipe,blockEntity.items,blockEntity.getMaxStackSize())) {
                        ++blockEntity.cookingProgress;
                        if (blockEntity.cookingProgress == blockEntity.cookingTotalTime) {
                            blockEntity.cookingProgress = 0;
                            blockEntity.cookingTotalTime = blockEntity.getTotalCookTime();
                            blockEntity.burn(level.registryAccess(),recipe,blockEntity.items,blockEntity.getMaxStackSize());
                            flag1 = true;
                        }
                    } else {
                        blockEntity.cookingProgress = 0;
                    }
                } else if (!blockEntity.isLit() && blockEntity.cookingProgress > 0) {
                    blockEntity.cookingProgress = Mth.clamp(blockEntity.cookingProgress - 2, 0, blockEntity.cookingTotalTime);
                }

            }

        }

        if (flag1) {
            blockEntity.setChanged();
        }
    }

    public boolean canBurn(RegistryAccess p_266924_,@Nullable Recipe<?> p_155006_, NonNullList<ItemStack> p_155007_, int p_155008_) {
        if (!p_155007_.get(0).isEmpty() && p_155006_ != null) {
            ItemStack itemstack = ((Recipe<WorldlyContainer>) p_155006_).assemble(this,p_266924_);
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = p_155007_.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItemSameTags(itemstack, itemstack1)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= p_155008_ && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) {
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }
    public boolean burn(RegistryAccess p_266740_, @Nullable Recipe<?> p_155027_, NonNullList<ItemStack> p_155028_, int p_155029_) {
        if (p_155027_ != null && this.canBurn(p_266740_,p_155027_, p_155028_, p_155029_)) {
            ItemStack itemstack1 = ((Recipe<WorldlyContainer>) p_155027_).assemble(this,p_266740_);
            ItemStack itemstack2 = p_155028_.get(2);
            if (itemstack2.isEmpty()) {
                this.items.set(2, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (!this.level.isClientSide) {
                this.setRecipeUsed(p_155027_);
            }



            p_155028_.get(0).shrink(1);
            p_155028_.get(1).shrink(1);
            return true;
        } else {
            return false;
        }
    }
    protected int getBurnDuration() {
       return 1600;
    }
    protected int getTotalCookTime() {
        return 100;
    }
    public int[] getSlotsForFace(Direction p_180463_1_) {
        if (p_180463_1_ == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return p_180463_1_ == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }
    public boolean canPlaceItemThroughFace(int p_180462_1_, ItemStack p_180462_2_, @Nullable Direction p_180462_3_) {
        return this.canPlaceItem(p_180462_1_, p_180462_2_);
    }
    public boolean canTakeItemThroughFace(int p_180461_1_, ItemStack p_180461_2_, Direction p_180461_3_) {
        if (p_180461_3_ == Direction.DOWN && p_180461_1_ == 1) {
            Item item = p_180461_2_.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }
    public int getContainerSize() {
        return this.items.size();
    }
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }
    public ItemStack getItem(int p_70301_1_) {
        return this.items.get(p_70301_1_);
    }

    public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
        return ContainerHelper.removeItem(this.items, p_70298_1_, p_70298_2_);
    }
    public ItemStack removeItemNoUpdate(int p_58387_) {
        return ContainerHelper.takeItem(this.items, p_58387_);
    }
    public void setItem(int p_70299_1_, ItemStack p_70299_2_) {
        ItemStack itemstack = this.items.get(p_70299_1_);
        boolean flag = !p_70299_2_.isEmpty() && ItemStack.isSameItemSameTags(itemstack, p_70299_2_);
        this.items.set(p_70299_1_, p_70299_2_);
        if (p_70299_2_.getCount() > this.getMaxStackSize()) {
            p_70299_2_.setCount(this.getMaxStackSize());
        }

        if (p_70299_1_ == 0 && !flag) {
            this.cookingTotalTime = this.getTotalCookTime();
            this.cookingProgress = 0;
            this.setChanged();
        }

    }
    public boolean stillValid(Player p_70300_1_) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return p_70300_1_.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }
    public boolean canPlaceItem(int p_94041_1_, ItemStack p_94041_2_) {
        if (p_94041_1_ == 2) {
            return false;
        } else if (p_94041_1_ != 1) {
            return true;
        } else {
            ItemStack itemstack = this.items.get(1);
            return true;
        }
    }
    public void clearContent() {
        this.items.clear();
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


    public void fillStackedContents(StackedContents p_194018_1_) {
        for(ItemStack itemstack : this.items) {
            p_194018_1_.accountStack(itemstack);
        }

    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
    }

}
