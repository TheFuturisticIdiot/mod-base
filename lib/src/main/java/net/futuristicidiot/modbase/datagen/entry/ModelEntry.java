package net.futuristicidiot.modbase.datagen.entry;

/**
 * Represents a queued item model for datagen. Holds the item name and model type.
 */
public class ModelEntry {
    private final String name;
    private final String type;

    public ModelEntry(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
