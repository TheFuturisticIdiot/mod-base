package net.futuristicidiot.modbase.registry.block;

import net.futuristicidiot.modbase.Tags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
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

    // ---- Generic ----

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

    // ---- Shape shortcuts (auto-tagged) ----

    protected static BlockEntry slab(String name, BlockBehaviour.Properties properties) {
        return registerBlock(name, () -> new SlabBlock(properties), true).tags(Tags.SLABS);
    }

    protected static BlockEntry stairs(String name, BlockEntry base, BlockBehaviour.Properties properties) {
        return registerBlock(name, () -> new StairBlock(() -> base.get().defaultBlockState(), properties), true).tags(Tags.STAIRS);
    }

    protected static BlockEntry wall(String name, BlockBehaviour.Properties properties) {
        return registerBlock(name, () -> new WallBlock(properties), true).tags(Tags.WALLS);
    }

    protected static BlockEntry fence(String name, BlockBehaviour.Properties properties) {
        return registerBlock(name, () -> new FenceBlock(properties), true).tags(Tags.FENCES);
    }

    protected static BlockEntry fenceGate(String name, BlockBehaviour.Properties properties, WoodType woodType) {
        return registerBlock(name, () -> new FenceGateBlock(properties, woodType), true);
    }

    protected static BlockEntry door(String name, BlockBehaviour.Properties properties, BlockSetType blockSetType) {
        return registerBlock(name, () -> new DoorBlock(properties, blockSetType), true);
    }

    protected static BlockEntry trapdoor(String name, BlockBehaviour.Properties properties, BlockSetType blockSetType) {
        return registerBlock(name, () -> new TrapDoorBlock(properties, blockSetType), true);
    }

    protected static BlockEntry log(String name, BlockBehaviour.Properties properties) {
        return registerBlock(name, () -> new RotatedPillarBlock(properties), true).tags(Tags.LOGS);
    }

    protected static BlockEntry button(String name, BlockBehaviour.Properties properties, BlockSetType blockSetType, int ticksToStayPressed, boolean arrowsCanPress) {
        return registerBlock(name, () -> new ButtonBlock(properties.noCollission(), blockSetType, ticksToStayPressed, arrowsCanPress), true);
    }

    protected static BlockEntry pressurePlate(String name, PressurePlateBlock.Sensitivity sensitivity, BlockBehaviour.Properties properties, BlockSetType blockSetType) {
        return registerBlock(name, () -> new PressurePlateBlock(sensitivity, properties.noCollission(), blockSetType), true);
    }

    // ---- Internal ----

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

    public static List<BlockEntry> getEntries() {
        return PENDING.stream().map(p -> p.entry).toList();
    }

    private record PendingBlock(String name, BlockEntry entry, Supplier<? extends Block> supplier, boolean withItem) {
    }
}
