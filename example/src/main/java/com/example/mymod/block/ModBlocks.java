package com.example.mymod.block;

import com.example.mymod.block.custom.CrateBlock;
import com.example.mymod.block.custom.CrusherBlock;
import net.futuristicidiot.modbase.registry.block.BlockEntry;
import net.futuristicidiot.modbase.registry.block.BlockRegistry;
import net.futuristicidiot.modbase.Tags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModBlocks extends BlockRegistry {
    public static BlockEntry EXAMPLE_BLOCK = block("example_block")
            .tags(Tags.MINEABLE_PICKAXE);

    public static BlockEntry CRATE = block("crate", CrateBlock::new)
            .tags(Tags.MINEABLE_AXE);

    public static BlockEntry CRUSHER = block("crusher", CrusherBlock::new)
            .tags(Tags.MINEABLE_PICKAXE, Tags.NEEDS_STONE);

    // ---- Example wood material pack ----
    private static final BlockBehaviour.Properties EVIL = BlockBehaviour.Properties.of().strength(2.0f, 3.0f);
    private static final BlockBehaviour.Properties EVIL_NOOCC = BlockBehaviour.Properties.of().strength(3.0f).noOcclusion();
    private static final TagKey<Block> EVIL_TOOL = Tags.MINEABLE_AXE;

    public static BlockEntry EVIL_PLANKS = block("evil_planks", EVIL).tags(EVIL_TOOL, Tags.PLANKS);
    public static BlockEntry EVIL_LOG = log("evil_log", EVIL).tags(EVIL_TOOL);

    public static BlockEntry EVIL_SLAB = slab("evil_slab", EVIL).tags(EVIL_TOOL);
    public static BlockEntry EVIL_STAIRS = stairs("evil_stairs", EVIL_PLANKS, EVIL).tags(EVIL_TOOL);

    public static BlockEntry EVIL_FENCE = fence("evil_fence", EVIL).tags(EVIL_TOOL);
    public static BlockEntry EVIL_FENCE_GATE = fenceGate("evil_fence_gate", EVIL, WoodType.OAK).tags(EVIL_TOOL);

    public static BlockEntry EVIL_DOOR = door("evil_door", EVIL_NOOCC, BlockSetType.OAK).tags(EVIL_TOOL);
    public static BlockEntry EVIL_TRAPDOOR = trapdoor("evil_trapdoor", EVIL_NOOCC, BlockSetType.OAK).tags(EVIL_TOOL);

    public static BlockEntry EVIL_BUTTON = button("evil_button",
            EVIL_NOOCC.strength(0.5f), BlockSetType.OAK, 30, true).tags(EVIL_TOOL);
    public static BlockEntry EVIL_PRESSURE_PLATE = pressurePlate("evil_pressure_plate",
            PressurePlateBlock.Sensitivity.EVERYTHING,EVIL, BlockSetType.OAK).tags(EVIL_TOOL);

    // No properties (defaults to empty):
    // block("basic_block")

    // With properties:
    // block("stone_block", BlockProps.create().strength(2.0f))

    // With tags:
    // block("stone_block", BlockProps.create().strength(2.0f)).tags(Tags.MINEABLE_PICKAXE)

    // Ore with forge tag:
    // block("example_ore").tags(Tags.MINEABLE_PICKAXE, Tags.NEEDS_IRON, Tags.ORES)


    // Custom mod tag (auto-prefixed with mod id):
    // block("magic_block").tags(Tags.MINEABLE_PICKAXE, Tags.block("magical"))

    // Explicit namespace tag:
    // block("compat_block").tags(Tags.block("othermod:special_blocks"))

    // Custom class:
    // block("custom_block", CustomBlock::new)

    // Block without a BlockItem:
    // blockNoItem("no_item_block")
}

