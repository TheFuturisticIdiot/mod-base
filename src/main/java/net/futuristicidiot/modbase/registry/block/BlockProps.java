package net.futuristicidiot.modbase.registry.block;

import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * Shorthand for BlockBehaviour.Properties.
 */
public class BlockProps {
    public static BlockBehaviour.Properties create() {
        return BlockBehaviour.Properties.of();
    }
}
