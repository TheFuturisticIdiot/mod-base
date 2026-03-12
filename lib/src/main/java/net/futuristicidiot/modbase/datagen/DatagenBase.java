package net.futuristicidiot.modbase.datagen;

import net.futuristicidiot.modbase.ModBase;
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
 * Base class for datagen registration. User creates a subclass annotated with
 * {@code @Mod.EventBusSubscriber(modid = "yourmod", bus = Mod.EventBusSubscriber.Bus.MOD)},
 * lists datagen classes in the constructor via use(), and adds a standard
 * {@code @SubscribeEvent} method that calls run().
 */
public abstract class DatagenBase {
    private final List<Class<?>> classes = new ArrayList<>();

    protected void use(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            try {
                Class.forName(clazz.getName(), true, clazz.getClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Failed to load datagen class: " + clazz.getSimpleName(), e);
            }
            this.classes.add(clazz);
        }
    }

    @SuppressWarnings("unchecked")
    protected void run(GatherDataEvent event) {
        String modId = ModBase.id();
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        List<Class<? extends RecipeGen>> recipeClasses = new ArrayList<>();
        List<Class<? extends LootGen.BlockLoot>> blockLootClasses = new ArrayList<>();
        List<Class<? extends LangGen>> langClasses = new ArrayList<>();

        for (Class<?> clazz : classes) {
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
        gen.addProvider(event.includeClient(), SoundGen.createProvider(gen, modId, fileHelper));

        for (Class<? extends RecipeGen> clazz : recipeClasses) {
            try {
                clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to init recipes: " + clazz.getSimpleName(), e);
            }
        }
        gen.addProvider(event.includeServer(), RecipeGen.createProvider(gen, modId));

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

        for (Class<? extends LangGen> clazz : langClasses) {
            try {
                clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to init lang: " + clazz.getSimpleName(), e);
            }
        }

        for (LanguageProvider provider : LangGen.createProviders(gen, modId)) {
            gen.addProvider(event.includeClient(), provider);
        }
    }
}
