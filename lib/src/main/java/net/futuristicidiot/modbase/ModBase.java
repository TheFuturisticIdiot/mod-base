package net.futuristicidiot.modbase;

import com.mojang.logging.LogUtils;
import net.futuristicidiot.modbase.config.ConfigBase;
import net.futuristicidiot.modbase.registry.block.BlockRegistry;
import net.futuristicidiot.modbase.registry.blockentity.BlockEntityRegistry;
import net.futuristicidiot.modbase.registry.item.ItemRegistry;
import net.futuristicidiot.modbase.registry.menu.MenuRegistry;
import net.futuristicidiot.modbase.registry.packet.PacketRegistry;
import net.futuristicidiot.modbase.registry.sound.SoundRegistry;
import net.futuristicidiot.modbase.registry.tab.TabRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class ModBase {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static String modId;
    private final List<Class<?>> configClasses = new ArrayList<>();

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

        for (Class<?> configClass : configClasses) {
            ConfigBase.initConfig(configClass.asSubclass(ConfigBase.class));
            LOGGER.info("[ModBase] Registered config: {}", configClass.getSimpleName());
        }

        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("[ModBase] Init complete for: {}", modId);
    }

    protected abstract String getModId();

    protected abstract void register();

    protected void use(Class<?>... classes) {
        for (Class<?> clazz : classes) {
            try {
                Class.forName(clazz.getName(), true, clazz.getClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Failed to initialise class: " + clazz.getSimpleName(), e);
            }
            if (ConfigBase.class.isAssignableFrom(clazz)) {
                configClasses.add(clazz);
            }
            LOGGER.info("[ModBase] Loaded: {}", clazz.getSimpleName());
        }
    }

    public static String id() {
        return modId;
    }
}
