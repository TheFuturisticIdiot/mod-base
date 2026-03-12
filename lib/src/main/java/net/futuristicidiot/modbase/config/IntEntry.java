package net.futuristicidiot.modbase.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class IntEntry extends ConfigEntry<Integer> {
    private final int min;
    private final int max;

    public IntEntry(String name, int defaultValue, int min, int max) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
    }

    @Override
    public void build(ForgeConfigSpec.Builder builder) {
        configValue = builder.defineInRange(name, defaultValue, min, max);
    }
}
