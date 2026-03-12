package com.example.mymod.datagen;

import net.futuristicidiot.modbase.datagen.BlockStateGen;
import net.futuristicidiot.modbase.datagen.entry.StateEntry;

public class ModBlockStates extends BlockStateGen {
    static StateEntry EXAMPLE_BLOCK = simple("example_block");

    // simple() for standard cube-all blocks
    // custom() for anything else:
    // custom("custom_block", (provider, block) -> { ... })
}
