package com.iwaliner.urushi.world;

import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Optional;

public class SazareIshiStructure extends Structure {
    protected SazareIshiStructure(StructureSettings p_226558_) {
        super(p_226558_);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext p_226571_) {
        return Optional.empty();
    }

    @Override
    public StructureType<?> type() {
        return null;
    }
}
