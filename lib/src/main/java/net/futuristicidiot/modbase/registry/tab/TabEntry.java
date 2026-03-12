package net.futuristicidiot.modbase.registry.tab;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class TabEntry implements Supplier<CreativeModeTab> {
    private RegistryObject<CreativeModeTab> registryObject;
    private String name;
    private Supplier<ItemStack> iconSupplier;
    private Supplier<?>[] itemEntries;

    public void setName(String name) {
        this.name = name;
    }

    public String getTabName() {
        return name;
    }

    public void bind(RegistryObject<CreativeModeTab> registryObject) {
        this.registryObject = registryObject;
    }

    public TabEntry icon(Supplier<?> iconEntry) {
        this.iconSupplier = () -> {
            Object obj = iconEntry.get();
            if (obj instanceof Item item) {
                return new ItemStack(item);
            } else if (obj instanceof Block block) {
                return new ItemStack(block);
            }
            throw new IllegalArgumentException("Icon must be an ItemEntry or BlockEntry");
        };
        return this;
    }

    public TabEntry items(Supplier<?>... entries) {
        this.itemEntries = entries;
        return this;
    }

    public Supplier<ItemStack> getIconSupplier() {
        return iconSupplier;
    }

    public Supplier<?>[] getItemEntries() {
        return itemEntries;
    }

    @Override
    public CreativeModeTab get() {
        return registryObject.get();
    }
}
