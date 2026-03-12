package com.example.mymod.datagen;

import com.example.mymod.block.ModBlocks;
import net.futuristicidiot.modbase.datagen.BlockStateGen;
import net.futuristicidiot.modbase.datagen.entry.StateEntry;

public class ModBlockStates extends BlockStateGen {
    static StateEntry EXAMPLE_BLOCK = simple(ModBlocks.EXAMPLE_BLOCK);
    static StateEntry CRATE = simple(ModBlocks.CRATE);
    static StateEntry CRUSHER = simple(ModBlocks.CRUSHER);

    // ---- Example wood material pack ----
    static StateEntry EVIL_PLANKS = simple(ModBlocks.EVIL_PLANKS);
    static StateEntry EVIL_LOG = pillar(ModBlocks.EVIL_LOG);
    static StateEntry EVIL_SLAB = slab(ModBlocks.EVIL_SLAB, ModBlocks.EVIL_PLANKS);
    static StateEntry EVIL_STAIRS = stairs(ModBlocks.EVIL_STAIRS, ModBlocks.EVIL_PLANKS);
    static StateEntry EVIL_FENCE = fence(ModBlocks.EVIL_FENCE, ModBlocks.EVIL_PLANKS);
    static StateEntry EVIL_FENCE_GATE = fenceGate(ModBlocks.EVIL_FENCE_GATE, ModBlocks.EVIL_PLANKS);
    static StateEntry EVIL_DOOR = door(ModBlocks.EVIL_DOOR);
    static StateEntry EVIL_TRAPDOOR = trapdoor(ModBlocks.EVIL_TRAPDOOR);
    static StateEntry EVIL_BUTTON = button(ModBlocks.EVIL_BUTTON, ModBlocks.EVIL_PLANKS);
    static StateEntry EVIL_PRESSURE_PLATE = pressurePlate(ModBlocks.EVIL_PRESSURE_PLATE, ModBlocks.EVIL_PLANKS);

    // Texture from string path (auto-prefixed with mod id):
    // stairs(ModBlocks.DARK_STAIRS, "block/dark_wood")

    // Texture from vanilla (has colon, used as-is):
    // stairs(ModBlocks.STONE_STAIRS, "minecraft:block/stone")

    // No second arg, uses own name for texture:
    // wall(ModBlocks.STONE_WALL)

    // Cross model - flowers, saplings:
    // cross(ModBlocks.POPPY)

    // Directional - front/side/top textures:
    // orientable(ModBlocks.FURNACE)

    // Fully custom:
    // custom(ModBlocks.WEIRD_BLOCK, (provider, block) -> { ... })
}
