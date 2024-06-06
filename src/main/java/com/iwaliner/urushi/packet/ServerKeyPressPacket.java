package com.iwaliner.urushi.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ServerKeyPressPacket {
    public static boolean isPressed;
    public ServerKeyPressPacket(boolean b) {
        this.isPressed = b;
    }

    public ServerKeyPressPacket(FriendlyByteBuf buffer) {
        this(buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(isPressed);
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        final var success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> {

                success.set(true);

        });

        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
