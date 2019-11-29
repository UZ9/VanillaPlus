package com.yerti.vanillaplus.structures;

import com.yerti.vanillaplus.structures.generators.CoalGenerator;

public enum StructureType {

    COALGENERATORS(CoalGenerator.class);

    Class<? extends Structure> structureParent;

    StructureType(Class<? extends Structure> structureParent) {
        this.structureParent = structureParent;
    }

    public Class<? extends Structure> getStructureParent() {
        return structureParent;
    }
}
