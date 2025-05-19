package com.iwaliner.urushi.block;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.RecipeTypeRegister;
import com.iwaliner.urushi.blockentity.FillerBlockEntity;
import com.iwaliner.urushi.blockentity.MarkerBlockEntity;
import com.iwaliner.urushi.blockentity.TankBlockEntity;
import com.iwaliner.urushi.blockentity.WoodenCabinetryBlockEntity;
import com.iwaliner.urushi.item.AbstractMagatamaItem;
import com.iwaliner.urushi.recipe.SandpaperPolishingRecipe;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.ElementBlock;
import com.iwaliner.urushi.util.interfaces.Tiered;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FillerBlock extends BaseEntityBlock {
    private static final VoxelShape BASE = Block.box(4D, 0.0D, 4D, 12D, 2D, 12D);
    private static final VoxelShape PILLAR = Block.box(7D, 1.0D, 7D, 9D, 16D, 9D);
    private static final VoxelShape OUTER_BOX = Block.box(4D, 0.0D, 4D, 12D, 16D, 12D);


    public FillerBlock(Properties p_49224_) {
        super(p_49224_);
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof FillerBlockEntity) {
                player.openMenu((FillerBlockEntity)blockentity);
                player.awardStat(Stats.OPEN_BARREL);
            }

            return InteractionResult.CONSUME;
        }
    }
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state2, boolean boo) {
        if (!state.is(state2.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof Container) {
                Containers.dropContents(level, pos, (Container)blockentity);
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, level, pos, state2, boo);
        }
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
        return new FillerBlockEntity(pos, state);
    }

    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }

    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152160_, BlockState p_152161_, BlockEntityType<T> p_152162_) {
        return createTickerHelper(p_152162_, BlockEntityRegister.Filler.get(), FillerBlockEntity::tick);
    }
   @Override
   public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean flag) {
       super.onPlace(state, level, pos, state2, flag);
       if(level.getBlockEntity(pos) instanceof FillerBlockEntity fillerBlockEntity){
           BlockPos markerPos=null;
           for (int i = 0; i < 6; i++) {
               BlockPos neighborPos=pos.relative(UrushiUtils.getDirectionFromInt(i));
               BlockState neighbor = level.getBlockState(neighborPos);
              if(neighbor.getBlock() instanceof MarkerBlock&&level.getBlockEntity(neighborPos) instanceof MarkerBlockEntity markerBlockEntity){
                  if(markerBlockEntity.hasRange()){
                      BlockPos xMarker=markerBlockEntity.posMarkerX;
                      BlockPos yMarker=markerBlockEntity.posMarkerY;
                      BlockPos zMarker=markerBlockEntity.posMarkerZ;
                      fillerBlockEntity.posOrigin=neighborPos;
                      fillerBlockEntity.rangeX=xMarker.getX()-neighborPos.getX();
                      fillerBlockEntity.rangeY=yMarker.getY()-neighborPos.getY();
                      fillerBlockEntity.rangeZ=zMarker.getZ()-neighborPos.getZ();
                      int count=0;
                      if(level.getBlockState(neighborPos).is(ItemAndBlockRegister.marker.get())){
                          level.setBlockAndUpdate(neighborPos,Blocks.AIR.defaultBlockState());
                          count++;
                      }
                      if(level.getBlockState(xMarker).is(ItemAndBlockRegister.marker.get())){
                          level.setBlockAndUpdate(xMarker,Blocks.AIR.defaultBlockState());
                          count++;
                      }
                      if(level.getBlockState(yMarker).is(ItemAndBlockRegister.marker.get())){
                          level.setBlockAndUpdate(yMarker,Blocks.AIR.defaultBlockState());
                          count++;
                      }
                      if(level.getBlockState(zMarker).is(ItemAndBlockRegister.marker.get())){
                          level.setBlockAndUpdate(zMarker,Blocks.AIR.defaultBlockState());
                          count++;
                      }
                      if(!level.isClientSide()){
                          ItemEntity itemEntity=new ItemEntity(level,neighborPos.getX()+0.5D,neighborPos.getY()+0.5D,neighborPos.getZ()+0.5D,new ItemStack(ItemAndBlockRegister.marker.get(),count));
                          level.addFreshEntity(itemEntity);
                      }
                      break;
                  }
              }
           }
       }
   }


}
