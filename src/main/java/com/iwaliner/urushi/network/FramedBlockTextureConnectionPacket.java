package com.iwaliner.urushi.network;

import com.iwaliner.urushi.ModCoreUrushi;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FramedBlockTextureConnectionPacket {
    private final boolean isPressed;

    public FramedBlockTextureConnectionPacket(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public FramedBlockTextureConnectionPacket(FriendlyByteBuf buf) {
        this.isPressed = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(isPressed);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        ServerLevel level = player.serverLevel();
        context.enqueueWork(() -> {
            player.getCapability(FramedBlockTextureConnectionProvider.FRAMED_BLOCK_TEXTURE_CONNECTION).ifPresent(data -> {
                data.toggle();

            });

        });
        return true;
    }
}
