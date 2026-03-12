package net.futuristicidiot.modbase;

import com.mojang.logging.LogUtils;
import net.futuristicidiot.modbase.config.ConfigBase;
import net.futuristicidiot.modbase.datagen.*;
import net.futuristicidiot.modbase.registry.block.BlockRegistry;
import net.futuristicidiot.modbase.registry.blockentity.BlockEntityRegistry;
import net.futuristicidiot.modbase.registry.item.ItemRegistry;
import net.futuristicidiot.modbase.registry.menu.MenuRegistry;
import net.futuristicidiot.modbase.registry.packet.PacketRegistry;
import net.futuristicidiot.modbase.registry.sound.SoundRegistry;
import net.futuristicidiot.modbase.registry.tab.TabRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ModBase {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static String modId;
    private final List<Class<? extends ConfigBase>> configClasses = new ArrayList<>();
    private final List<Class<? extends RecipeGen>> recipeClasses = new ArrayList<>();
    private final List<Class<? extends LootGen.BlockLoot>> blockLootClasses = new ArrayList<>();
    private final List<Class<? extends LangGen>> langClasses = new ArrayList<>();

    public ModBase() {
        modId = getModId();
        LOGGER.info("[ModBase] Initialising mod: {}", modId);

        @SuppressWarnings("removal")
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        register();

        ItemRegistry.init(modId, modBus);
        BlockRegistry.init(modId, modBus);
        BlockEntityRegistry.init(modId, modBus);
        MenuRegistry.init(modId, modBus);
        SoundRegistry.init(modId, modBus);
        TabRegistry.init(modId, modBus);
        PacketRegistry.init(modId, modBus);

        for (Class<? extends ConfigBase> configClass : configClasses) {
            ConfigBase.initConfig(configClass);
            LOGGER.info("[ModBase] Registered config: {}", configClass.getSimpleName());
        }

        datagen();

        modBus.addListener(this::gatherData);

        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("[ModBase] Init complete for: {}", modId);
    }

    protected abstract String getModId();

    protected abstract void register();

    protected void datagen() {}

    @SuppressWarnings("unchecked")
    protected void use(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            try {
                Class.forName(clazz.getName(), true, clazz.getClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Failed to initialise class: " + clazz.getSimpleName(), e);
            }
            if (ConfigBase.class.isAssignableFrom(clazz)) {
                configClasses.add((Class<? extends ConfigBase>) clazz);
            }
            if (RecipeGen.class.isAssignableFrom(clazz)) {
                recipeClasses.add((Class<? extends RecipeGen>) clazz);
            }
            if (LootGen.BlockLoot.class.isAssignableFrom(clazz)) {
                blockLootClasses.add((Class<? extends LootGen.BlockLoot>) clazz);
            }
            if (LangGen.class.isAssignableFrom(clazz)) {
                langClasses.add((Class<? extends LangGen>) clazz);
            }
            LOGGER.info("[ModBase] Loaded: {}", clazz.getSimpleName());
        }
    }

    public static String id() {
        return modId;
    }

    private void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

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
