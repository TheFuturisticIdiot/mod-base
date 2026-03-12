package com.example.mymod.datagen;

import net.futuristicidiot.modbase.datagen.ItemModelGen;
import net.futuristicidiot.modbase.datagen.entry.ModelEntry;

public class ModItemModels extends ItemModelGen {
    static ModelEntry EXAMPLE_ITEM = item("example_item");
    static ModelEntry ANOTHER_ITEM = item("another_item");

    // Door items use a flat texture, not the block model
    static ModelEntry EVIL_DOOR = item("evil_door");

    // Default parent is "generated" (standard flat item)
    // For tools/weapons: item("my_sword", "handheld")
}
