package net.futuristicidiot.modbase.registry.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ItemEntry implements Supplier<Item> {
    private RegistryObject<Item> registryObject;
    private final List<TagKey<Item>> tags = new ArrayList<>();

    @SafeVarargs
    public final ItemEntry tags(TagKey<Item>... tags) {
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }

    public List<TagKey<Item>> getTags() {
        return tags;
    }

    public void bind(RegistryObject<Item> registryObject) {
        this.registryObject = registryObject;
    }

    @Override
    public Item get() {
        return registryObject.get();
    }

    public RegistryObject<Item> getRegistryObject() {
        return registryObject;
    }
}
