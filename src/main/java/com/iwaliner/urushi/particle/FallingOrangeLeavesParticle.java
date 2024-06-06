package com.iwaliner.urushi.particle;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FallingOrangeLeavesParticle extends AbstractFallingLeavesParticle{
    FallingOrangeLeavesParticle(ClientLevel p_108346_, double p_108347_, double p_108348_, double p_108349_, double p_108350_, double p_108351_, double p_108352_, SpriteSet p_108353_) {
        super(239,121,23,false,p_108346_, p_108347_, p_108348_, p_108349_, p_108350_, p_108351_, p_108352_, p_108353_);
    }
    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet p_108366_) {
            this.sprites = p_108366_;
        }

        public Particle createParticle(SimpleParticleType p_108377_, ClientLevel p_108378_, double p_108379_, double p_108380_, double p_108381_, double p_108382_, double p_108383_, double p_108384_) {
            return new FallingOrangeLeavesParticle(p_108378_, p_108379_, p_108380_, p_108381_, p_108382_, p_108383_, p_108384_, this.sprites);
        }
    }
}
