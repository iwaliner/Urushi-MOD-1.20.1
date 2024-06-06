package com.iwaliner.urushi.blockentity;



import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.block.DirtFurnaceBlock;
import com.iwaliner.urushi.block.RiceCauldronBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public  class RiceCauldronBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible {
    public int processingTime;
    private static final int[] SLOTS_FOR_UP_AND_SIDES = new int[]{1};
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int p_58431_) {
                return RiceCauldronBlockEntity.this.processingTime;
            }


        public void set(int p_58433_, int p_58434_) {

                    RiceCauldronBlockEntity.this.processingTime = p_58434_;

        }

        public int getCount() {
            return 4;
        }
    };
    public RiceCauldronBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.RiceCauldronBlockEntity.get(), p_155052_, p_155053_);
    }
    public void load(CompoundTag p_155025_) {
        super.load(p_155025_);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(p_155025_, this.items);
        this.processingTime = p_155025_.getInt("ProcessTime");


    }

    protected void saveAdditional(CompoundTag p_187452_) {
        super.saveAdditional(p_187452_);
        p_187452_.putInt("ProcessTime", this.processingTime);
      ContainerHelper.saveAllItems(p_187452_, this.items);
        CompoundTag compoundtag = new CompoundTag();
    }

    protected Component getDefaultName() {
        return Component.translatable("container.ricecauldron");
    }


    @Override
    protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
        return null;
    }


    public int getContainerSize() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public ItemStack removeItem(int i, int j) {
        return ContainerHelper.removeItem(this.items, i, j);
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return ContainerHelper.takeItem(this.items, i);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        ItemStack itemstack = this.items.get(slot);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameTags(stack, itemstack);
        this.items.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }


    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }


    public void setItems(NonNullList<ItemStack> p_199721_1_) {
        this.items = p_199721_1_;
    }


    public static void tick(Level level, BlockPos pos, BlockState bs, RiceCauldronBlockEntity blockEntity) {
        ItemStack slot0Stack=blockEntity.items.get(0);
        Item slot0Item=slot0Stack.getItem();
        ItemStack slot1Stack=blockEntity.items.get(1);
        Item slot1Item=slot1Stack.getItem();
        BlockState state=level.getBlockState(pos);

        //スロットに入ってるアイテムに対応した見た目のブロックにする
        if(blockEntity.processingTime==0&&!level.isClientSide()){
            if(slot0Stack.getCount()>0){
                level.setBlock(pos, state.setValue(RiceCauldronBlock.VARIANT, Integer.valueOf(2)), 2);
            }else if(slot0Stack.isEmpty()&&slot1Stack.isEmpty()&&state.getValue(RiceCauldronBlock.VARIANT)!=1){
                level.setBlock(pos, state.setValue(RiceCauldronBlock.VARIANT, Integer.valueOf(0)), 2);
            }
        }

        //アイテム変化
        if(level.getBlockState(pos.below()).getBlock()== ItemAndBlockRegister.dirt_furnace.get()&&level.getBlockState(pos.below()).getValue(DirtFurnaceBlock.LIT)){
            if(slot0Stack.getCount()>0&&slot1Stack.isEmpty()){
                if(blockEntity.processingTime<100){
                    blockEntity.processingTime++;
                }else{
                    blockEntity.setItem(1, new ItemStack(ItemAndBlockRegister.rice.get(),slot0Stack.getCount()));
                    blockEntity.setItem(0, ItemStack.EMPTY);
                    blockEntity.processingTime = 0;
                    if(!level.isClientSide()) {
                        level.setBlock(pos, state.setValue(RiceCauldronBlock.VARIANT, Integer.valueOf(3)), 2);
                    }
                }

            }

        }
    }


    public boolean canPlaceItem(int i, ItemStack stack) {
        if (i == 1) {
            return false;
        }  else {
            return this.items.get(0).getCount()==0&&this.items.get(1).getCount()==0&&stack.getItem()== ItemAndBlockRegister.raw_rice.get();
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @org.jetbrains.annotations.Nullable Direction p_19237_) {
        return this.canPlaceItem(i, itemStack);
    }


    public boolean canTakeItemThroughFace(int i, ItemStack stack, Direction direction) {
        if ( direction==Direction.UP) {
            return false;
        }
        return true;

    }

    public int[] getSlotsForFace(Direction direction) {
        if(direction==Direction.UP){
            return SLOTS_FOR_UP;
        }else{
            return SLOTS_FOR_UP_AND_SIDES;
        }
    }
    private boolean isWorking(){
        return this.processingTime!=0;
    }
    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else
                return handlers[1].cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void fillStackedContents(StackedContents contents) {
        for(ItemStack itemstack : this.items) {
            contents.accountStack(itemstack);
        }
    }

}
