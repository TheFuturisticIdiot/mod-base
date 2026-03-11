package net.futuristicidiot.modbase.datagen;

import net.futuristicidiot.modbase.registry.block.BlockEntry;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class LootGen {

    public static abstract class BlockLoot extends BlockLootSubProvider {
        private final List<Block> knownBlocks = new ArrayList<>();

        protected BlockLoot() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        protected void dropsSelf(BlockEntry block) {
            knownBlocks.add(block.get());
            dropSelf(block.get());
        }

        protected void dropsItem(BlockEntry block, Supplier<? extends Item> item) {
            knownBlocks.add(block.get());
            dropOther(block.get(), item.get());
        }

        protected void dropsSilkTouch(BlockEntry block) {
            knownBlocks.add(block.get());
            add(block.get(), createSingleItemTableWithSilkTouch(block.get(), block.get().asItem()));
        }

        @Override
        protected abstract void generate();

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return knownBlocks;
        }
    }

    public static abstract class Entity {
        // Placeholder for entity loot, to be implemented with entity registry
    }
}
