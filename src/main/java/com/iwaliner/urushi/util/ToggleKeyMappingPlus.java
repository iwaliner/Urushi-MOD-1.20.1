package com.iwaliner.urushi.util;


import com.iwaliner.urushi.ModCoreUrushi;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.login.ServerboundKeyPacket;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.function.BooleanSupplier;

@OnlyIn(Dist.CLIENT)
public class ToggleKeyMappingPlus extends KeyMapping {


    public ToggleKeyMappingPlus(String description, net.minecraftforge.client.settings.IKeyConflictContext keyConflictContext, final InputConstants.Type inputType, final int keyCode, String category) {
        super(description, keyConflictContext, inputType.getOrCreate(keyCode), category);
    }
    public ToggleKeyMappingPlus(String name, int button, String category) {
        super(name, InputConstants.Type.KEYSYM, button, category);
    }

    public void setDown(boolean p_92534_) {
        if (p_92534_ && isConflictContextAndModifierActive()) {
            super.setDown(!this.isDown());
        }

    }
}
