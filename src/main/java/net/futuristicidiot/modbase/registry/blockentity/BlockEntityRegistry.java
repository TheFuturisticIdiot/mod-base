package net.futuristicidiot.modbase.registry.blockentity;

import net.futuristicidiot.modbase.registry.block.BlockEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockEntityRegistry {
    private static final List<PendingBlockEntity<?>> PENDING = new ArrayList<>();

    protected static <T extends BlockEntity> BlockEntityEntry<T> blockEntity(
            String name, BlockEntityFactory<T> factory, BlockEntry... blocks) {
        BlockEntityEntry<T> entry = new BlockEntityEntry<>();
        PENDING.add(new PendingBlockEntity<>(name, entry, factory, blocks));
        return entry;
    }

    @SuppressWarnings("unchecked")
    public static void init(String modId, IEventBus modBus) {
        DeferredRegister<BlockEntityType<?>> register = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, modId);

        for (PendingBlockEntity<?> pending : PENDING) {
            registerEntry(register, (PendingBlockEntity<BlockEntity>) pending);
        }

        register.register(modBus);
    }

    @SuppressWarnings("unchecked")
    private static <T extends BlockEntity> void registerEntry(
            DeferredRegister<BlockEntityType<?>> register, PendingBlockEntity<T> pending) {

        RegistryObject<BlockEntityType<T>> regObj = (RegistryObject<BlockEntityType<T>>) (RegistryObject<?>)
                register.register(pending.name, () -> {
                    Block[] resolvedBlocks = new Block[pending.blocks.length];
                    for (int i = 0; i < pending.blocks.length; i++) {
                        resolvedBlocks[i] = pending.blocks[i].get();
                    }
                    return BlockEntityType.Builder.of(
                            (BlockPos pos, BlockState state) -> pending.factory.create(pending.entry.get(), pos, state),
                            resolvedBlocks
                    ).build(null);
                });

        pending.entry.bind(regObj);
    }

    @FunctionalInterface
    public interface BlockEntityFactory<T extends BlockEntity> {
        T create(BlockEntityType<T> type, BlockPos pos, BlockState state);
    }

    private record PendingBlockEntity<T extends BlockEntity>(
            String name, BlockEntityEntry<T> entry, BlockEntityFactory<T> factory, BlockEntry[] blocks) {}
}
