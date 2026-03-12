package net.futuristicidiot.modbase.datagen;

import net.futuristicidiot.modbase.registry.block.BlockEntry;
import net.futuristicidiot.modbase.registry.block.BlockRegistry;
import net.futuristicidiot.modbase.Tags;
import net.futuristicidiot.modbase.registry.item.ItemEntry;
import net.futuristicidiot.modbase.registry.item.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

/**
 * Auto-generates block and item tag JSON files from tags declared on BlockEntry and ItemEntry.
 */
public class TagGen {

    public static BlockTagsProvider createBlockTagProvider(DataGenerator gen, String modId,
            CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper) {
        return new BlockTagsProvider(gen.getPackOutput(), lookupProvider, modId, fileHelper) {
            @Override
            protected void addTags(HolderLookup.Provider provider) {
                for (BlockEntry entry : BlockRegistry.getEntries()) {
                    if (entry.getTags().isEmpty()) continue;
                    Block block = entry.get();
                    for (TagKey<Block> tag : entry.getTags()) {
                        TagKey<Block> resolved = Tags.resolveBlock(tag, modId);
                        tag(resolved).add(block);
                    }
                }
            }
        };
    }

    public static ItemTagsProvider createItemTagProvider(DataGenerator gen, String modId,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            BlockTagsProvider blockTagsProvider, ExistingFileHelper fileHelper) {
        return new ItemTagsProvider(gen.getPackOutput(), lookupProvider,
                blockTagsProvider.contentsGetter(), modId, fileHelper) {
            @Override
            protected void addTags(HolderLookup.Provider provider) {
                for (ItemEntry entry : ItemRegistry.getEntries()) {
                    if (entry.getTags().isEmpty()) continue;
                    Item item = entry.get();
                    for (TagKey<Item> tag : entry.getTags()) {
                        TagKey<Item> resolved = Tags.resolveItem(tag, modId);
                        tag(resolved).add(item);
                    }
                }
            }
        };
    }
}
