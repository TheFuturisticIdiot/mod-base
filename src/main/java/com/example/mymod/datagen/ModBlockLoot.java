package com.example.mymod.datagen;

import com.example.mymod.block.ModBlocks;
import net.futuristicidiot.modbase.datagen.LootGen;

public class ModBlockLoot extends LootGen.BlockLoot {
    @Override
    protected void generate() {
        dropsSelf(ModBlocks.EXAMPLE_BLOCK);

        // Drop a different item:
        // dropsItem(ModBlocks.EXAMPLE_ORE, ModItems.EXAMPLE_GEM);

        // Silk touch behaviour:
        // dropsSilkTouch(ModBlocks.GLASS_BLOCK);
    }
}
