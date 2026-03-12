package net.futuristicidiot.modbase.datagen;

import net.futuristicidiot.modbase.datagen.entry.StateEntry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class BlockStateGen {
    private static final List<StateEntry> PENDING = new ArrayList<>();

    protected static StateEntry simple(String name) {
        StateEntry entry = new StateEntry(name, true, null);
        PENDING.add(entry);
        return entry;
    }

    protected static StateEntry custom(String name, BiConsumer<BlockStateProvider, Block> provider) {
        StateEntry entry = new StateEntry(name, false, provider);
        PENDING.add(entry);
        return entry;
    }

    public static BlockStateProvider createProvider(DataGenerator gen, String modId, ExistingFileHelper fileHelper) {
        return new BlockStateProvider(gen.getPackOutput(), modId, fileHelper) {
            @SuppressWarnings("unchecked")
            @Override
            protected void registerStatesAndModels() {
                for (StateEntry entry : PENDING) {
                    ResourceLocation rl = ResourceLocation.fromNamespaceAndPath(modId, entry.getName());
                    Block block = ForgeRegistries.BLOCKS.getValue(rl);
                    if (block == null) continue;

                    if (entry.isSimple()) {
                        simpleBlockWithItem(block, cubeAll(block));
                    } else {
                        BiConsumer<BlockStateProvider, Block> custom =
                                (BiConsumer<BlockStateProvider, Block>) entry.getCustomProvider();
                        if (custom != null) {
                            custom.accept(this, block);
                        }
                    }
                }
            }
        };
    }
}
