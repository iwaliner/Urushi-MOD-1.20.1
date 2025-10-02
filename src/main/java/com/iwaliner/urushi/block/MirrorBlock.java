package com.iwaliner.urushi.block;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.blockentity.MirrorBlockEntity;
import com.iwaliner.urushi.util.ComplexDirection;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.Tiered;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
 
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MirrorBlock extends BaseEntityBlock implements Tiered {
    private static final VoxelShape BASE = Block.box(6D, 0.0D, 6D, 10D, 1D, 10D);
    private static final VoxelShape PILLAR = Block.box(7D, 1.0D, 7D, 9D, 16D, 9D);
    private static final VoxelShape OUTER_BOX = Block.box(1D, 0.0D, 1D, 15D, 16D, 15D);
    public static final IntegerProperty DIRECTION = IntegerProperty.create("complex_facing",0,30);


    private int tier;
    public MirrorBlock(int tier, Properties p_49224_) {
        super(p_49224_);
        this.tier = tier;
        this.registerDefaultState(this.stateDefinition.any().setValue(DIRECTION, 1));
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
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        if(facing==Direction.NORTH){
            return this.defaultBlockState().setValue(DIRECTION, ComplexDirection.N.getID());
        }else if(facing==Direction.EAST){
            return this.defaultBlockState().setValue(DIRECTION,ComplexDirection.E.getID());
        }else if(facing==Direction.SOUTH){
            return this.defaultBlockState().setValue(DIRECTION,ComplexDirection.S.getID());
        }else{
            return this.defaultBlockState().setValue(DIRECTION,ComplexDirection.W.getID());
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MirrorBlockEntity(pos, state);
    }

    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }
    @Override
    public void appendHoverText(ItemStack p_49816_, @Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        UrushiUtils.setInfo(list, "mirror1");
        UrushiUtils.setInfo(list, "mirror2");
        UrushiUtils.setInfo(list, "mirror3");
        UrushiUtils.setInfo(list, "mirror4");
    }


    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152160_, BlockState p_152161_, BlockEntityType<T> p_152162_) {
        return createTickerHelper(p_152162_, BlockEntityRegister.Mirror.get(), MirrorBlockEntity::tick);
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        ComplexDirection complexDirection=MirrorBlockEntity.getDirectionFromID(state.getValue(MirrorBlock.DIRECTION));
        if (level.getBlockEntity(pos) instanceof MirrorBlockEntity) {

                if(!player.isSuppressingBounce()){
                    level.setBlockAndUpdate(pos, state.setValue(DIRECTION, state.getValue(DIRECTION) > 15 ? 1 : state.getValue(DIRECTION) + 1));
                    return InteractionResult.SUCCESS;
                }else if(canFaceAbove(complexDirection)){
                    ComplexDirection newDirection=ComplexDirection.FAIL;
                    if(complexDirection==ComplexDirection.N||complexDirection==ComplexDirection.S){
                        newDirection=ComplexDirection.N_UN;
                    }else if(complexDirection==ComplexDirection.N_UN){
                        newDirection=ComplexDirection.UN;
                    }else if(complexDirection==ComplexDirection.UN){
                        newDirection=ComplexDirection.U_UN;
                    }else if(complexDirection==ComplexDirection.U_UN){
                        newDirection=ComplexDirection.U_NSdir;
                    }else if(complexDirection==ComplexDirection.U_NSdir){
                        newDirection=ComplexDirection.U_US;
                    }else if(complexDirection==ComplexDirection.U_US){
                        newDirection=ComplexDirection.US;
                    }else if(complexDirection==ComplexDirection.US){
                        newDirection=ComplexDirection.S_US;
                    }else if(complexDirection==ComplexDirection.S_US){
                        newDirection=ComplexDirection.N;
                    }else if(complexDirection==ComplexDirection.E||complexDirection==ComplexDirection.W){
                        newDirection=ComplexDirection.E_UE;
                    }else if(complexDirection==ComplexDirection.E_UE){
                        newDirection=ComplexDirection.UE;
                    }else if(complexDirection==ComplexDirection.UE){
                        newDirection=ComplexDirection.U_UE;
                    }else if(complexDirection==ComplexDirection.U_UE){
                        newDirection=ComplexDirection.U_WEdir;
                    }else if(complexDirection==ComplexDirection.U_WEdir){
                        newDirection=ComplexDirection.U_UW;
                    }else if(complexDirection==ComplexDirection.U_UW){
                        newDirection=ComplexDirection.UW;
                    }else if(complexDirection==ComplexDirection.UW){
                        newDirection=ComplexDirection.W_UW;
                    }else if(complexDirection==ComplexDirection.W_UW){
                        newDirection=ComplexDirection.E;
                    }else{
                        return InteractionResult.FAIL;
                    }

                    level.setBlockAndUpdate(pos, state.setValue(DIRECTION, newDirection.getID()));
                    return InteractionResult.SUCCESS;
                }

        }
        return InteractionResult.FAIL;
    }
    private  boolean canFaceAbove(ComplexDirection direction){
        return direction==ComplexDirection.N||direction==ComplexDirection.N_UN ||direction==ComplexDirection.UN ||direction==ComplexDirection.U_UN ||
                direction==ComplexDirection.E||direction==ComplexDirection.E_UE ||direction==ComplexDirection.UE ||direction==ComplexDirection.U_UE ||
                direction==ComplexDirection.S||direction==ComplexDirection.S_US ||direction==ComplexDirection.US ||direction==ComplexDirection.U_US ||
                direction==ComplexDirection.W||direction==ComplexDirection.W_UW ||direction==ComplexDirection.UW ||direction==ComplexDirection.U_UW
                ||direction==ComplexDirection.U_NSdir ||direction==ComplexDirection.U_WEdir;
    }

}
