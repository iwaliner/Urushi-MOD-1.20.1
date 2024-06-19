package com.iwaliner.urushi.block;

import com.google.common.collect.Maps;
import com.iwaliner.urushi.ClientSetUp;
import com.iwaliner.urushi.ConfigUrushi;
import com.iwaliner.urushi.network.FramedBlockTextureConnectionProvider;
import com.iwaliner.urushi.util.ElementUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;


public class FramedPaneBlock extends HorizonalRotateBlock{
    protected static final VoxelShape SHAPEA = Block.box(7.0D, 0.0D, 0D, 9.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPEB = Block.box(0D, 0.0D, 7D, 16D, 16.0D, 9.0D);
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty VARIANT = AbstractFramedBlock.VARIANT;
    public FramedPaneBlock(Properties p_i48355_2_) {
        super(p_i48355_2_);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, Boolean.valueOf(false)).setValue(EAST, Boolean.valueOf(false)).setValue(SOUTH, Boolean.valueOf(false)).setValue(WEST, Boolean.valueOf(false)).setValue(UP, Boolean.valueOf(false)).setValue(DOWN, Boolean.valueOf(false)).setValue(VARIANT, Boolean.valueOf(false)).setValue(FACING, Direction.NORTH));

    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if(state.getValue(FACING)== Direction.NORTH||state.getValue(FACING)==Direction.SOUTH){
            return SHAPEB;
        }else{
            return SHAPEA;
        }
    }

    @Override
    public VoxelShape getVisualShape(BlockState p_60479_, BlockGetter p_60480_, BlockPos p_60481_, CollisionContext p_60482_) {
        return Shapes.empty();
    }


    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = Util.make(Maps.newEnumMap(Direction.class), (p_203421_0_) -> {
        p_203421_0_.put(Direction.NORTH, NORTH);
        p_203421_0_.put(Direction.EAST, EAST);
        p_203421_0_.put(Direction.SOUTH, SOUTH);
        p_203421_0_.put(Direction.WEST, WEST);
        p_203421_0_.put(Direction.UP, UP);
        p_203421_0_.put(Direction.DOWN, DOWN);
    });

    @Override
    public boolean propagatesSkylightDown(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
        return false;
    }


    public boolean connectsTo(BlockState thisState,BlockState nextState) {
        if(nextState.getBlock() instanceof FramedPaneBlock&&thisState.getBlock() instanceof FramedPaneBlock) {
            if(nextState.getValue(VARIANT)==thisState.getValue(VARIANT)&&(nextState.getValue(FACING)==thisState.getValue(FACING)||nextState.getValue(FACING)==thisState.getValue(FACING).getOpposite())) {
                return thisState.getBlock() == nextState.getBlock();
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    public boolean connectsToByFacing(BlockState thisState, Direction direction, LevelAccessor world, BlockPos pos) {
        BlockPos offsetPos = pos;

        if (direction== Direction.NORTH) {
            offsetPos = pos.north();
        } else if (direction == Direction.SOUTH) {
            offsetPos = pos.south();
        } else if (direction == Direction.WEST) {
            offsetPos = pos.west();
        } else if (direction == Direction.EAST) {
            offsetPos = pos.east();
        }else if (direction == Direction.UP) {
            offsetPos = pos.above();
        }else if (direction == Direction.DOWN) {
            offsetPos = pos.below();
        }

        if(world.getBlockState(offsetPos).getBlock() instanceof FramedPaneBlock) {
            if(world.getBlockState(offsetPos).getValue(VARIANT)==thisState.getValue(VARIANT)&&(world.getBlockState(offsetPos).getValue(FACING)==thisState.getValue(FACING)||world.getBlockState(offsetPos).getValue(FACING)==thisState.getValue(FACING).getOpposite())) {
                return thisState.getBlock() == world.getBlockState(offsetPos).getBlock();
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        switch(direction) {
            case CLOCKWISE_180:
                return state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    @Override
    public BlockState mirror(BlockState p_54122_, Mirror p_54123_) {
        switch(p_54123_) {
            case LEFT_RIGHT:
                return p_54122_.setValue(NORTH, p_54122_.getValue(SOUTH)).setValue(SOUTH, p_54122_.getValue(NORTH));
            case FRONT_BACK:
                return p_54122_.setValue(EAST, p_54122_.getValue(WEST)).setValue(WEST, p_54122_.getValue(EAST));
            default:
                return super.mirror(p_54122_, p_54123_);
        }
    }

    @Override
    public BlockState updateShape(BlockState state1, Direction facing, BlockState state2, LevelAccessor world, BlockPos pos1, BlockPos pos2) {
        return state1.setValue(PROPERTY_BY_DIRECTION.get(facing), Boolean.valueOf(this.connectsToByFacing(state1,facing,world,pos1))) ;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(NORTH, EAST, WEST, SOUTH,UP,DOWN,VARIANT,FACING);
    }
    private boolean textureConnection(Player player){
        AtomicBoolean b = new AtomicBoolean(false);
        player.getCapability(FramedBlockTextureConnectionProvider.FRAMED_BLOCK_TEXTURE_CONNECTION).ifPresent(data -> {
            b.set(data.isPressed());

        });
        return b.get();
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_) {

        LevelAccessor iblockreader = p_196258_1_.getLevel();
        BlockPos blockpos = p_196258_1_.getClickedPos();
        BlockState thisState = iblockreader.getBlockState(blockpos);
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockPos blockpos5 = blockpos.above();
        BlockPos blockpos6 = blockpos.below();
        BlockState nState = iblockreader.getBlockState(blockpos1);
        BlockState eState = iblockreader.getBlockState(blockpos2);
        BlockState sState = iblockreader.getBlockState(blockpos3);
        BlockState wState = iblockreader.getBlockState(blockpos4);
        BlockState aState = iblockreader.getBlockState(blockpos5);
        BlockState bState = iblockreader.getBlockState(blockpos6);

    /*    return super.getStateForPlacement(p_196258_1_).setValue(NORTH, Boolean.valueOf(this.connectsTo(thisState, nState)))
                .setValue(SOUTH, Boolean.valueOf(this.connectsTo(thisState, sState)))
                .setValue(WEST, Boolean.valueOf(this.connectsTo(thisState, wState)))
                .setValue(EAST, Boolean.valueOf(this.connectsTo(thisState, eState)))
                .setValue(UP, Boolean.valueOf(this.connectsTo(thisState, aState)))
                .setValue(DOWN, Boolean.valueOf(this.connectsTo(thisState, bState)))
                .setValue(VARIANT,p_196258_1_.getLevel().getServer()==null? p_196258_1_.getPlayer().isSuppressingBounce() : p_196258_1_.getLevel().getServer().isSingleplayer() ?
                        ClientSetUp.connectionKey.isDown() :
                        p_196258_1_.getPlayer().isSuppressingBounce());

*/
        try {
            BlockState newState=this.defaultBlockState().setValue(NORTH, Boolean.valueOf(this.connectsTo(thisState, nState)))
                    .setValue(SOUTH, Boolean.valueOf(this.connectsTo(thisState, sState)))
                    .setValue(WEST, Boolean.valueOf(this.connectsTo(thisState, wState)))
                    .setValue(EAST, Boolean.valueOf(this.connectsTo(thisState, eState)))
                    .setValue(UP, Boolean.valueOf(this.connectsTo(thisState, aState)))
                    .setValue(DOWN, Boolean.valueOf(this.connectsTo(thisState, bState)))
                    .setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite())
                    .setValue(VARIANT,
                            textureConnection(Objects.requireNonNull(p_196258_1_.getPlayer())) )
                    ;

            return newState;
        }catch (Exception e){
            BlockState newState=this.defaultBlockState().setValue(NORTH, Boolean.valueOf(this.connectsTo(thisState, nState)))
                    .setValue(SOUTH, Boolean.valueOf(this.connectsTo(thisState, sState)))
                    .setValue(WEST, Boolean.valueOf(this.connectsTo(thisState, wState)))
                    .setValue(EAST, Boolean.valueOf(this.connectsTo(thisState, eState)))
                    .setValue(UP, Boolean.valueOf(this.connectsTo(thisState, aState)))
                    .setValue(DOWN, Boolean.valueOf(this.connectsTo(thisState, bState)))
                    .setValue(FACING, p_196258_1_.getHorizontalDirection().getOpposite())
                    .setValue(VARIANT, p_196258_1_.getPlayer().isSuppressingBounce())
                    ;

            return newState;
        }

    }





}
