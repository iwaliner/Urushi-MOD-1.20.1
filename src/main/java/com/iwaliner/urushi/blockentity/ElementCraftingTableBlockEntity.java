package com.iwaliner.urushi.blockentity;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.RecipeTypeRegister;
import com.iwaliner.urushi.block.ElementCraftingTableBlock;
import com.iwaliner.urushi.block.SanboBlock;
import com.iwaliner.urushi.recipe.AbstractElementCraftingRecipe;
import com.iwaliner.urushi.recipe.FryingRecipe;
import com.iwaliner.urushi.recipe.IElementCraftingRecipe;
import com.iwaliner.urushi.recipe.WoodElementTier1CraftingRecipe;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.interfaces.ReiryokuExportable;
import com.iwaliner.urushi.util.interfaces.ReiryokuImportable;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
 
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ElementCraftingTableBlockEntity extends AbstractReiryokuStorableBlockEntity implements ReiryokuImportable, RecipeHolder {
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    public int coolTime;

    public ElementCraftingTableBlockEntity(BlockPos p_155550_, BlockState p_155551_) {
        super(BlockEntityRegister.ElementCraftingTable.get(),1000, p_155550_, p_155551_);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.coolTime = tag.getInt("coolTime");

    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("coolTime", this.coolTime);

    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();
        compoundtag.putInt("coolTime", this.coolTime);
        this.putBaseTag(compoundtag);
        return compoundtag;
    }
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    public RecipeType<? extends AbstractElementCraftingRecipe> getRecipeType(){
        Block block= (ElementCraftingTableBlock)this.getBlockState().getBlock();
       if(block instanceof ElementCraftingTableBlock){
           ElementCraftingTableBlock elementCraftingTableBlock= (ElementCraftingTableBlock) block;
           return elementCraftingTableBlock.getRecipeType();
       }
       return null;

    }


    public static void tick(Level level, BlockPos pos, BlockState state, ElementCraftingTableBlockEntity elementCraftingTable) {
       elementCraftingTable.recieveReiryoku(level,pos);
       if(elementCraftingTable.getCoolTime()>0){
           elementCraftingTable.coolTime--;
       }else if(elementCraftingTable.getCoolTime()<0){
           elementCraftingTable.coolTime=0;
       }
       if(elementCraftingTable.getCoolTime()>0&&!state.getValue(ElementCraftingTableBlock.LIT)){
           level.setBlock(pos,state.setValue(ElementCraftingTableBlock.LIT,true),2);
       }else if(elementCraftingTable.getCoolTime()==0&&state.getValue(ElementCraftingTableBlock.LIT)){
           level.setBlock(pos,state.setValue(ElementCraftingTableBlock.LIT,false),2);
       }

       BlockState northState=level.getBlockState(pos.north());
       BlockState eastState=level.getBlockState(pos.east());
       BlockState southState=level.getBlockState(pos.south());
       BlockState westState=level.getBlockState(pos.west());
       boolean b1=northState.getBlock() instanceof SanboBlock&&eastState.getBlock() instanceof SanboBlock&&southState.getBlock() instanceof SanboBlock&&westState.getBlock() instanceof SanboBlock;
       if(level.getBlockEntity(pos.north()) instanceof SanboBlockEntity&&level.getBlockEntity(pos.east()) instanceof SanboBlockEntity&&level.getBlockEntity(pos.south()) instanceof SanboBlockEntity&&level.getBlockEntity(pos.west()) instanceof SanboBlockEntity) {
           SanboBlockEntity northSanbo = (SanboBlockEntity) level.getBlockEntity(pos.north());
           SanboBlockEntity eastSanbo = (SanboBlockEntity) level.getBlockEntity(pos.east());
           SanboBlockEntity southSanbo = (SanboBlockEntity) level.getBlockEntity(pos.south());
           SanboBlockEntity westSanbo = (SanboBlockEntity) level.getBlockEntity(pos.west());
           boolean b2 = northSanbo != null && eastSanbo != null && southSanbo != null && westSanbo != null;
           if (b1 && b2) {

               ElementType craftingTableElementType = elementCraftingTable.getStoredElementType();

               ItemStack northStack = northSanbo.getItem(0);
               ItemStack eastStack = eastSanbo.getItem(0);
               ItemStack southStack = southSanbo.getItem(0);
               ItemStack westStack = westSanbo.getItem(0);


               SimpleContainer container = new SimpleContainer(northStack, eastStack, southStack, westStack);
               Recipe<?> recipe = level.getRecipeManager().getRecipeFor((RecipeType<AbstractElementCraftingRecipe>) elementCraftingTable.getRecipeType(), container, level).orElse(null);
               if (recipe != null
                       //&& northSanboTier == elementCraftingTableTier && eastSanboTier == elementCraftingTableTier && southSanboTier == elementCraftingTableTier && westSanboTier == elementCraftingTableTier
              ) {
                   IElementCraftingRecipe iElementCraftingRecipe=(IElementCraftingRecipe)recipe;
                   int consumeReiryoku=iElementCraftingRecipe.getReiryoku();
                   if(elementCraftingTable.canDecreaseReiryoku(consumeReiryoku)) {
                       if (elementCraftingTable.getCoolTime() == 0) {
                           elementCraftingTable.coolTime = elementCraftingTable.getMaxCooltime();
                       } else if(elementCraftingTable.getCoolTime()==1) {


                           ItemStack resultStack = recipe.getResultItem(level.registryAccess());
                           ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 1D, pos.getZ() + 0.5D, resultStack.copy());
                           level.addFreshEntity(itemEntity);
                           northSanbo.clearContent();
                           eastSanbo.clearContent();
                           southSanbo.clearContent();
                           westSanbo.clearContent();
                           elementCraftingTable.decreaseStoredReiryoku(consumeReiryoku);
                           for(int i=0; i<20;i++) {
                               level.addParticle(ParticleTypes.COMPOSTER, pos.getX() + 0.0625D * 4 + 0.0625D * level.getRandom().nextInt(8), pos.getY() + 1D + 0.0625D * level.getRandom().nextInt(8), pos.getZ() + 0.0625D * 4 + 0.0625D * level.getRandom().nextInt(8), 0.0D, 0.0D, 0.0D);
                           }
                       }
                   }
               }
           }
       }

    }

    @Override
    public ElementType getImportElementType() {
        return this.getStoredElementType();
    }

    @Override
    public void setRecipeUsed(@org.jetbrains.annotations.Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation resourcelocation = recipe.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        ElementCraftingTableBlock elementCraftingTableBlock= (ElementCraftingTableBlock) this.getBlockState().getBlock();
        return (Recipe<?>)elementCraftingTableBlock.getRecipeType();
    }
    private int getMaxCooltime(){
        return 60;
    }
    private int getCoolTime(){
        return this.coolTime;
    }

}
