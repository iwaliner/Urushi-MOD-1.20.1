package com.iwaliner.urushi;

import com.iwaliner.urushi.entiity.GhostEntity;
import com.iwaliner.urushi.network.AdditionalHeartProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModCoreUrushi.ModID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobAttributeSetUp {
    /**モブの挙動・性質を設定*/
    @SubscribeEvent
    public static void MobAttributesEvent(EntityAttributeCreationEvent entityRegisterEvent) {


        entityRegisterEvent.put(EntityRegister.Ghost.get(),
                GhostEntity.createAttributes()
                        .add(Attributes.MAX_HEALTH, 20.0D)
                        .add(Attributes.ATTACK_DAMAGE, 6.0D)
                        .add(Attributes.ATTACK_SPEED, 0.5D)
                        .add(Attributes.FLYING_SPEED, 1.0D)
                        .add(Attributes.MOVEMENT_SPEED, 0.2D)
                        .build());

        entityRegisterEvent.put(EntityRegister.GianntSkeleton.get(),
                GhostEntity.createAttributes()
                        .add(Attributes.MAX_HEALTH, 20.0D)
                        .add(Attributes.ATTACK_DAMAGE, 6.0D)
                        .add(Attributes.ATTACK_SPEED, 0.5D)
                        .add(Attributes.FLYING_SPEED, 1.0D)
                        .add(Attributes.MOVEMENT_SPEED, 0.2D)
                        .build());

        entityRegisterEvent.put(EntityRegister.KakuriyoVillager.get(),
                GhostEntity.createAttributes()
                        .add(Attributes.MAX_HEALTH, 20.0D)
                        .add(Attributes.ATTACK_DAMAGE, 0.0D)
                        .add(Attributes.ATTACK_SPEED, 0.0D)
                        .add(Attributes.FLYING_SPEED, 0.0D)
                        .add(Attributes.MOVEMENT_SPEED, 0.5D)
                        .build());

    }
}
