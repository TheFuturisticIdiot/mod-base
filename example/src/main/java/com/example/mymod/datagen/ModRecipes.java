package com.example.mymod.datagen;

import com.example.mymod.block.ModBlocks;
import com.example.mymod.item.ModItems;
import net.futuristicidiot.modbase.datagen.RecipeGen;
import net.minecraft.world.item.Items;

public class ModRecipes extends RecipeGen {
    public ModRecipes() {
        shaped(ModItems.EXAMPLE_ITEM)
                .pattern("XX")
                .pattern("XX")
                .define('X', Items.DIAMOND);

        shapeless(ModItems.ANOTHER_ITEM)
                .requires(Items.COAL)
                .requires(Items.FLINT);

        // ---- Example wood recipes ----

        // Log -> 4 planks
        shapeless(ModBlocks.EVIL_PLANKS)
                .requires(ModBlocks.EVIL_LOG)
                .count(4);

        // 3 planks -> 6 slabs
        shaped(ModBlocks.EVIL_SLAB)
                .pattern("PPP")
                .define('P', ModBlocks.EVIL_PLANKS)
                .count(6);

        // L-shape -> 4 stairs
        shaped(ModBlocks.EVIL_STAIRS)
                .pattern("P  ")
                .pattern("PP ")
                .pattern("PPP")
                .define('P', ModBlocks.EVIL_PLANKS)
                .count(4);

        // Plank-stick-plank × 2 -> 3 fences
        shaped(ModBlocks.EVIL_FENCE)
                .pattern("PSP")
                .pattern("PSP")
                .define('P', ModBlocks.EVIL_PLANKS)
                .define('S', Items.STICK)
                .count(3);

        // Stick-plank-stick × 2 -> 1 fence gate
        shaped(ModBlocks.EVIL_FENCE_GATE)
                .pattern("SPS")
                .pattern("SPS")
                .define('P', ModBlocks.EVIL_PLANKS)
                .define('S', Items.STICK);

        // 2×3 planks -> 3 doors
        shaped(ModBlocks.EVIL_DOOR)
                .pattern("PP")
                .pattern("PP")
                .pattern("PP")
                .define('P', ModBlocks.EVIL_PLANKS)
                .count(3);

        // 3×2 planks -> 2 trapdoors
        shaped(ModBlocks.EVIL_TRAPDOOR)
                .pattern("PPP")
                .pattern("PPP")
                .define('P', ModBlocks.EVIL_PLANKS)
                .count(2);

        // 1 plank -> 1 button
        shapeless(ModBlocks.EVIL_BUTTON)
                .requires(ModBlocks.EVIL_PLANKS);

        // 2 planks -> 1 pressure plate
        shaped(ModBlocks.EVIL_PRESSURE_PLATE)
                .pattern("PP")
                .define('P', ModBlocks.EVIL_PLANKS);

        // Smelting:
        // smelting(ModItems.EXAMPLE_INGOT)
        //         .input(ModBlocks.EXAMPLE_ORE)
        //         .time(200)
        //         .xp(0.7f);

        // Recipe names are auto-generated from output + type
    }
}
