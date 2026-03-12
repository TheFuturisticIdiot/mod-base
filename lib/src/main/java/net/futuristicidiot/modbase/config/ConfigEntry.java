package net.futuristicidiot.modbase.config;

import net.minecraftforge.common.ForgeConfigSpec;

public abstract class ConfigEntry<T> {
    protected final String name;
    protected final T defaultValue;
    protected ForgeConfigSpec.ConfigValue<T> configValue;

    protected ConfigEntry(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public abstract void build(ForgeConfigSpec.Builder builder);

    public T get() {
        return configValue.get();
    }
}
