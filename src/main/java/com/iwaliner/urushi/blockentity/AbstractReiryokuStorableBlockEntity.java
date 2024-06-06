package com.iwaliner.urushi.blockentity;


import com.iwaliner.urushi.block.MirrorBlock;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.interfaces.ElementBlock;
import com.iwaliner.urushi.util.interfaces.Mirror;
import com.iwaliner.urushi.util.interfaces.ReiryokuStorable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractReiryokuStorableBlockEntity extends BlockEntity implements ReiryokuStorable{
        protected int storedReiryoku;
       private final int capacity;
        protected int receiveWaitingTime;
        protected int receiveAmount;
        protected int receiveElementType;

         public AbstractReiryokuStorableBlockEntity(BlockEntityType<?> blockEntity,int capacity, BlockPos p_155550_, BlockState p_155551_) {
            super(blockEntity, p_155550_, p_155551_);
            this.capacity=capacity;
        }

    /**保存してあるデータを読み取る*/
    public void load(CompoundTag tag) {
        super.load(tag);
        this.storedReiryoku = tag.getInt("storedReiryoku");
        this.receiveWaitingTime = tag.getInt("receiveWaitingTime");
        this.receiveAmount = tag.getInt("receiveAmount");
        this.receiveElementType = tag.getInt("receiveElementType");
    }

    /**データを保存する*/
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("storedReiryoku", this.storedReiryoku);
        tag.putInt("receiveWaitingTime", this.receiveWaitingTime);
        tag.putInt("receiveAmount", this.receiveAmount);
        tag.putInt("receiveElementType", this.receiveElementType);
    }

    /**必ず使うデータの新規設定*/
    protected void putBaseTag(CompoundTag compoundtag){
        compoundtag.putInt("storedReiryoku", this.storedReiryoku);
        compoundtag.putInt("receiveWaitingTime", this.receiveWaitingTime);
        compoundtag.putInt("receiveAmount", this.receiveAmount);
        compoundtag.putInt("receiveElementType", this.receiveElementType);
    }

    /**非稼働状態ならtrue*/
    @Override
    public boolean isIdle() {
        return receiveWaitingTime==0;
    }

    /**霊力の最大貯蔵容量*/
    @Override
        public int getReiryokuCapacity() {
            return capacity;
        }

    /**霊力の現在の貯蔵量*/
        @Override
        public int getStoredReiryoku() {
           return storedReiryoku;
        }

    /**霊力の貯蔵量を増加*/
        @Override
        public void addStoredReiryoku(int i) {
            if(getStoredReiryoku()+i<=getReiryokuCapacity()){
                    storedReiryoku+=i;

            }else{
                int j=getReiryokuCapacity()-getStoredReiryoku();
                    storedReiryoku=getReiryokuCapacity();

            }
        }

    /**霊力の貯蔵量を増加させる容量があるか*/
        @Override
        public boolean canAddReiryoku(int i){
            if(getStoredReiryoku()+i<=getReiryokuCapacity()){
                return true;
            }else{
                return false;
            }
        }

    /**霊力の貯蔵量を減少*/
        @Override
        public void decreaseStoredReiryoku(int i) {
            if(getStoredReiryoku()-i>=0){
                    storedReiryoku-=i;

            }else{
                int j=i-getStoredReiryoku();
                    storedReiryoku=0;

            }
        }

    /**霊力の貯蔵量を減少させられるか*/
        @Override
        public boolean canDecreaseReiryoku(int i) {
            if(getStoredReiryoku()-i>=0){
                return true;
            }else{
                return false;
            }
        }

    /**貯蔵する元素*/
        @Override
        public ElementType getStoredElementType() {
            if (this.getBlockState().getBlock() instanceof ElementBlock) {
                ElementBlock block = (ElementBlock) this.getBlockState().getBlock();
                return block.getElementType();
            }
            return null;
        }

    /**パーティクルが届いて霊力が増加するまでのtick数*/
    @Override
    public int getReceiveWaitingTime() {
           return this. receiveWaitingTime;
    }

    /**次に霊力が増加するときの増加量*/
    @Override
    public int getReceiveAmount() {
        return this. receiveAmount;
    }

    /**受け取る元素*/
    @Override
    public ElementType getReceiveElementType() {
        return ElementType.getType(receiveElementType-1);
    }



    /**tick更新ごとに、霊力を受信可能なら受信する*/
    @Override
        public void recieveReiryoku(Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof ReiryokuStorable) {

            ReiryokuStorable blockEntity = (ReiryokuStorable) level.getBlockEntity(pos);
            if(blockEntity!=null){
                int receiveWaitingTime = blockEntity.getReceiveWaitingTime();
                ElementType receiveElementType=blockEntity.getReceiveElementType();
                int receiveAmount=blockEntity.getReceiveAmount();
                int storedReiryoku= blockEntity.getStoredReiryoku();

                if((receiveWaitingTime==1&&(blockEntity.getStoredElementType()==receiveElementType||receiveElementType==ElementType.FAIL)&&receiveAmount>0)||(receiveWaitingTime==1&&blockEntity instanceof Mirror &&receiveAmount>0)){
                    if (blockEntity.canAddReiryoku(receiveAmount)) {
                        blockEntity.addStoredReiryoku(receiveAmount);
                        blockEntity.setReceiveAmount(0);
                        blockEntity.markUpdated();
                        /**最大貯蔵量の近辺の処理をまだ書いてない*/
                    }else{
                        for(int i=0;i<receiveAmount;i++) {
                            ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ(), ElementUtils.getOverflowStack(this.getStoredElementType()));
                            level.addFreshEntity(itemEntity);
                        }
                    }
                }

                if(receiveWaitingTime>0){
                    blockEntity.setReceiveWaitingTime(receiveWaitingTime-1);
                }else{
                    blockEntity.setReceiveWaitingTime(0);
                    blockEntity.setReceiveAmount(0);

                }
            }
        }
    }

    /**次に霊力が増加するときの増加量を指定*/
    @Override
    public void setReceiveAmount(int receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    /**受け取る元素を指定*/
    @Override
    public void setReceiveElementType(ElementType elementType) {
        this.receiveElementType = elementType.getID()+1;
    }

    /**パーティクルが届いて霊力が増加するまでのtick数を指定*/
    @Override
    public void setReceiveWaitingTime(int receiveWaitingTime) {
        this.receiveWaitingTime = receiveWaitingTime;
    }

    /**サーバー側とクライアント側を同期させるためのパケット*/
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /**更新時のタグ*/
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    /**更新させる*/
    public void markUpdated() {
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

}
