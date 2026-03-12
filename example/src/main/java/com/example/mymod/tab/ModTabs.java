package com.example.mymod.tab;

import com.example.mymod.block.ModBlocks;
import com.example.mymod.item.ModItems;
import net.futuristicidiot.modbase.registry.tab.TabEntry;
import net.futuristicidiot.modbase.registry.tab.TabRegistry;

public class ModTabs extends TabRegistry {
    public static TabEntry MAIN_TAB = tab("main_tab")
            .icon(ModItems.EXAMPLE_ITEM)
            .items(
                    ModItems.EXAMPLE_ITEM,
                    ModItems.ANOTHER_ITEM,
                    ModBlocks.EXAMPLE_BLOCK,

                    ModBlocks.CRATE,
                    ModBlocks.CRUSHER,

                    ModBlocks.EVIL_PLANKS,
                    ModBlocks.EVIL_LOG,
                    ModBlocks.EVIL_SLAB,
                    ModBlocks.EVIL_STAIRS,
                    ModBlocks.EVIL_FENCE,
                    ModBlocks.EVIL_FENCE_GATE,
                    ModBlocks.EVIL_DOOR,
                    ModBlocks.EVIL_TRAPDOOR,
                    ModBlocks.EVIL_PRESSURE_PLATE,
                    ModBlocks.EVIL_BUTTON
            );

    // .icon() accepts an ItemEntry or BlockEntry
    // .items() accepts any mix of ItemEntry and BlockEntry
}
