package net.futuristicidiot.modbase.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ListEntry<T> extends ConfigEntry<List<? extends T>> {

    public ListEntry(String name, List<? extends T> defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public void build(ForgeConfigSpec.Builder builder) {
        configValue = applyComment(builder).defineList(name, defaultValue, e -> true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<? extends T> get() {
        return (List<? extends T>) configValue.get();
    }
}
