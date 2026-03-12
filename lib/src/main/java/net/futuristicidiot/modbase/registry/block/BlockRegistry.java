package net.futuristicidiot.modbase.registry.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class BlockRegistry {
    private static final List<PendingBlock> PENDING = new ArrayList<>();
    private static DeferredRegister<Block> register;

    protected static BlockEntry block(String name) {
        return registerBlock(name, () -> new Block(BlockBehaviour.Properties.of()), true);
    }

    protected static BlockEntry block(String name, BlockBehaviour.Properties properties) {
        return block(name, () -> new Block(properties));
    }

    protected static BlockEntry block(String name, Supplier<? extends Block> supplier) {
        return registerBlock(name, supplier, true);
    }

    protected static BlockEntry blockNoItem(String name) {
        return registerBlock(name, () -> new Block(BlockBehaviour.Properties.of()), false);
    }

    protected static BlockEntry blockNoItem(String name, BlockBehaviour.Properties properties) {
        return blockNoItem(name, () -> new Block(properties));
    }

    protected static BlockEntry blockNoItem(String name, Supplier<? extends Block> supplier) {
        return registerBlock(name, supplier, false);
    }

    private static BlockEntry registerBlock(String name, Supplier<? extends Block> supplier, boolean withItem) {
        BlockEntry entry = new BlockEntry();
        PENDING.add(new PendingBlock(name, entry, supplier, withItem));
        return entry;
    }

    public static void init(String modId, IEventBus modBus) {
        register = DeferredRegister.create(ForgeRegistries.BLOCKS, modId);
        DeferredRegister<Item> itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, modId);

        for (PendingBlock pending : PENDING) {
            RegistryObject<Block> regObj = register.register(pending.name, pending.supplier);
            pending.entry.bind(regObj);

            if (pending.withItem) {
                RegistryObject<Item> itemObj = itemRegister.register(pending.name,
                        () -> new BlockItem(pending.entry.get(), new Item.Properties()));
                pending.entry.bindItem(itemObj);
            }
        }

        register.register(modBus);
        itemRegister.register(modBus);
    }

    public static List<String> getRegisteredNames() {
        return PENDING.stream().map(p -> p.name).toList();
    }

    private record PendingBlock(String name, BlockEntry entry, Supplier<? extends Block> supplier, boolean withItem) {
    }
}
