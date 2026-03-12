package net.futuristicidiot.modbase.datagen.entry;

import java.util.function.BiConsumer;

/**
 * Represents a queued block state for datagen.
 */
public class StateEntry {
    private final String name;
    private final boolean simple;
    private final BiConsumer<?, ?> customProvider;

    public StateEntry(String name, boolean simple, BiConsumer<?, ?> customProvider) {
        this.name = name;
        this.simple = simple;
        this.customProvider = customProvider;
    }

    public String getName() {
        return name;
    }

    public boolean isSimple() {
        return simple;
    }

    public BiConsumer<?, ?> getCustomProvider() {
        return customProvider;
    }
}
