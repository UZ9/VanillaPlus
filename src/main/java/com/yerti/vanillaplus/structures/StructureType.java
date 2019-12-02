package com.yerti.vanillaplus.structures;

import com.yerti.vanillaplus.structures.generators.CoalGenerator;
import com.yerti.vanillaplus.structures.storage.CraftingTerminal;

public enum StructureType {

    COAL_GENERATOR(CoalGenerator.class), CRAFTING_TERMINAL(CraftingTerminal.class);

    Class<? extends Structure> structureParent;

    StructureType(Class<? extends Structure> structureParent) {
        this.structureParent = structureParent;
    }

    public Class<? extends Structure> getStructureParent() {
        return structureParent;
    }
}
