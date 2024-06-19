package com.iwaliner.urushi;

import com.iwaliner.urushi.network.FramedBlockTextureConnectionPacket;
import com.iwaliner.urushi.network.NetworkAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModCoreUrushi.ModID, value = Dist.CLIENT)
public class ClientNonBusSetUp {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if(ClientSetUp.connectionKey.consumeClick()) {
            NetworkAccess.sendToServer(new FramedBlockTextureConnectionPacket(ClientSetUp.connectionKey.isDown()));
        }
    }
}
