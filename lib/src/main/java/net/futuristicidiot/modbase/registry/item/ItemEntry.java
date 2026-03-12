package net.futuristicidiot.modbase.registry.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemEntry implements Supplier<Item> {
    private RegistryObject<Item> registryObject;

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
