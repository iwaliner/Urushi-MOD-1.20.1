package com.iwaliner.urushi.fluidtype;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class HotSpringWaterFluidType extends FluidType {
    /**
     * Default constructor.
     *
     * @param properties the general properties of the fluid type
     */
    public HotSpringWaterFluidType(Properties properties) {
        super(properties);
    }



    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            private static final ResourceLocation HotSpringStillTex = new ResourceLocation("block/water_still");
            private static final ResourceLocation HotSpringFlowingTex = new ResourceLocation("block/water_flow");
            private static final ResourceLocation HotSpringOverrayTex = new ResourceLocation("block/water_overlay");

            @Override
            public ResourceLocation getStillTexture() {
                return HotSpringStillTex;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return HotSpringFlowingTex;
            }

            @Override
            public @Nullable ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                return HotSpringOverrayTex;
            }

            @Override
            public int getTintColor() {
                return 0xbf60c3c9;
            }
        });
    }
}
