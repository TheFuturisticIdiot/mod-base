package com.example.mymod.block;

import net.futuristicidiot.modbase.registry.block.BlockEntry;
import net.futuristicidiot.modbase.registry.block.BlockRegistry;

public class ModBlocks extends BlockRegistry {
    public static BlockEntry EXAMPLE_BLOCK = block("example_block");

    // No properties (defaults to empty):
    // block("basic_block")

    // With properties:
    // block("stone_block", BlockProps.create().strength(2.0f))

    // Custom class:
    // block("custom_block", CustomBlock::new)

    // Block without a BlockItem:
    // blockNoItem("no_item_block")
}
