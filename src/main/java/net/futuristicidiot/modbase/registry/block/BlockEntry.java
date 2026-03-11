package net.futuristicidiot.modbase.registry.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockEntry implements Supplier<Block> {
    private RegistryObject<Block> registryObject;
    private RegistryObject<Item> blockItemObject;

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
