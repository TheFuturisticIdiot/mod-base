package net.futuristicidiot.modbase.registry.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockEntityEntry<T extends net.minecraft.world.level.block.entity.BlockEntity> implements Supplier<BlockEntityType<T>> {
    private RegistryObject<BlockEntityType<T>> registryObject;

    public void bind(RegistryObject<BlockEntityType<T>> registryObject) {
        this.registryObject = registryObject;
    }

    @Override
    public BlockEntityType<T> get() {
        return registryObject.get();
    }

    public RegistryObject<BlockEntityType<T>> getRegistryObject() {
        return registryObject;
    }
}
