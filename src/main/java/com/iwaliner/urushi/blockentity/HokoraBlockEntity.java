package com.iwaliner.urushi.blockentity;


import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ParticleRegister;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.interfaces.ElementBlock;
import com.iwaliner.urushi.util.interfaces.ReiryokuExportable;
import com.iwaliner.urushi.util.interfaces.Tiered;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public  class HokoraBlockEntity extends AbstractReiryokuStorableBlockEntity implements WorldlyContainer, StackedContentsCompatible, ReiryokuExportable {

    private static final int[] SLOTS_FOR_UP = new int[]{0};
    public int coolTime;
    private NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    public HokoraBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.Hokora.get(),1000, p_155052_, p_155053_);
    }
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items.clear();
        ContainerHelper.loadAllItems(compoundTag, this.items);
        this.coolTime = compoundTag.getInt("coolTime");
    }

    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
       ContainerHelper.saveAllItems(compoundTag, this.items,true);
        compoundTag.putInt("coolTime", this.coolTime);
    }
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();
        ContainerHelper.saveAllItems(compoundtag, this.items, true);
        compoundtag.putInt("coolTime", this.coolTime);
        this.putBaseTag(compoundtag);
        return compoundtag;
    }
    @Override
    public ElementType getExportElementType() {
        return this.getStoredElementType();
    }

    private int getAddAmount(){
        return 5;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public ItemStack getDisplayingStack() {
        this.markUpdated();
        return this.getItem(0) ;
    }
    @Override
    public int getMaxStackSize() {
        return 1;
    }

    protected Component getDefaultName() {
        return Component.translatable("container.hokora");
    }




    public int getContainerSize() {
        return 1;
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

    /*public void moveItemToExportSlot() {


        ItemStack stack=this.getItem(0);
        if(!stack.isEmpty()) {
            this.setItem(1, stack);
            this.setItem(0, ItemStack.EMPTY);
        }
        this.markUpdated();
    }*/
    public ItemStack pickItem() {
        this.markUpdated();

        ItemStack stack=this.getItem(0).copy();
        this.setItem(0,ItemStack.EMPTY);
        return stack;
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
        this.markUpdated();
        ItemStack itemstack = this.items.get(slot);
        ElementType elementType=this.getStoredElementType();
        if (itemstack.isEmpty()) {
            if(stack.getItem() == getFuel(elementType)) {
                this.items.set(slot, stack);
                if (stack.getCount() > this.getMaxStackSize()) {
                    stack.setCount(this.getMaxStackSize());
                }
            }
        }else{
            this.items.set(slot, stack);
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


    public void setItems(NonNullList<ItemStack> list) {
        this.items = list;
    }
    private static Item getFuel(ElementType elementType){
        if(elementType==ElementType.WoodElement){
            return ItemAndBlockRegister.matured_japanese_apricot_fruit.get();
        }else if(elementType==ElementType.FireElement){
            return Items.WHEAT;
        }else if(elementType==ElementType.EarthElement){
            return Item.byBlock(ItemAndBlockRegister.rice_crop.get());
        }else if(elementType==ElementType.MetalElement){
            return Item.byBlock(ItemAndBlockRegister.green_onion_crop.get());
        }else if(elementType==ElementType.WaterElement){
            return Item.byBlock(ItemAndBlockRegister.soy_crop.get());
        }else{
            return Items.AIR;
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState bs, HokoraBlockEntity blockEntity) {

        ItemStack slotStack=blockEntity.items.get(0);
        BlockState state=level.getBlockState(pos);

      ElementType elementType=blockEntity.getStoredElementType();

        if(slotStack.getItem()==getFuel(elementType)){
            if(blockEntity.coolTime<20*5) {
                blockEntity.coolTime++;

            }else if( blockEntity.canAddReiryoku(blockEntity.getAddAmount())){
                blockEntity.addStoredReiryoku(blockEntity.getAddAmount());
                blockEntity.setItem(0, ItemStack.EMPTY);
                blockEntity.markUpdated();
                blockEntity.coolTime=0;
                for(int i=0; i<20;i++) {
                    level.addParticle(ParticleTypes.COMPOSTER, pos.getX() + 0.0625D * 4 + 0.0625D * level.getRandom().nextInt(8), pos.getY() + 0.0625D * 4 + 0.0625D * level.getRandom().nextInt(8), pos.getZ() + 0.0625D * 4 + 0.0625D * level.getRandom().nextInt(8), 0.0D, 0.0D, 0.0D);
                }
                level.playSound((Player) null, pos, SoundEvents.GLOW_ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1F, 1F);

            }
        }else{

            if(blockEntity.coolTime!=0){
                blockEntity.coolTime=0;
            }
        }



    }
    public void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public boolean canPlaceItem(int slot, ItemStack stack) {
        ElementType elementType=this.getStoredElementType();

            return this.items.get(0).getCount()==0&&stack.getItem()==getFuel(elementType);

    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @org.jetbrains.annotations.Nullable Direction p_19237_) {
        ElementType elementType=this.getStoredElementType();
        return this.canPlaceItem(i, itemStack)&itemStack.getItem()==getFuel(elementType);
    }


    public boolean canTakeItemThroughFace(int i, ItemStack stack, Direction direction) {
        if ( direction==Direction.UP) {
            return false;
        }
        return true;

    }

    public int[] getSlotsForFace(Direction direction) {

            return SLOTS_FOR_UP;

    }
    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER) {
            if (facing == Direction.UP)
                return handlers[0].cast();

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
