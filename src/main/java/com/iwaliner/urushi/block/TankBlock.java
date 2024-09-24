package com.iwaliner.urushi.block;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.blockentity.AbstractReiryokuStorableBlockEntity;
import com.iwaliner.urushi.blockentity.SenryoubakoBlockEntity;
import com.iwaliner.urushi.blockentity.TankBlockEntity;
import com.iwaliner.urushi.item.AbstractMagatamaItem;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.ElementBlock;
import com.iwaliner.urushi.util.interfaces.Tiered;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.util.RandomSource;

public class TankBlock extends BaseEntityBlock implements Tiered, ElementBlock {
    private static final VoxelShape BASE = Block.box(4D, 0.0D, 4D, 12D, 2D, 12D);
    private static final VoxelShape PILLAR = Block.box(7D, 1.0D, 7D, 9D, 16D, 9D);
    private static final VoxelShape OUTER_BOX = Block.box(4D, 0.0D, 4D, 12D, 16D, 12D);

    private ElementType elementType;
    private int tier;
    public TankBlock(int tier, ElementType type, Properties p_49224_) {
        super(p_49224_);
        elementType = type;
        this.tier = tier;
    }
    @Nullable
    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return OUTER_BOX;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        return Shapes.or(BASE,PILLAR);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TankBlockEntity(pos, state);
    }

    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }
    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        UrushiUtils.setInfo(list, "tank1");
        UrushiUtils.setInfo(list, "tank2");
    }

    @Override
    public ElementType getElementType() {
        return elementType;
    }

    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152160_, BlockState p_152161_, BlockEntityType<T> p_152162_) {
        return createTickerHelper(p_152162_, BlockEntityRegister.Tank.get(), TankBlockEntity::tick);
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.getBlockEntity(pos) instanceof TankBlockEntity) {
            TankBlockEntity blockEntity = (TankBlockEntity) level.getBlockEntity(pos);
            ItemStack stack=player.getItemInHand(hand);
            if(stack.getItem() instanceof AbstractMagatamaItem){

                int blockEntityStoredReiryoku=blockEntity.getStoredReiryoku();
                int blockEntityCapacity=blockEntity.getReiryokuCapacity();
                int magatamaStoredReiryoku=ElementUtils.getStoredReiryokuAmount(stack);
                int magatamaCapacity=ElementUtils.getReiryokuCapacity(stack);
                int i1=magatamaCapacity-magatamaStoredReiryoku;
                ElementType magatamaElementType=((AbstractMagatamaItem) stack.getItem()).getElementType();
                ElementType tankElementType=blockEntity.getStoredElementType();
                if(magatamaElementType==tankElementType) {
                    if(!player.isSuppressingBounce()) {
                    if (blockEntityStoredReiryoku >= i1) {
                        ElementUtils.increaseStoredReiryokuAmount(stack, i1);
                        blockEntity.decreaseStoredReiryoku(i1);
                        return InteractionResult.SUCCESS;
                    } else {
                        ElementUtils.increaseStoredReiryokuAmount(stack, blockEntityStoredReiryoku);
                        blockEntity.decreaseStoredReiryoku(blockEntityStoredReiryoku);
                        return InteractionResult.SUCCESS;
                    }
                }else if(magatamaStoredReiryoku>=100){
                        if(blockEntity.canAddReiryoku(100)){
                            blockEntity.addStoredReiryoku(100);
                            ElementUtils.increaseStoredReiryokuAmount(stack,-100);
                            return InteractionResult.SUCCESS;
                        }

                    }
                }
            }else if(!player.isSuppressingBounce()) {

                if (!level.isClientSide()) {
                    player.displayClientMessage(ElementUtils.getStoredReiryokuDisplayMessage(blockEntity.getStoredReiryoku(), blockEntity.getReiryokuCapacity(), blockEntity.getStoredElementType()), true);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
    public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
        return true;
    }
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity blockEntity=level.getBlockEntity(pos);
        if(blockEntity instanceof TankBlockEntity){
            TankBlockEntity tankBlockEntity= (TankBlockEntity) blockEntity;
            int i=Mth.floor(15F*(float) tankBlockEntity.getStoredReiryoku() /(float) tankBlockEntity.getReiryokuCapacity());
            return i>0? i : tankBlockEntity.getStoredReiryoku()==0? 0 : 1;
        }
        return 0;
    }
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof TankBlockEntity) {
                ItemStack itemstack = new ItemStack(this);
                BlockItem.setBlockEntityData(itemstack, BlockEntityRegister.Tank.get(), blockentity.saveWithoutMetadata());
                ItemEntity itementity = new ItemEntity(level, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), itemstack);
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            }
            super.playerWillDestroy(level, pos, state, player);
        }
    }
    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack= super.getCloneItemStack(state, target, level, pos, player);
        level.getBlockEntity(pos, BlockEntityRegister.Tank.get()).ifPresent((blockEntity) -> {
                  BlockItem.setBlockEntityData(stack, BlockEntityRegister.Tank.get(), blockEntity.saveWithoutMetadata());
                });
        return stack;
}

}
