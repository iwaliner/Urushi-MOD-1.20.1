package com.iwaliner.urushi.block;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.blockentity.FillerBlockEntity;
import com.iwaliner.urushi.blockentity.MarkerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MarkerBlock extends BaseEntityBlock {
    private static final VoxelShape BASE = Block.box(4D, 0.0D, 4D, 12D, 2D, 12D);
    private static final VoxelShape PILLAR = Block.box(7D, 1.0D, 7D, 9D, 16D, 9D);
    private static final VoxelShape OUTER_BOX = Block.box(4D, 0.0D, 4D, 12D, 16D, 12D);


    public MarkerBlock(Properties p_49224_) {
        super(p_49224_);
        this.registerDefaultState(this.stateDefinition.any());
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return OUTER_BOX;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        return Shapes.or(BASE,PILLAR);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MarkerBlockEntity(pos, state);
    }

    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }



    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(level.getBlockEntity(pos) instanceof MarkerBlockEntity markerBlockEntity){
            if(level.isClientSide()){
                ModCoreUrushi.logger.info("client:"+markerBlockEntity.hasRange());
            }else{
                ModCoreUrushi.logger.info("server:"+markerBlockEntity.hasRange());
            }
            if(!markerBlockEntity.hasRange()){
                int limit=128;
                for(int i=1;i<=limit;i++){
                    BlockPos eachPos1=pos.offset(i,0,0);
                    BlockPos eachPos2=pos.offset(-i,0,0);
                    if(level.getBlockState(eachPos1).getBlock() instanceof MarkerBlock){
                        markerBlockEntity.posMarkerX=eachPos1;
                        break;
                    }else if(level.getBlockState(eachPos2).getBlock() instanceof MarkerBlock){
                        markerBlockEntity.posMarkerX=eachPos2;
                        break;
                    }
                }
                for(int i=1;i<=limit;i++){
                    BlockPos eachPos1=pos.offset(0,i,0);
                    BlockPos eachPos2=pos.offset(0,-i,0);
                    if(level.getBlockState(eachPos1).getBlock() instanceof MarkerBlock){
                        markerBlockEntity.posMarkerY=eachPos1;
                        break;
                    }else if(level.getBlockState(eachPos2).getBlock() instanceof MarkerBlock){
                        markerBlockEntity.posMarkerY=eachPos2;
                        break;
                    }
                }
                for(int i=1;i<=limit;i++){
                    BlockPos eachPos1=pos.offset(0,0,i);
                    BlockPos eachPos2=pos.offset(0,0,-i);
                    if(level.getBlockState(eachPos1).getBlock() instanceof MarkerBlock){
                        markerBlockEntity.posMarkerZ=eachPos1;
                        break;
                    }else if(level.getBlockState(eachPos2).getBlock() instanceof MarkerBlock){
                        markerBlockEntity.posMarkerZ=eachPos2;
                        break;
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.CONSUME;
    }

}
