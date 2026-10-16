package com.iwaliner.urushi.blockentity;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.block.EmitterBlock;
import com.iwaliner.urushi.util.ComplexDirection;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.interfaces.Mirror;
import com.iwaliner.urushi.util.interfaces.ReiryokuExportable;
import com.iwaliner.urushi.util.interfaces.ReiryokuImportable;
import com.iwaliner.urushi.util.interfaces.ReiryokuStorable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.iwaliner.urushi.block.EmitterBlock.FACING;

public class EmitterBlockEntity extends AbstractReiryokuStorableBlockEntity implements ReiryokuExportable {
    public int exportCoolTime=0;


    public EmitterBlockEntity(BlockPos p_155550_, BlockState p_155551_) {
        super(BlockEntityRegister.Emitter.get(), 100, p_155550_, p_155551_);
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("exportCoolTime",exportCoolTime);
    }

    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();
        this.putBaseTag(compoundtag);
        compoundtag.putInt("exportCoolTime",exportCoolTime);
        return compoundtag;
    }
    public void load(CompoundTag tag) {
        super.load(tag);
        exportCoolTime=tag.getInt("exportCoolTime");
    }




    public boolean isIdleSending(BlockState state){ //送信ができないときにtrue
        return exportCoolTime!=0||state.getValue(EmitterBlock.POWERED);
    }

    /**
     * tick毎の処理
     */
    public static void tick(Level level, BlockPos pos, BlockState state, EmitterBlockEntity emitterBlockEntity) {
        ElementUtils.replaceOldBlock(level,pos,state);
        /**送られた霊力を受け取る処理*/
        emitterBlockEntity.receiveReiryoku(level, pos);
        if (state.getBlock() instanceof EmitterBlock) {

            /**後ろにあるブロックから霊力を吸いだす処理　開始*/
            int i = ElementUtils.getReiryokuSendAmount();
            BlockPos importPos = pos.relative(state.getValue(FACING).getOpposite());
            BlockState importState = level.getBlockState(importPos);
            BlockEntity importBlockEntity = level.getBlockEntity(importPos);
            if (importBlockEntity instanceof ReiryokuStorable) {
                ReiryokuStorable reiryokuStorable = (ReiryokuStorable) importBlockEntity;
                ElementType elementType = reiryokuStorable.getStoredElementType();
                if (reiryokuStorable.canDecreaseReiryoku(i) && emitterBlockEntity.canAddReiryoku(i) && emitterBlockEntity.getStoredElementType() == elementType) {
                    reiryokuStorable.decreaseStoredReiryoku(i);
                    emitterBlockEntity.addStoredReiryoku(i);
                    reiryokuStorable.markUpdated();
                    emitterBlockEntity.markUpdated();
                }
            }
            /**後ろにあるブロックから霊力を吸いだす処理　終了*/

            /**連続して送信を行う(二個目以降)*/
            if(emitterBlockEntity.exportCoolTime>1){
                emitterBlockEntity.genParticleAndDecrease(level,pos,state,emitterBlockEntity, emitterBlockEntity.exportCoolTime-1);
            }else{
                emitterBlockEntity.exportCoolTime=0;
            }


            if (!emitterBlockEntity.isIdleSending(state)&&emitterBlockEntity.sendDistance(level, pos) != 0 && emitterBlockEntity.getStoredReiryoku() > 0) {

                //連続して送る総tick数
                int exportSuccessiveTime=0;

                for(int j=ElementUtils.getMaxExportSuccessiveTime();j>0;j--){
                    if(emitterBlockEntity.canDecreaseReiryoku(ElementUtils.getReiryokuSendAmount()*j)){
                        exportSuccessiveTime=j;
                        break;
                    }
                }
                if(exportSuccessiveTime>0) {
                    emitterBlockEntity.send(level, pos,  exportSuccessiveTime);
                }

            }

        }
    }

    /**
     * 送信先のブロックが何ブロック離れているか
     */
    private int sendDistance(Level level, BlockPos emitterPos) {
        BlockState emitterState = level.getBlockState(emitterPos);
        double corner = 6D;
        int j = 0;
        if (!(emitterState.getBlock() instanceof EmitterBlock) ) {
            return j;
        }
        Direction direction = emitterState.getValue(FACING);
        EmitterBlockEntity emitterBlockEntity = (EmitterBlockEntity) level.getBlockEntity(emitterPos);
        VoxelShape particleShape = Block.box(corner, corner, corner, 16D - corner, 16D - corner, 16D - corner);
        int range = Mth.floor(ElementUtils.getReiryokuParticleSpeed() * 80 - 0.25D);

        int sendAmount = 0;
        ElementType storedElementType = null;
        if (emitterBlockEntity != null){
            sendAmount = ElementUtils.getReiryokuSendAmount();
            storedElementType = emitterBlockEntity.getStoredElementType();
        }
        for (int i = 1; i < range; i++) {
            BlockPos relativePos = emitterPos.relative(direction, i);
            BlockEntity blockEntity = level.getBlockEntity(relativePos);
            BlockState state = level.getBlockState(relativePos);
            VoxelShape shape = state.getCollisionShape(level, relativePos).optimize();
            if (storedElementType!=null && blockEntity != null) {
                if ((blockEntity instanceof EmitterBlockEntity)) {
                    break;
                }
                if (blockEntity instanceof ReiryokuImportable || blockEntity instanceof MirrorBlockEntity) {
                    ReiryokuStorable reiryokuStorable = (ReiryokuStorable) blockEntity;
                    if (reiryokuStorable.canAddReiryoku(sendAmount)) {
                        if (blockEntity instanceof Mirror || reiryokuStorable.getStoredElementType() == storedElementType) {
                            j = i;
                            break;
                        }
                    }

                }

            } else if (Shapes.joinIsNotEmpty(shape, particleShape, BooleanOp.AND)) {
                break;
            }

        }


        return j;
    }


    /**
     * 霊力を輸送
     */
    private void send(Level level, BlockPos emitterPos,int exportSuccessiveTime) {
        BlockState emitterState = level.getBlockState(emitterPos);
        if (!(emitterState.getBlock() instanceof EmitterBlock)) {
            return;
        }
        Direction direction = emitterState.getValue(FACING);
        int distance = sendDistance(level, emitterPos);
        BlockPos goalPos = emitterPos.relative(direction, distance);
        EmitterBlockEntity emitterBlockEnitity = (EmitterBlockEntity) level.getBlockEntity(emitterPos);
        ReiryokuStorable goalBlockEntity = (ReiryokuStorable) level.getBlockEntity(goalPos);
        int arriveTick = Mth.floor((distance - 1) / ElementUtils.getReiryokuParticleSpeed()) <= 0 ? 1 : Mth.floor((distance - 1) / ElementUtils.getReiryokuParticleSpeed());
        int sendAmount = ElementUtils.getReiryokuSendAmount() * exportSuccessiveTime;
        if(emitterBlockEnitity != null && goalBlockEntity != null&&emitterBlockEnitity.canDecreaseReiryoku(sendAmount)&&goalBlockEntity.isIdle()) {

            while (true){
                if (goalBlockEntity.canAddReiryoku(sendAmount)) {
                        goalBlockEntity.setReceiveWaitingTime(arriveTick);
                        goalBlockEntity.setReceiveSuccessiveTime(exportSuccessiveTime);
                        goalBlockEntity.setReceiveAmount(ElementUtils.getReiryokuSendAmount());
                        goalBlockEntity.setReceiveElementType(emitterBlockEnitity.getExportElementType());
                    if (goalBlockEntity instanceof Mirror) {
                        Mirror mirror = (Mirror) goalBlockEntity;
                        mirror.setIncidentDirection(direction.getOpposite());
                    }
                    goalBlockEntity.markUpdated();
                    genParticleAndDecrease(level,emitterPos,emitterState,emitterBlockEnitity,exportSuccessiveTime);

                        break; //ループから抜け出す
                }else{
                    --sendAmount;
                }
            }

        }

    }
    private void genParticleAndDecrease(Level level,BlockPos pos,BlockState emitterState,EmitterBlockEntity emitterBlockEnitity,int newExportCoolTime){
        double vX = 0D, vY = 0D, vZ = 0D;
        double v0 = ElementUtils.getReiryokuParticleSpeed();

        Direction direction = emitterState.getValue(FACING);
        double dX = 0D, dY = 0D, dZ = 0D;
        if (direction == Direction.UP) {
            vY = v0;
            dY = -0.25D;
        } else if (direction == Direction.DOWN) {
            vY = -v0;
            dY = 0.25D;
        } else if (direction == Direction.NORTH) {
            vZ = -v0;
            dZ = 0.25D;
        } else if (direction == Direction.SOUTH) {
            vZ = v0;
            dZ = -0.25D;
        } else if (direction == Direction.EAST) {
            vX = v0;
            dX = -0.25D;
        } else if (direction == Direction.WEST) {
            vX = -v0;
            dX = 0.25D;
        }

            emitterBlockEnitity.decreaseStoredReiryoku(ElementUtils.getReiryokuSendAmount());
            emitterBlockEnitity.markUpdated();
            level.addParticle(ElementUtils.getMediumElementParticle(emitterBlockEnitity.getExportElementType()), pos.getX() + 0.5D + dX, pos.getY() + 0.5D + dY, pos.getZ() + 0.5D + dZ,vX, vY, vZ);
            emitterBlockEnitity.exportCoolTime=newExportCoolTime;
    }

    /**
     * 搬出時の元素
     */
    @Override
    public ElementType getExportElementType() {
        return this.getStoredElementType();
    }
    

    // change mirror incidentDirection tag when removing emitter
    public void onBlockRemove() {
        if(this.level==null) {
            return;
        }
        int distance = this.sendDistance(level, this.getBlockPos());
        if (distance <= 0) {
            return;
        }
        Direction direction = this.getBlockState().getValue(FACING);
        BlockPos targetPos = this.getBlockPos().relative(direction, distance);
        BlockEntity targetEntity = level.getBlockEntity(targetPos);
        if (targetEntity instanceof MirrorBlockEntity mirror) {
            mirror.setIncidentDirection(ComplexDirection.FAIL);
            mirror.markUpdated();
        }


    }
}
