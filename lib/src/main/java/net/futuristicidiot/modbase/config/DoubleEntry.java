package net.futuristicidiot.modbase.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class DoubleEntry extends ConfigEntry<Double> {
    private final double min;
    private final double max;

    public DoubleEntry(String name, double defaultValue, double min, double max) {
        super(name, defaultValue);
        this.min = min;
        this.max = max;
    }

    @Override
    public void build(ForgeConfigSpec.Builder builder) {
        configValue = applyComment(builder).defineInRange(name, defaultValue, min, max);
    }
}
