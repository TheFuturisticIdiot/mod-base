package net.futuristicidiot.modbase.datagen.entry;

import net.futuristicidiot.modbase.registry.block.BlockEntry;

import java.util.function.BiConsumer;

/**
 * Represents a queued block state for datagen.
 */
public class StateEntry {
    private final BlockEntry block;
    private final Shape shape;
    private final String texture; // null = derive from block name
    private final BiConsumer<?, ?> customProvider;

    public enum Shape {
        SIMPLE, SLAB, STAIRS, WALL, FENCE, FENCE_GATE,
        DOOR, TRAPDOOR, BUTTON, PRESSURE_PLATE,
        PILLAR, CROSS, ORIENTABLE, CUSTOM
    }

    public StateEntry(BlockEntry block, Shape shape, String texture, BiConsumer<?, ?> customProvider) {
        this.block = block;
        this.shape = shape;
        this.texture = texture;
        this.customProvider = customProvider;
    }

    public BlockEntry getBlock() {
        return block;
    }

    public Shape getShape() {
        return shape;
    }

    public String getTexture() {
        return texture;
    }

    public BiConsumer<?, ?> getCustomProvider() {
        return customProvider;
    }
}
