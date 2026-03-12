package net.futuristicidiot.modbase.screen;

/**
 * Defines a single slot's position, index, and type.
 */
public record SlotDefinition(int index, int x, int y, SlotType type) {

    public static SlotDefinition input(int index, int x, int y) {
        return new SlotDefinition(index, x, y, SlotType.INPUT);
    }

    public static SlotDefinition output(int index, int x, int y) {
        return new SlotDefinition(index, x, y, SlotType.OUTPUT);
    }

    public static SlotDefinition other(int index, int x, int y) {
        return new SlotDefinition(index, x, y, SlotType.OTHER);
    }
}
