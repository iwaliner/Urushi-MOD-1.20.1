package com.iwaliner.urushi.item;

import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.entiity.CushionEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class CushionItem extends Item {
    private DyeColor color;
    public CushionItem(DyeColor dyeColor, Properties p_i48487_1_) {
        super(p_i48487_1_);
        this.color=dyeColor;
    }
    public  DyeColor getColor(){
        return color;
    }
    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!(context.getLevel() instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        }

        Vec3 vector3d = Vec3.atBottomCenterOf(context.getClickedPos());
        AABB axisalignedbb = EntityRegister.Cushion.get().getDimensions().makeBoundingBox(vector3d.x(), vector3d.y(), vector3d.z());
        if (context.getLevel().getEntities((Entity) null, axisalignedbb).isEmpty()) {
            CushionEntity entity = new CushionEntity(context.getLevel(), context.getClickLocation().x,  context.getClickLocation().y,  context.getClickLocation().z);
            entity.moveTo(context.getClickLocation().x, context.getClickLocation().y, context.getClickLocation().z, context.getRotation(), 0.0F);
            entity.setType(this.color);
            context.getLevel().addFreshEntity(entity);
            context.getItemInHand().shrink(1);
            context.getLevel().playSound((Player) null, context.getClickedPos(), SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1F);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
