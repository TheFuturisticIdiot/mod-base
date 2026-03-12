package com.example.mymod.datagen;

import com.example.mymod.block.ModBlocks;
import net.futuristicidiot.modbase.datagen.LootGen;

public class ModBlockLoot extends LootGen.BlockLoot {
    @Override
    protected void generate() {
        dropsSelf(ModBlocks.EXAMPLE_BLOCK);
        dropsSelf(ModBlocks.CRATE);
        dropsSelf(ModBlocks.CRUSHER);

        // ---- Palm wood material pack ----
        dropsSelf(ModBlocks.EVIL_PLANKS);
        dropsSelf(ModBlocks.EVIL_LOG);
        dropsSlab(ModBlocks.EVIL_SLAB);
        dropsSelf(ModBlocks.EVIL_STAIRS);
        dropsSelf(ModBlocks.EVIL_FENCE);
        dropsSelf(ModBlocks.EVIL_FENCE_GATE);
        dropsDoor(ModBlocks.EVIL_DOOR);
        dropsSelf(ModBlocks.EVIL_TRAPDOOR);
        dropsSelf(ModBlocks.EVIL_BUTTON);
        dropsSelf(ModBlocks.EVIL_PRESSURE_PLATE);

        // Drop a different item:
        // dropsItem(ModBlocks.EXAMPLE_ORE, ModItems.EXAMPLE_GEM);

        // Silk touch behaviour:
        // dropsSilkTouch(ModBlocks.GLASS_BLOCK);
    }
}
