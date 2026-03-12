package com.example.mymod.item;

import net.futuristicidiot.modbase.registry.item.ItemEntry;
import net.futuristicidiot.modbase.registry.item.ItemProps;
import net.futuristicidiot.modbase.registry.item.ItemRegistry;

public class ModItems extends ItemRegistry {
    public static ItemEntry EXAMPLE_ITEM = item("example_item");
    public static ItemEntry ANOTHER_ITEM = item("another_item", ItemProps.create().stacksTo(16));

    // No properties (defaults to empty):
    // item("basic_item")

    // With properties:
    // item("fancy_item", ItemProps.create().stacksTo(16))

    // Custom class:
    // item("custom_item", CustomItem::new)

    // With forge item tag:
    // item("ruby").tags(Tags.GEMS)

    // With multiple tags:
    // item("magic_ingot").tags(Tags.INGOTS, Tags.item("magical_materials"))

    // Explicit namespace tag:
    // item("compat_item").tags(Tags.item("othermod:special_items"))
}
