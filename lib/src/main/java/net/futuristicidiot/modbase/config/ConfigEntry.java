package net.futuristicidiot.modbase.config;

import net.minecraftforge.common.ForgeConfigSpec;

public abstract class ConfigEntry<T> {
    protected final String name;
    protected final T defaultValue;
    protected String comment;
    protected ForgeConfigSpec.ConfigValue<T> configValue;

    protected ConfigEntry(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    @SuppressWarnings("unchecked")
    public <E extends ConfigEntry<T>> E comment(String comment) {
        this.comment = comment;
        return (E) this;
    }

    protected ForgeConfigSpec.Builder applyComment(ForgeConfigSpec.Builder builder) {
        if (comment != null) {
            builder.comment(comment);
        }
        return builder;
    }

    public abstract void build(ForgeConfigSpec.Builder builder);

    public T get() {
        return configValue.get();
    }
}
