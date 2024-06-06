package com.iwaliner.urushi.particle;

import com.iwaliner.urushi.blockentity.EmitterBlockEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class MediumParticle extends TextureSheetParticle {

    MediumParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet p_108353_) {
        super(level, x, y, z, xd, yd,zd);
        this.friction = 1F;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.quadSize *= 2F;
        this.lifetime = 80;
        this.setSpriteFromAge(p_108353_);
        this.hasPhysics=false;


    }

    @Override
    public void tick() {
        super.tick();
        BlockPos pos=BlockPos.containing(this.getPos());
        BlockState state=level.getBlockState(pos);
        BlockState frontState=level.getBlockState(pos.offset(this.xd==0D? 0 : this.xd>0D? 1 : -1,this.yd==0D? 0 : this.yd>0D? 1 : -1,this.zd==0D? 0 : this.zd>0D? 1 : -1));
        VoxelShape shape= state.getCollisionShape(level,pos).optimize();
        VoxelShape frontShape= frontState.getCollisionShape(level,pos.offset(this.xd==0D? 0 : this.xd>0D? 1 : -1,this.yd==0D? 0 : this.yd>0D? 1 : -1,this.zd==0D? 0 : this.zd>0D? 1 : -1)).optimize();
        double corner=6D;
        VoxelShape particleShape= Block.box(corner,corner,corner,16D-corner,16D-corner,16D-corner);
        if(!this.removed && Shapes.joinIsNotEmpty(shape,particleShape, BooleanOp.AND)){
            this.remove();
        }
        if(!this.removed && Shapes.joinIsNotEmpty(frontShape,particleShape, BooleanOp.AND)){
            this.setLifetime(Mth.floor(1/ EmitterBlockEntity.particleSpeed)+5);
        }



    }




    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet p_108366_) {
            this.sprites = p_108366_;
        }

        public Particle createParticle(SimpleParticleType p_108377_, ClientLevel p_108378_, double p_108379_, double p_108380_, double p_108381_, double p_108382_, double p_108383_, double p_108384_) {
            return new MediumParticle(p_108378_, p_108379_, p_108380_, p_108381_, p_108382_, p_108383_, p_108384_, this.sprites);
        }
    }
}
