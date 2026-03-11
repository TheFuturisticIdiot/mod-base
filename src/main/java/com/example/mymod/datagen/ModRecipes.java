package com.example.mymod.datagen;

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

        // Smelting:
        // smelting(ModItems.EXAMPLE_INGOT)
        //         .input(ModBlocks.EXAMPLE_ORE)
        //         .time(200)
        //         .xp(0.7f);

        // Recipe names are auto-generated from output + type
    }
}
