package com.iwaliner.urushi.network;

import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.network.FramedBlockTextureConnectionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkAccess {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ModCoreUrushi.ModID,"messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;



        net.messageBuilder(FramedBlockTextureConnectionPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FramedBlockTextureConnectionPacket::new)
                .encoder(FramedBlockTextureConnectionPacket::toBytes)
                .consumerMainThread(FramedBlockTextureConnectionPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
