package net.futuristicidiot.modbase.registry.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class ItemRegistry {
    private static final List<PendingItem> PENDING = new ArrayList<>();
    private static DeferredRegister<Item> register;

    protected static ItemEntry item(String name) {
        return item(name, () -> new Item(new Item.Properties()));
    }

    protected static ItemEntry item(String name, Item.Properties properties) {
        return item(name, () -> new Item(properties));
    }

    protected static ItemEntry item(String name, Supplier<? extends Item> supplier) {
        ItemEntry entry = new ItemEntry();
        PENDING.add(new PendingItem(name, entry, supplier));
        return entry;
    }

    public static void init(String modId, IEventBus modBus) {
        register = DeferredRegister.create(ForgeRegistries.ITEMS, modId);
        for (PendingItem pending : PENDING) {
            RegistryObject<Item> regObj = register.register(pending.name, pending.supplier);
            pending.entry.bind(regObj);
        }
        register.register(modBus);
    }

    public static DeferredRegister<Item> getRegister() {
        return register;
    }

    public static List<String> getRegisteredNames() {
        return PENDING.stream().map(p -> p.name).toList();
    }

    private record PendingItem(String name, ItemEntry entry, Supplier<? extends Item> supplier) {
    }
}
