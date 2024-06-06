package com.iwaliner.urushi.blockentity;



import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.RecipeTypeRegister;
import com.iwaliner.urushi.block.DirtFurnaceBlock;
import com.iwaliner.urushi.block.RiceCauldronBlock;
import com.iwaliner.urushi.blockentity.menu.FryerMenu;
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
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public  class FryerBlockEntity extends AbstractFryerBlockEntity  {
    public FryerBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.FryerBlockEntity.get(), p_155052_,p_155053_, RecipeTypeRegister.FryingRecipe);
    }
    protected Component getDefaultName() {
        return Component.translatable("container.fryer");
    }


    protected AbstractContainerMenu createMenu(int p_59293_, Inventory p_59294_) {
        return new FryerMenu(p_59293_, p_59294_, this, this.dataAccess);
    }

}
