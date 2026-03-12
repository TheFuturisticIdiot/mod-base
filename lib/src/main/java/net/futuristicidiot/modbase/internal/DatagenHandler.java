package net.futuristicidiot.modbase.internal;

import net.futuristicidiot.modbase.datagen.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handles datagen in a separate class so that datagen-only classes
 * (DataGenerator, LootTableProvider, etc) are not loaded at runtime.
 * This class is only loaded via reflection when the GatherDataEvent fires.
 */
public class DatagenHandler {

    @SuppressWarnings("unchecked")
    public static void handle(GatherDataEvent event, String modId, List<Class<?>> allClasses) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        List<Class<? extends RecipeGen>> recipeClasses = new ArrayList<>();
        List<Class<? extends LootGen.BlockLoot>> blockLootClasses = new ArrayList<>();
        List<Class<? extends LangGen>> langClasses = new ArrayList<>();

        for (Class<?> clazz : allClasses) {
            if (RecipeGen.class.isAssignableFrom(clazz)) {
                recipeClasses.add((Class<? extends RecipeGen>) clazz);
            }
            if (LootGen.BlockLoot.class.isAssignableFrom(clazz)) {
                blockLootClasses.add((Class<? extends LootGen.BlockLoot>) clazz);
            }
            if (LangGen.class.isAssignableFrom(clazz)) {
                langClasses.add((Class<? extends LangGen>) clazz);
            }
        }

        gen.addProvider(event.includeClient(), ItemModelGen.createProvider(gen, modId, fileHelper));
        gen.addProvider(event.includeClient(), BlockStateGen.createProvider(gen, modId, fileHelper));

        // Instantiate recipe classes
        for (Class<? extends RecipeGen> clazz : recipeClasses) {
            try {
                clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to init recipes: " + clazz.getSimpleName(), e);
            }
        }
        gen.addProvider(event.includeServer(), RecipeGen.createProvider(gen, modId));

        // Block loot
        for (Class<? extends LootGen.BlockLoot> clazz : blockLootClasses) {
            gen.addProvider(event.includeServer(), new LootTableProvider(gen.getPackOutput(),
                    Collections.emptySet(),
                    List.of(new LootTableProvider.SubProviderEntry(() -> {
                        try {
                            return clazz.getDeclaredConstructor().newInstance();
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to create loot provider: " + clazz.getSimpleName(), e);
                        }
                    }, LootContextParamSets.BLOCK))));
        }

        // Instantiate lang classes so their constructors queue overrides
        for (Class<? extends LangGen> clazz : langClasses) {
            try {
                clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to init lang: " + clazz.getSimpleName(), e);
            }
        }

        // Create lang providers (en_us auto-generated + any other locales)
        for (LanguageProvider provider : LangGen.createProviders(gen, modId)) {
            gen.addProvider(event.includeClient(), provider);
        }
    }
}
