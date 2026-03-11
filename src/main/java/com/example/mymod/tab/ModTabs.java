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
                    ModBlocks.EXAMPLE_BLOCK
            );

    // .icon() accepts an ItemEntry or BlockEntry
    // .items() accepts any mix of ItemEntry and BlockEntry
}
