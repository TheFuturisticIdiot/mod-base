package net.futuristicidiot.modbase.registry.block;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class BlockEntry implements Supplier<Block> {
    private RegistryObject<Block> registryObject;
    private RegistryObject<Item> blockItemObject;
    private final List<TagKey<Block>> tags = new ArrayList<>();

    @SafeVarargs
    public final BlockEntry tags(TagKey<Block>... tags) {
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }

    public List<TagKey<Block>> getTags() {
        return tags;
    }

    public void bind(RegistryObject<Block> registryObject) {
        this.registryObject = registryObject;
    }

    public void bindItem(RegistryObject<Item> blockItemObject) {
        this.blockItemObject = blockItemObject;
    }

    @Override
    public Block get() {
        return registryObject.get();
    }

    public Item asItem() {
        if (blockItemObject == null) {
            throw new IllegalStateException("Block was registered without a BlockItem");
        }
        return blockItemObject.get();
    }

    public RegistryObject<Block> getRegistryObject() {
        return registryObject;
    }
}
