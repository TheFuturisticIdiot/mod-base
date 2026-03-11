package net.futuristicidiot.modbase.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class BoolEntry extends ConfigEntry<Boolean> {

    public BoolEntry(String name, boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public void build(ForgeConfigSpec.Builder builder) {
        configValue = builder.define(name, defaultValue);
    }
}
