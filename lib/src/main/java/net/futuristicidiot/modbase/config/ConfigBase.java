package net.futuristicidiot.modbase.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public abstract class ConfigBase {

    @SuppressWarnings("removal")
    public static void initConfig(Class<? extends ConfigBase> clazz) {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                if (!Modifier.isStatic(field.getModifiers())) continue;
                field.setAccessible(true);
                Object value = field.get(null);
                if (value instanceof ConfigEntry<?> entry) {
                    entry.build(builder);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to build config for field: " + field.getName(), e);
            }
        }
        ForgeConfigSpec spec = builder.build();

        if (Client.class.isAssignableFrom(clazz)) {
            ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, spec);
        } else if (Server.class.isAssignableFrom(clazz)) {
            ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, spec);
        }
    }

    // Shared factory methods
    protected static BoolEntry bool(String name, boolean defaultValue) {
        return new BoolEntry(name, defaultValue);
    }

    protected static IntEntry integer(String name, int defaultValue, int min, int max) {
        return new IntEntry(name, defaultValue, min, max);
    }

    protected static StringEntry string(String name, String defaultValue) {
        return new StringEntry(name, defaultValue);
    }

    protected static DoubleEntry decimal(String name, double defaultValue, double min, double max) {
        return new DoubleEntry(name, defaultValue, min, max);
    }

    protected static <E extends Enum<E>> EnumEntry<E> enumValue(String name, E defaultValue) {
        return new EnumEntry<>(name, defaultValue);
    }

    protected static <T> ListEntry<T> list(String name, List<? extends T> defaultValue) {
        return new ListEntry<>(name, defaultValue);
    }

    public static abstract class Client extends ConfigBase {}
    public static abstract class Server extends ConfigBase {}
}
