package com.iwaliner.urushi.item;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.Random;

public class UrushiBlockItem extends BlockItem {
    public UrushiBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int a, boolean b) {
        super.inventoryTick(stack, level, entity, a, b);
       ItemStack mainStack=ItemStack.EMPTY;
       ItemStack offStack=ItemStack.EMPTY;
        if(entity instanceof LivingEntity){
           LivingEntity livingEntity= (LivingEntity) entity;
           mainStack=livingEntity.getMainHandItem();
            offStack=livingEntity.getOffhandItem();
            if(mainStack==stack) {
                if (UrushiUtils.isAprilFoolsDay()&&shouldPollenParticleWhenAprilFool(stack)) {
                    Vec3 color = new Vec3(0.85D, 0.655D, 0D);
                    Vector3f vector3f = new Vector3f(1f, 0f, 0f);
                    Vector3f rotatedVector3f = vector3f.rotateY(Mth.PI * (-livingEntity.yBodyRot - 90f) / 180f);
                    Vec3 position = livingEntity.position().add(rotatedVector3f.x, 1D, rotatedVector3f.z);
                    for (int i = 0; i < 10; i++) {
                        RandomSource rand = livingEntity.getRandom();
                        double d0 = -0.5D + rand.nextDouble();
                        double d1 = -0.5D + rand.nextDouble();
                        double d2 = -0.5D + rand.nextDouble();
                        level.addParticle(new DustParticleOptions(color.toVector3f(), 1.0F), position.x + d0, position.y + d1, position.z + d2, 0F, 0F, 0F);
                    }
                }
            }

        }

    }
    private boolean shouldPollenParticleWhenAprilFool(ItemStack stack){
        if(stack.getItem()== Item.byBlock(ItemAndBlockRegister.japanese_cedar_sapling.get())||stack.getItem()==Item.byBlock(ItemAndBlockRegister.japanese_cedar_leaves.get())){
            return true;
        }else if(stack.getItem()== Item.byBlock(ItemAndBlockRegister.cypress_sapling.get())||stack.getItem()==Item.byBlock(ItemAndBlockRegister.cypress_leaves.get())){
            return true;
        }
        return false;
    }
}
