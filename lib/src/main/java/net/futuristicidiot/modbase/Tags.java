package net.futuristicidiot.modbase;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Predefined tag constants for common block and item tags.
 * Use with BlockEntry.tags() and ItemEntry.tags().
 */
public class Tags {
    // Block tags - Mineable
    public static final TagKey<Block> MINEABLE_PICKAXE = BlockTags.MINEABLE_WITH_PICKAXE;
    public static final TagKey<Block> MINEABLE_AXE = BlockTags.MINEABLE_WITH_AXE;
    public static final TagKey<Block> MINEABLE_SHOVEL = BlockTags.MINEABLE_WITH_SHOVEL;
    public static final TagKey<Block> MINEABLE_HOE = BlockTags.MINEABLE_WITH_HOE;

    // Block tags - Tool tier
    public static final TagKey<Block> NEEDS_STONE = BlockTags.NEEDS_STONE_TOOL;
    public static final TagKey<Block> NEEDS_IRON = BlockTags.NEEDS_IRON_TOOL;
    public static final TagKey<Block> NEEDS_DIAMOND = BlockTags.NEEDS_DIAMOND_TOOL;

    // Block tags - Common vanilla
    public static final TagKey<Block> LOGS = BlockTags.LOGS;
    public static final TagKey<Block> PLANKS = BlockTags.PLANKS;
    public static final TagKey<Block> WALLS = BlockTags.WALLS;
    public static final TagKey<Block> FENCES = BlockTags.FENCES;
    public static final TagKey<Block> SLABS = BlockTags.SLABS;
    public static final TagKey<Block> STAIRS = BlockTags.STAIRS;

    // Block tags - Forge
    public static final TagKey<Block> ORES = BlockTags.create(
            ResourceLocation.fromNamespaceAndPath("forge", "ores"));

    // Item tags - Forge
    public static final TagKey<Item> GEMS = forgeItemTag("gems");
    public static final TagKey<Item> INGOTS = forgeItemTag("ingots");
    public static final TagKey<Item> NUGGETS = forgeItemTag("nuggets");
    public static final TagKey<Item> DUSTS = forgeItemTag("dusts");
    public static final TagKey<Item> RODS = forgeItemTag("rods");
    public static final TagKey<Item> RAW_MATERIALS = forgeItemTag("raw_materials");
    public static final TagKey<Item> ITEM_ORES = forgeItemTag("ores");

    // Item tags - Common vanilla
    public static final TagKey<Item> ITEM_LOGS = vanillaItemTag("logs");
    public static final TagKey<Item> ITEM_PLANKS = vanillaItemTag("planks");
    public static final TagKey<Item> COALS = vanillaItemTag("coals");

    /**
     * Create a custom block tag reference.
     * No colon = prefixed with mod id at datagen time.
     * Has colon = used as-is.
     */
    public static TagKey<Block> block(String name) {
        if (name.contains(":")) {
            return BlockTags.create(ResourceLocation.tryParse(name));
        }
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("__modid__", name));
    }

    /**
     * Create a custom item tag reference.
     * No colon = prefixed with mod id at datagen time.
     * Has colon = used as-is.
     */
    public static TagKey<Item> item(String name) {
        if (name.contains(":")) {
            return TagKey.create(net.minecraft.core.registries.Registries.ITEM, ResourceLocation.tryParse(name));
        }
        return TagKey.create(net.minecraft.core.registries.Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath("__modid__", name));
    }

    /** Resolve a block tag, replacing __modid__ placeholder with actual mod id. */
    public static TagKey<Block> resolveBlock(TagKey<Block> tag, String modId) {
        if (tag.location().getNamespace().equals("__modid__")) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(modId, tag.location().getPath()));
        }
        return tag;
    }

    /** Resolve an item tag, replacing __modid__ placeholder with actual mod id. */
    public static TagKey<Item> resolveItem(TagKey<Item> tag, String modId) {
        if (tag.location().getNamespace().equals("__modid__")) {
            return TagKey.create(net.minecraft.core.registries.Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(modId, tag.location().getPath()));
        }
        return tag;
    }

    private static TagKey<Item> forgeItemTag(String name) {
        return TagKey.create(net.minecraft.core.registries.Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath("forge", name));
    }

    private static TagKey<Item> vanillaItemTag(String name) {
        return TagKey.create(net.minecraft.core.registries.Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath("minecraft", name));
    }
}
