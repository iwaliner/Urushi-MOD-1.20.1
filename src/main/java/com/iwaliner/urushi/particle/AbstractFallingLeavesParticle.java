package com.iwaliner.urushi.particle;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ParticleRegister;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public abstract class AbstractFallingLeavesParticle extends SimpleAnimatedParticle {

    AbstractFallingLeavesParticle(int R,int G,int B,boolean isSakura,ClientLevel p_108346_, double p_108347_, double p_108348_, double p_108349_, double p_108350_, double p_108351_, double p_108352_, SpriteSet p_108353_) {
        super(p_108346_, p_108347_, p_108348_, p_108349_, p_108353_, 1.25F);
        this.friction = 0F;
        this.xd = p_108350_;
        this.yd = p_108351_;
        this.zd = p_108352_;
        if(isSakura) {
            this.quadSize *= 0.3F;
        }else{
            this.quadSize *= 0.8F;
            this.setColor((float) R / 255, (float) G / 255, (float) B / 255);
        }
        this.lifetime = 240 + this.random.nextInt(12);
        this.setSpriteFromAge(p_108353_);
    }
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        int r=random.nextInt(8);
        this.setParticleSpeed(0.001D*(10+r),0D,-0.001D*(10+r));
    }
}
