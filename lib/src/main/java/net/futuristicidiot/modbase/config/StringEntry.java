package net.futuristicidiot.modbase.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class StringEntry extends ConfigEntry<String> {

    public StringEntry(String name, String defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public void build(ForgeConfigSpec.Builder builder) {
        configValue = builder.define(name, defaultValue);
    }
}
