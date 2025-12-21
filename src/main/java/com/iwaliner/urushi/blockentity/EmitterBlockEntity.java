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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.iwaliner.urushi.block.EmitterBlock.FACING;

public class EmitterBlockEntity extends AbstractReiryokuStorableBlockEntity implements ReiryokuExportable {

    public EmitterBlockEntity(BlockPos p_155550_, BlockState p_155551_) {
        super(BlockEntityRegister.Emitter.get(), 100, p_155550_, p_155551_);
    }

    private int getTier() {
        if (getBlockState().getBlock() instanceof EmitterBlock) {
            return ((EmitterBlock) getBlockState().getBlock()).getTier();
        }
        return 0;
    }

    public static final double particleSpeed = 0.2D;

    private double getParticleSpeed() {
        if(this.getTier() == 2){
            return 0.3D;
        }
        return 0.2D;
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();
        this.putBaseTag(compoundtag);
        return compoundtag;
    }

    /**
     * 一度に送信する霊力の量
     */
    private int getSendAmount() {
        int i = getTier() == 2 ? 5 : 1;
        if (this.getStoredReiryoku() - i < 0) {
            return getStoredReiryoku();
        } else {
            return i;
        }
    }


    /**
     * tick毎の処理
     */
    public static void tick(Level level, BlockPos pos, BlockState state, EmitterBlockEntity emitterBlockEntity) {

        /**送られた霊力を受け取る処理*/
        emitterBlockEntity.recieveReiryoku(level, pos);
        if (state.getBlock() instanceof EmitterBlock) {

            /**後ろにあるブロックから霊力を吸いだす処理　開始*/
            int i = emitterBlockEntity.getTier() == 2 ? 5 : 1;
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


            if (state.getValue(EmitterBlock.POWERED)) {
                return;
            }
            if (emitterBlockEntity.sendDistance(level, pos) != 0 && emitterBlockEntity.getStoredReiryoku() > 0) {
                double vX = 0D, vY = 0D, vZ = 0D;
                double v0 = emitterBlockEntity.getParticleSpeed();

                Direction direction = state.getValue(FACING);
                double dx = 0D, dy = 0D, dz = 0D;
                if (direction == Direction.UP) {
                    vY = v0;
                    dy = -0.25D;
                } else if (direction == Direction.DOWN) {
                    vY = -v0;
                    dy = 0.25D;
                } else if (direction == Direction.NORTH) {
                    vZ = -v0;
                    dz = 0.25D;
                } else if (direction == Direction.SOUTH) {
                    vZ = v0;
                    dz = -0.25D;
                } else if (direction == Direction.EAST) {
                    vX = v0;
                    dx = -0.25D;
                } else if (direction == Direction.WEST) {
                    vX = -v0;
                    dx = 0.25D;
                }
                emitterBlockEntity.send(level, pos, pos.getX() + 0.5D + dx, pos.getY() + 0.5D + dy, pos.getZ() + 0.5D + dz, vX, vY, vZ);


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
        int range = Mth.floor(this.getParticleSpeed() * 80 - 0.25D);

        int sendAmount = 0;
        ElementType storedElementType = null;
        if (emitterBlockEntity != null){
            sendAmount = emitterBlockEntity.getSendAmount();
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
    private void send(Level level, BlockPos emitterPos, double dX, double dY, double dZ, double vX, double vY, double vZ) {
        BlockState emitterState = level.getBlockState(emitterPos);
        if (!(emitterState.getBlock() instanceof EmitterBlock)) {
            return;
        }
        Direction direction = emitterState.getValue(FACING);
        int distance = sendDistance(level, emitterPos);
        BlockPos goalPos = emitterPos.relative(direction, distance);
        EmitterBlockEntity emitterBlockEnitity = (EmitterBlockEntity) level.getBlockEntity(emitterPos);
        ReiryokuStorable goalBlockEntity = (ReiryokuStorable) level.getBlockEntity(goalPos);
        int arriveTick = Mth.floor((distance - 1) / getParticleSpeed()) <= 0 ? 1 : Mth.floor((distance - 1) / getParticleSpeed());
        if (emitterBlockEnitity != null && goalBlockEntity != null && emitterBlockEnitity.canDecreaseReiryoku(emitterBlockEnitity.getSendAmount()) && goalBlockEntity.canAddReiryoku(emitterBlockEnitity.getSendAmount())) {
            if (goalBlockEntity.isIdle()) {
                goalBlockEntity.setReceiveWaitingTime(arriveTick);
                goalBlockEntity.setReceiveAmount(emitterBlockEnitity.getSendAmount());
                goalBlockEntity.setReceiveElementType(emitterBlockEnitity.getExportElementType());
                if (goalBlockEntity instanceof Mirror) {
                    Mirror mirror = (Mirror) goalBlockEntity;
                    mirror.setIncidentDirection(direction.getOpposite());
                }
                goalBlockEntity.markUpdated();
                emitterBlockEnitity.decreaseStoredReiryoku(emitterBlockEnitity.getSendAmount());
                emitterBlockEnitity.markUpdated();
                level.addParticle(ElementUtils.getMediumElementParticle(this.getExportElementType()), dX, dY, dZ, vX, vY, vZ);

            }
        }

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
