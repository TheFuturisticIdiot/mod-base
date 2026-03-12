package net.futuristicidiot.modbase.registry.tab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class TabRegistry {
    private static final List<TabEntry> PENDING = new ArrayList<>();

    protected static TabEntry tab(String name) {
        TabEntry entry = new TabEntry();
        entry.setName(name);
        PENDING.add(entry);
        return entry;
    }

    public static void init(String modId, IEventBus modBus) {
        DeferredRegister<CreativeModeTab> register = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modId);

        for (TabEntry entry : PENDING) {
            RegistryObject<CreativeModeTab> regObj = register.register(entry.getTabName(), () -> {
                CreativeModeTab.Builder builder = CreativeModeTab.builder()
                        .title(Component.translatable("creativetab." + modId + "." + entry.getTabName()));

                Supplier<ItemStack> iconSupplier = entry.getIconSupplier();
                if (iconSupplier != null) {
                    builder.icon(iconSupplier);
                }

                Supplier<?>[] items = entry.getItemEntries();
                if (items != null && items.length > 0) {
                    builder.displayItems((params, output) -> {
                        for (Supplier<?> supplier : items) {
                            Object obj = supplier.get();
                            if (obj instanceof Item item) {
                                output.accept(item);
                            } else if (obj instanceof Block block) {
                                output.accept(block);
                            }
                        }
                    });
                }

                return builder.build();
            });
            entry.bind(regObj);
        }

        register.register(modBus);
    }

    public static List<String> getRegisteredNames() {
        return PENDING.stream().map(TabEntry::getTabName).toList();
    }
}
