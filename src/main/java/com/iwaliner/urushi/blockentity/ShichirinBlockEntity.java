package com.iwaliner.urushi.blockentity;



import com.iwaliner.urushi.*;
import com.iwaliner.urushi.block.ShichirinBlock;
import com.iwaliner.urushi.recipe.FryingRecipe;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public  class ShichirinBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible, RecipeHolder {
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();

    public final RecipeType<? extends CampfireCookingRecipe> recipeType=RecipeType.CAMPFIRE_COOKING;
    private static final int[] SLOTS_FOR_DOWN = new int[]{1};
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_SIDES = new int[]{2};
    private int processingTime;
    public int prePerfectFire;
    public int fire;
    private int differ;
    public String savedRecipe;
    private final int iconAmount=ConfigUrushi.shichirinIconAmount.get();
    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

    public ShichirinBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.Shichirin.get(), p_155052_, p_155053_);
    }
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items.clear();
        ContainerHelper.loadAllItems(tag, this.items);
        this.processingTime = tag.getInt("processTime");
        this.fire = tag.getInt("fire");
        this.differ = tag.getInt("differ");
        this.prePerfectFire = tag.getInt("prePerfectFire");
        this.savedRecipe=tag.getString("savedRecipe");

    }

    protected void saveAdditional(CompoundTag p_187452_) {
        super.saveAdditional(p_187452_);
        p_187452_.putInt("processTime", this.processingTime);
        p_187452_.putInt("fire", this.fire);
        p_187452_.putInt("differ", this.differ);
        p_187452_.putInt("prePerfectFire", this.prePerfectFire);
        ContainerHelper.saveAllItems(p_187452_, this.items,true);
        if(savedRecipe!=null) {
            p_187452_.putString("savedRecipe", this.savedRecipe);
        }
    }
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();
        compoundtag.putInt("processTime", this.processingTime);
        compoundtag.putInt("fire", this.fire);
        compoundtag.putInt("differ", this.differ);
        compoundtag.putInt("prePerfectFire", this.prePerfectFire);

        ContainerHelper.saveAllItems(compoundtag, this.items, true);
        if(savedRecipe!=null) {
            compoundtag.putString("savedRecipe", this.savedRecipe);
        }
        return compoundtag;
    }
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public ItemStack getDisplayingStack() {
        this.markUpdated();
        return this.getItem(1)==ItemStack.EMPTY? this.getItem(0) : this.getItem(1);
    }
    @Override
    public int getMaxStackSize() {
        return 64;
    }

    protected Component getDefaultName() {
        return Component.translatable("container.shichirin");
    }


    @Override
    protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
        return null;
    }


    public int getContainerSize() {
        return 3;
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

    public void moveItemToExportSlot() {
        ItemStack stack=this.getItem(0);
        if(!stack.isEmpty()) {
            this.setItem(1, stack);
            this.setItem(0, ItemStack.EMPTY);
        }
        this.markUpdated();
    }
    public ItemStack pickItem() {
        this.markUpdated();
        boolean slot0IsEmpty=this.getItem(0).isEmpty();
        ItemStack stack=slot0IsEmpty? this.getItem(1).copy() : this.getItem(0).copy();
        this.setItem(slot0IsEmpty? 1 : 0,ItemStack.EMPTY);
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
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameTags(stack, itemstack);
        this.items.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }




    }


    @Override
    public boolean stillValid(Player player) {
        assert this.level != null;
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }


    public void setItems(NonNullList<ItemStack> p_199721_1_) {
        this.items = p_199721_1_;
    }


    public static void tick(Level level, BlockPos pos, BlockState state, ShichirinBlockEntity blockEntity) {

        if (!level.isClientSide&&state.getBlock() instanceof ShichirinBlock) {
            ItemStack slot0Stack = blockEntity.items.get(0);
            ItemStack slot1Stack = blockEntity.items.get(1);
            ItemStack fuelStack = blockEntity.items.get(2);
            AbstractCookingRecipe campfireCookingRecipe;
            if (blockEntity.savedRecipe==null) {
                campfireCookingRecipe = level.getRecipeManager().getRecipeFor((RecipeType<CampfireCookingRecipe>) blockEntity.recipeType, blockEntity, level).orElse(null);
                if (campfireCookingRecipe != null) {
                    blockEntity.savedRecipe = campfireCookingRecipe.getId().toString();
                }
            } else {
                Optional<? extends Recipe<?>> r = level.getRecipeManager().byKey(Objects.requireNonNull(ResourceLocation.tryParse(blockEntity.savedRecipe)));
                if (r.isPresent()) {
                    AbstractCookingRecipe cr = (AbstractCookingRecipe) r.get();
                    if (cr.getIngredients().get(0).getItems()[0]==slot0Stack) {
                        campfireCookingRecipe = cr;

                    } else {
                        campfireCookingRecipe = level.getRecipeManager().getRecipeFor((RecipeType<CampfireCookingRecipe>) blockEntity.recipeType, blockEntity, level).orElse(null);
                        if (campfireCookingRecipe != null) {
                            blockEntity.savedRecipe = campfireCookingRecipe.getId().toString();
                        }
                    }
                } else {
                    campfireCookingRecipe = level.getRecipeManager().getRecipeFor((RecipeType<CampfireCookingRecipe>) blockEntity.recipeType, blockEntity, level).orElse(null);
                    if (campfireCookingRecipe != null) {
                        blockEntity.savedRecipe = campfireCookingRecipe.getId().toString();
                    }
                }
            }


            if (fuelStack.isEmpty() && state.getValue(ShichirinBlock.SHICHIRIN) != 0) {
                level.setBlock(pos, state.setValue(ShichirinBlock.SHICHIRIN, 0), 2);
            }
            if (state.getValue(ShichirinBlock.SHICHIRIN) == 0 && !fuelStack.isEmpty()) {
                if(blockEntity.prePerfectFire==0){
                    blockEntity.prePerfectFire=350;
                }
                level.setBlock(pos, state.setValue(ShichirinBlock.SHICHIRIN, 1), 2);
            }
            if (state.getValue(ShichirinBlock.SHICHIRIN) != 0) {

                if (blockEntity.fire > 0) {
                    --blockEntity.fire;
                } else {
                    blockEntity.fire = 0;
                }
                if (campfireCookingRecipe != null){
                    if (blockEntity.canWork() && blockEntity.canBurn(campfireCookingRecipe, blockEntity.items, blockEntity.getMaxStackSize())) {
                        blockEntity.processingTime++;
                        if (blockEntity.processingTime > 20 * 5) {
                            blockEntity.differ += blockEntity.fire - blockEntity.getPerfectFire(campfireCookingRecipe);
                        }
                    } else {
                        blockEntity.processingTime = 0;
                    }
                if (!blockEntity.canBurn(campfireCookingRecipe, blockEntity.items, blockEntity.getMaxStackSize())) {
                    blockEntity.moveItemToExportSlot();
                }
                if (blockEntity.processingTime >= blockEntity.getMaxProcessTime(campfireCookingRecipe) && blockEntity.canBurn(campfireCookingRecipe, blockEntity.items, blockEntity.getMaxStackSize())) {
                    blockEntity.prePerfectFire = blockEntity.getPerfectFire(campfireCookingRecipe);
                    ItemStack resultStack = campfireCookingRecipe.assemble(blockEntity, level.registryAccess());
                    resultStack.grow(slot0Stack.getCount() - 1);
                    if (resultStack.getTag() == null) {
                        resultStack.setTag(new CompoundTag());
                    }
                    CompoundTag tag = resultStack.getTag();

                    blockEntity.setCookingNBT(tag, blockEntity.differ);
                    blockEntity.items.set(0, resultStack.copy());
                    blockEntity.processingTime = 0;
                    blockEntity.differ = 0;
                    blockEntity.moveItemToExportSlot();

                    if (level.random.nextInt(5) == 0) {
                        fuelStack.shrink(1);
                    }
                    level.playSound((Player) null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1F, 1F);
                }
            }
                if (blockEntity.fire == 0 && state.getValue(ShichirinBlock.SHICHIRIN) != 0 && state.getValue(ShichirinBlock.SHICHIRIN) != 1) {
                    //火を消す
                    level.setBlock(pos, state.setValue(ShichirinBlock.SHICHIRIN, 1), 2);
                    level.playSound((Player) null,pos,SoundEvents.FIRE_EXTINGUISH,SoundSource.BLOCKS,1F,1F);
                } else if (blockEntity.getCookingTypeByFire(campfireCookingRecipe) == 0 && state.getValue(ShichirinBlock.SHICHIRIN) != 2) {
                    level.setBlock(pos, state.setValue(ShichirinBlock.SHICHIRIN, 2), 2);
                    level.playSound((Player) null,pos,SoundEvents.FIRE_AMBIENT,SoundSource.BLOCKS,1F,1F);
                } else if (blockEntity.getCookingTypeByFire(campfireCookingRecipe) == 1 && state.getValue(ShichirinBlock.SHICHIRIN) != 3) {
                    level.setBlock(pos, state.setValue(ShichirinBlock.SHICHIRIN, 3), 2);
                    level.playSound((Player) null,pos,SoundEvents.FIRE_AMBIENT,SoundSource.BLOCKS,1F,1F);
                } else if (blockEntity.getCookingTypeByFire(campfireCookingRecipe) == 2 && state.getValue(ShichirinBlock.SHICHIRIN) != 4) {
                    level.setBlock(pos, state.setValue(ShichirinBlock.SHICHIRIN, 4), 2);
                    level.playSound((Player) null,pos,SoundEvents.FIRE_AMBIENT,SoundSource.BLOCKS,1F,1F);
                }


                List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(1D));
                if (!list.isEmpty()) {
                    for (LivingEntity entity : list) {
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            MutableComponent component = Component.translatable("info.urushi.shichirin.message");
                            String filled = "█";
                            String empty = "▒";
                            String center_filled = "★";
                            String center_empty = "☆";
                             int iconAmount= blockEntity.iconAmount;

                            int j1=Mth.floor(iconAmount*(double) blockEntity.fire/(double) blockEntity.getPerfectFire(campfireCookingRecipe));
                             int j2= j1>iconAmount-1? iconAmount : blockEntity.fire==0? 0 : j1+1;
                            int j3= j1<iconAmount+1? 0 : j1>iconAmount*2? iconAmount : j1-iconAmount;
                            int j4= j1>iconAmount-1? 1 : 0;
                            int j5= j1>iconAmount-1? 0 : 1;
                            player.displayClientMessage(component.append(StringUtils.repeat(filled,j2)).append(StringUtils.repeat(empty,iconAmount - j2)).append(StringUtils.repeat(center_filled,j4)).append(StringUtils.repeat(center_empty,j5)).append(StringUtils.repeat(filled,j3)).append(StringUtils.repeat(empty,iconAmount-j3)).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.UNDERLINE), true);



                           /* Entity entity2 = Minecraft.getInstance().getCameraEntity();
                            double d0 = (double) Minecraft.getInstance().gameMode.getPickRange();
                            double entityReach =  Minecraft.getInstance().player.getEntityReach();
                            HitResult hitResult = player.pick(Math.max(d0, entityReach), 1f, false);
                            Vec3 vec3=hitResult.getLocation();

                            level.setBlock(new BlockPos((int) (vec3.x+0.5D),(int) (vec3.y+0.5D),(int) (vec3.z+0.5D)), ItemAndBlockRegister.ghost_red_kakuriyo_portal_frame.get().defaultBlockState(),3);

*/
                        }
                    }
                }
            }
        }

    }
    public int getMaxProcessTime(AbstractCookingRecipe campfireCookingRecipe){
        if(campfireCookingRecipe==null){
            return 600;
        }
        return campfireCookingRecipe.getCookingTime();
    }
    public int getPerfectFire(AbstractCookingRecipe campfireCookingRecipe){
        if(campfireCookingRecipe==null){
            return prePerfectFire;
        }
        return Mth.floor(campfireCookingRecipe.getExperience()*1000);
    }
    public int addFire(int i){
        return this.fire+=i;
    }
    public boolean canWork(){
        if (getItem(0).isEmpty()) return false;
        assert level != null;
        return level.getBlockState(getBlockPos()).getBlock() instanceof ShichirinBlock && level.getBlockState(getBlockPos()).getValue(ShichirinBlock.SHICHIRIN)!=0;
    }
    public void markUpdated() {
        this.setChanged();
        Objects.requireNonNull(this.getLevel()).sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == 1) {
            return false;
        }else if (slot == 2) {
            return stack.is(TagUrushi.SHICHIRIN_FUEL);
        }  else {
            return this.items.get(0).getCount()==0&&this.items.get(1).getCount()==0;
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
        }else if(direction==Direction.DOWN){
            return SLOTS_FOR_DOWN;
        }else{
            return SLOTS_FOR_SIDES;
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
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void fillStackedContents(StackedContents contents) {
        for(ItemStack itemstack : this.items) {
            contents.accountStack(itemstack);
        }
    }
    public boolean canBurn(@Nullable Recipe<?> recipe, NonNullList<ItemStack> stacks, int count) {
        if(stacks.get(2).isEmpty()){
            return false;
        }else {
            if (!stacks.get(0).isEmpty() && recipe != null) {
                ItemStack itemstack = ((Recipe<WorldlyContainer>) recipe).assemble(this,level.registryAccess());
                if (itemstack.isEmpty()) {
                    return false;
                } else {
                    ItemStack itemstack1 = stacks.get(1);
                    if (itemstack1.isEmpty()) {
                        return true;
                    } else if (!ItemStack.isSameItemSameTags(itemstack1, itemstack)) {
                        return false;
                    } else if (itemstack1.getCount() + itemstack.getCount() <= count && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) {
                        return true;
                    } else {
                        return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
                    }
                }
            } else {
                return false;
            }
        }
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
        return (Recipe<?>) this.recipeType;
    }
    private CompoundTag setCookingNBT(CompoundTag tag,int differ){
        ShichirinEnum enumType=getEnum();
        tag.putInt("cookingEnum", enumType.getID());
        return tag;
    }
    public  enum ShichirinEnum {
        undercookedLevel5(0),
        undercookedLevel4(1),
        undercookedLevel3(2),
        undercookedLevel2(3),
        undercookedLevel1(4),
        wellcookedLevel1A(5),
        wellcookedLevel2A(6),
        wellcookedLevel3A(7),
        wellcookedLevel4A(8),
        wellcookedLevel5(9),
        wellcookedLevel4B(10),
        wellcookedLevel3B(11),
        wellcookedLevel2B(12),
        wellcookedLevel1B(13),
        overcookedLevel1(14),
        overcookedLevel2(15),
        overcookedLevel3(16),
        overcookedLevel4(17),
        overcookedLevel5(18);

        private int id;

        private ShichirinEnum(int id) {
            this.id = id;
        }
        public int getID()
        {
            return this.id;
        }
    }
    public ShichirinEnum getEnum(){
        int i=ConfigUrushi.shichirincookingDifficlutly.get();
       // int i=2000;
        if(differ<-i/2-i*8){
           return ShichirinEnum.undercookedLevel5;
        }else if(differ<-i/2-i*7){
          return  ShichirinEnum.undercookedLevel4;
        }else if(differ<-i/2-i*6){
            return  ShichirinEnum.undercookedLevel3;
        } else if(differ<-i/2-i*5){
            return  ShichirinEnum.undercookedLevel2;
        }else if(differ<-i/2-i*4){
            return  ShichirinEnum.undercookedLevel1;
        }else if(differ<-i/2-i*3){
            return  ShichirinEnum.wellcookedLevel1A;
        }else if(differ<-i/2-i*2){
            return  ShichirinEnum.wellcookedLevel2A;
        }else if(differ<-i/2-i){
            return  ShichirinEnum.wellcookedLevel3A;
        }else if(differ<-i/2){
            return  ShichirinEnum.wellcookedLevel4A;
        }else if(differ<i/2){
            return  ShichirinEnum.wellcookedLevel5;
        }else if(differ<i/2+i){
            return  ShichirinEnum.wellcookedLevel4B;
        } else if(differ<i/2+i*2){
            return  ShichirinEnum.wellcookedLevel3B;
        }else if(differ<i/2+i*3){
            return  ShichirinEnum.wellcookedLevel2B;
        }else if(differ<i/2+i*4){
            return  ShichirinEnum.wellcookedLevel1B;
        }else if(differ<i/2+i*5){
            return  ShichirinEnum.overcookedLevel1;
        }else if(differ<i/2+i*6){
            return  ShichirinEnum.overcookedLevel2;
        }else if(differ<i/2+i*7){
            return  ShichirinEnum.overcookedLevel3;
        }else if(differ<i/2+i*8){
            return  ShichirinEnum.overcookedLevel4;
        }else {
            return  ShichirinEnum.overcookedLevel5;
        }
    }


    public static int getCookingLevel(int ID){
        int level;
       if(ID<5){
            level=-ID+5;
        }else if(ID<14){
            if(ID<9){
                level=ID-4;
            }else{
                level=-ID+14;
            }
          }else{
           level=ID-13;
        }
       return level;
    }
    public static String getCookingType(int ID){
        if(ID<5){
            return "undercooked";
        }else if(ID<14){
            return "wellcooked";
        }else{
            return "overcooked";
        }
    }
    public int getCookingTypeByFire(AbstractCookingRecipe recipe){
        double i=this.iconAmount*(double) fire/(double) getPerfectFire(recipe);
        if(fire==0){
            return 5000;
        }
        if(i<(this.iconAmount*2+1D)/3D){
            //炎が弱すぎ
            return 0;
        }else if(i<(this.iconAmount*2+1D)*2/3D){
            //炎がちょうどよい
            return 1;
        }else{
            //炎が強すぎ
            return 2;
        }
    }
}
