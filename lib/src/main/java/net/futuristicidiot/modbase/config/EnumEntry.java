package net.futuristicidiot.modbase.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EnumEntry<E extends Enum<E>> extends ConfigEntry<E> {

    public EnumEntry(String name, E defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public void build(ForgeConfigSpec.Builder builder) {
        configValue = applyComment(builder).defineEnum(name, defaultValue);
    }
}
