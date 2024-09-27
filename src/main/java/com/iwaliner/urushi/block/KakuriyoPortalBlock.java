package com.iwaliner.urushi.block;

import com.iwaliner.urushi.DimensionRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.TagUrushi;

import com.iwaliner.urushi.world.dimension.KakuriyoTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.ITeleporter;

import net.minecraft.util.RandomSource;

public class KakuriyoPortalBlock extends HorizonalRotateBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final int AABB_OFFSET = 2;
    protected static final VoxelShape Z_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape X_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

    public KakuriyoPortalBlock(BlockBehaviour.Properties p_54909_) {
        super(p_54909_);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.NORTH));

    }

    public VoxelShape getShape(BlockState state, BlockGetter p_54943_, BlockPos p_54944_, CollisionContext p_54945_) {


                return state.getValue(FACING).getAxis()== Direction.Axis.Z? Z_AXIS_AABB: X_AXIS_AABB;

    }




    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions()) {

            if(level instanceof ServerLevel) {

                ResourceKey<Level> resourcekey = level.dimension() == Level.OVERWORLD ? DimensionRegister.KakuriyoKey : Level.OVERWORLD;
                ServerLevel serverlevel = ((ServerLevel)level).getServer().getLevel(resourcekey);
                if (serverlevel == null) {

                    return;

                }
                BlockPos center=null;

                outer:for(int i=-5;i<5;i++){
                    for(int j=-1;j<7;j++){
                        for(int k=-5;k<5;k++) {
                            if (level.getBlockState(pos.offset(i, j, k)).getBlock() == ItemAndBlockRegister.kakuriyo_portal_core.get()&&resourcekey!=DimensionRegister.KakuriyoKey) {
                                center = pos.offset(i, j-4, k);

                                break outer;
                            }else if(level.getBlockState(pos.offset(i, j, k)).getBlock() == ItemAndBlockRegister.ghost_kakuriyo_portal_core.get()&&resourcekey==DimensionRegister.KakuriyoKey){
                                center = pos.offset(i, j-4, k);

                                break outer;
                            }
                        }
                    }
                }
                if(center==null){

                    return;
                }else {
                    entity.setDeltaMovement(Vec3.ZERO);
                    if (state.getValue(FACING).getAxis() == Direction.Axis.Z) {

                        entity.teleportTo(center.getX() + 0.5D, center.getY() + 0.5D, center.getZ() - 0.5D);
                    }else{

                        entity.teleportTo(center.getX() - 0.5D, center.getY() + 0.5D, center.getZ() + 0.5D);

                    }
                }

                BlockPos currentPos=entity.blockPosition();
                BlockPos upArrayPos=new BlockPos(currentPos.getX(),300,currentPos.getZ());
                if(resourcekey==DimensionRegister.KakuriyoKey){
                    int surfaceY=10;
                    for(int i=0;i<300;i++){
                        if(serverlevel.getBlockState(upArrayPos.below(i)).is(TagUrushi.YOMI_STONE)||serverlevel.getBlockState(upArrayPos.below(i)).is(BlockTags.DIRT)){
                            surfaceY=upArrayPos.below(i).getY();

                            break;
                        }
                    }
                    entity.teleportTo(entity.getX(), surfaceY+5, entity.getZ());


                }else{
                    int portalY=4000;
                    for(int i=0;i<300;i++){
                        if(serverlevel.getBlockState(upArrayPos.below(i)).getBlock() instanceof KakuriyoPortalCoreBlock||serverlevel.getBlockState(upArrayPos.below(i)).getBlock() instanceof GhostKakuriyoPortalCoreBlock){
                            portalY=upArrayPos.below(i).getY()-5;

                            break;
                        }
                    }
                    if(portalY==4000){
                        for(int i=320;i>-64;i--){
                            BlockPos pos1=new BlockPos(pos.getX(),i,pos.getZ());
                            BlockState state1=level.getBlockState(pos1);
                            if(state1.isCollisionShapeFullBlock(level,pos)){
                                portalY=i-5;

                                break;
                            }
                        }
                    }

                    entity.teleportTo(entity.getX(), portalY, entity.getZ());

                }
                ITeleporter teleporter = new KakuriyoTeleporter();
                entity.changeDimension(serverlevel, teleporter);

               // if(state.getValue(FACING).getAxis()== Direction.Axis.Z) {

               // }else{
                //    ITeleporter teleporter = new KakuriyoTeleporterAxisX();
                //    entity.changeDimension(serverlevel, teleporter);
               // }


            }
        }

    }

    public void animateTick(BlockState p_54920_, Level p_54921_, BlockPos p_54922_, RandomSource p_54923_) {
        if (p_54923_.nextInt(100) == 0) {
            p_54921_.playLocalSound((double)p_54922_.getX() + 0.5D, (double)p_54922_.getY() + 0.5D, (double)p_54922_.getZ() + 0.5D, SoundEvents.BEACON_AMBIENT, SoundSource.BLOCKS, 1F, p_54923_.nextFloat() * 0.4F + 0.8F, false);
        }

        for(int i = 0; i < 4; ++i) {
            double d0 = (double)p_54922_.getX() + p_54923_.nextDouble();
            double d1 = (double)p_54922_.getY() + p_54923_.nextDouble();
            double d2 = (double)p_54922_.getZ() + p_54923_.nextDouble();
            double d3 = ((double)p_54923_.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double)p_54923_.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double)p_54923_.nextFloat() - 0.5D) * 0.5D;
            int j = p_54923_.nextInt(2) * 2 - 1;
            if (!p_54921_.getBlockState(p_54922_.west()).is(this) && !p_54921_.getBlockState(p_54922_.east()).is(this)) {
                d0 = (double)p_54922_.getX() + 0.5D + 0.25D * (double)j;
                d3 = (double)(p_54923_.nextFloat() * 2.0F * (float)j);
            } else {
                d2 = (double)p_54922_.getZ() + 0.5D + 0.25D * (double)j;
                d5 = (double)(p_54923_.nextFloat() * 2.0F * (float)j);
            }
            org.joml.Vector3f PARTICLE_COLOR = new org.joml.Vector3f(Vec3.fromRGB24(15253552).toVector3f());
             DustColorTransitionOptions PARTICLE = new DustColorTransitionOptions(PARTICLE_COLOR, PARTICLE_COLOR, 1.0F);

            p_54921_.addParticle(PARTICLE, d0, d1, d2, d3, d4, d5);
        }

    }

    public ItemStack getCloneItemStack(BlockGetter p_54911_, BlockPos p_54912_, BlockState p_54913_) {
        return ItemStack.EMPTY;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_56051_) {
        p_56051_.add( WATERLOGGED,FACING);
    }
    public boolean propagatesSkylightDown(BlockState p_52348_, BlockGetter p_52349_, BlockPos p_52350_) {
        return !p_52348_.getValue(WATERLOGGED);
    }
    public FluidState getFluidState(BlockState p_56073_) {
        return p_56073_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_56073_);
    }
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

}
