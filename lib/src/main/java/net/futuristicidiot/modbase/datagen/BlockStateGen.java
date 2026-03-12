package net.futuristicidiot.modbase.datagen;

import net.futuristicidiot.modbase.ModBase;
import net.futuristicidiot.modbase.datagen.entry.StateEntry;
import net.futuristicidiot.modbase.datagen.entry.StateEntry.Shape;
import net.futuristicidiot.modbase.registry.block.BlockEntry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class BlockStateGen {
    private static final List<StateEntry> PENDING = new ArrayList<>();

    // --- Simple cube ---

    protected static StateEntry simple(BlockEntry block) {
        StateEntry entry = new StateEntry(block, Shape.SIMPLE, null, null);
        PENDING.add(entry);
        return entry;
    }

    // --- Shapes with texture from another block ---

    protected static StateEntry slab(BlockEntry block, BlockEntry textureSource) {
        return addShape(block, Shape.SLAB, textureSource);
    }

    protected static StateEntry stairs(BlockEntry block, BlockEntry textureSource) {
        return addShape(block, Shape.STAIRS, textureSource);
    }

    protected static StateEntry wall(BlockEntry block, BlockEntry textureSource) {
        return addShape(block, Shape.WALL, textureSource);
    }

    protected static StateEntry fence(BlockEntry block, BlockEntry textureSource) {
        return addShape(block, Shape.FENCE, textureSource);
    }

    protected static StateEntry fenceGate(BlockEntry block, BlockEntry textureSource) {
        return addShape(block, Shape.FENCE_GATE, textureSource);
    }

    protected static StateEntry button(BlockEntry block, BlockEntry textureSource) {
        return addShape(block, Shape.BUTTON, textureSource);
    }

    protected static StateEntry pressurePlate(BlockEntry block, BlockEntry textureSource) {
        return addShape(block, Shape.PRESSURE_PLATE, textureSource);
    }

    // --- Shapes with texture from string ---

    protected static StateEntry slab(BlockEntry block, String texture) {
        return addShape(block, Shape.SLAB, texture);
    }

    protected static StateEntry stairs(BlockEntry block, String texture) {
        return addShape(block, Shape.STAIRS, texture);
    }

    protected static StateEntry wall(BlockEntry block, String texture) {
        return addShape(block, Shape.WALL, texture);
    }

    protected static StateEntry fence(BlockEntry block, String texture) {
        return addShape(block, Shape.FENCE, texture);
    }

    protected static StateEntry fenceGate(BlockEntry block, String texture) {
        return addShape(block, Shape.FENCE_GATE, texture);
    }

    protected static StateEntry button(BlockEntry block, String texture) {
        return addShape(block, Shape.BUTTON, texture);
    }

    protected static StateEntry pressurePlate(BlockEntry block, String texture) {
        return addShape(block, Shape.PRESSURE_PLATE, texture);
    }

    // --- Shapes with no texture arg (uses own name or conventions) ---

    protected static StateEntry slab(BlockEntry block) {
        return addShape(block, Shape.SLAB, (String) null);
    }

    protected static StateEntry stairs(BlockEntry block) {
        return addShape(block, Shape.STAIRS, (String) null);
    }

    protected static StateEntry wall(BlockEntry block) {
        return addShape(block, Shape.WALL, (String) null);
    }

    protected static StateEntry fence(BlockEntry block) {
        return addShape(block, Shape.FENCE, (String) null);
    }

    protected static StateEntry fenceGate(BlockEntry block) {
        return addShape(block, Shape.FENCE_GATE, (String) null);
    }

    protected static StateEntry button(BlockEntry block) {
        return addShape(block, Shape.BUTTON, (String) null);
    }

    protected static StateEntry pressurePlate(BlockEntry block) {
        return addShape(block, Shape.PRESSURE_PLATE, (String) null);
    }

    // --- Self-textured shapes ---

    protected static StateEntry door(BlockEntry block) {
        return addShape(block, Shape.DOOR, (String) null);
    }

    protected static StateEntry trapdoor(BlockEntry block) {
        return addShape(block, Shape.TRAPDOOR, (String) null);
    }

    protected static StateEntry pillar(BlockEntry block) {
        return addShape(block, Shape.PILLAR, (String) null);
    }

    protected static StateEntry cross(BlockEntry block) {
        return addShape(block, Shape.CROSS, (String) null);
    }

    protected static StateEntry orientable(BlockEntry block) {
        return addShape(block, Shape.ORIENTABLE, (String) null);
    }

    // --- Custom ---

    protected static StateEntry custom(BlockEntry block, BiConsumer<BlockStateProvider, Block> provider) {
        StateEntry entry = new StateEntry(block, Shape.CUSTOM, null, provider);
        PENDING.add(entry);
        return entry;
    }

    // --- Helpers ---

    private static StateEntry addShape(BlockEntry block, Shape shape, BlockEntry textureSource) {
        // Store the registry name of the texture source block - resolved at datagen time
        String texName = "__blockentry__:" + System.identityHashCode(textureSource);
        StateEntry entry = new StateEntry(block, shape, texName, null);
        // Store a reference we can look up later
        TEXTURE_BLOCK_REFS.add(new TextureBlockRef(entry, textureSource));
        PENDING.add(entry);
        return entry;
    }

    private static StateEntry addShape(BlockEntry block, Shape shape, String texture) {
        StateEntry entry = new StateEntry(block, shape, texture, null);
        PENDING.add(entry);
        return entry;
    }

    // Track BlockEntry texture references for resolution at datagen time
    private static final List<TextureBlockRef> TEXTURE_BLOCK_REFS = new ArrayList<>();
    private record TextureBlockRef(StateEntry entry, BlockEntry textureSource) {}

    /**
     * Resolve the texture ResourceLocation for a StateEntry.
     * - BlockEntry ref -> modid:block/blockname
     * - String with colon -> used as-is
     * - String without colon -> modid: prepended
     * - null -> modid:block/ownname
     */
    private static ResourceLocation resolveTexture(StateEntry entry, String modId, Block block) {
        String tex = entry.getTexture();

        // Check if it's a BlockEntry reference
        if (tex != null && tex.startsWith("__blockentry__:")) {
            for (TextureBlockRef ref : TEXTURE_BLOCK_REFS) {
                if (ref.entry == entry) {
                    ResourceLocation rl = ref.textureSource.getRegistryObject().getId();
                    return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), "block/" + rl.getPath());
                }
            }
        }

        // String texture
        if (tex != null) {
            if (tex.contains(":")) {
                return ResourceLocation.tryParse(tex);
            }
            return ResourceLocation.fromNamespaceAndPath(modId, tex);
        }

        // Default: own block name
        ResourceLocation rl = block.builtInRegistryHolder().key().location();
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), "block/" + rl.getPath());
    }

    public static BlockStateProvider createProvider(DataGenerator gen, String modId, ExistingFileHelper fileHelper) {
        return new BlockStateProvider(gen.getPackOutput(), modId, fileHelper) {
            @SuppressWarnings("unchecked")
            @Override
            protected void registerStatesAndModels() {
                for (StateEntry entry : PENDING) {
                    Block block = entry.getBlock().get();
                    ResourceLocation tex = resolveTexture(entry, modId, block);

                    switch (entry.getShape()) {
                        case SIMPLE -> simpleBlockWithItem(block, cubeAll(block));

                        case SLAB -> {
                            ResourceLocation doubleSlab = tex; // full block texture
                            slabBlock((SlabBlock) block, doubleSlab, tex);
                            simpleBlockItem(block, models().slab(name(block), tex, tex, tex));
                        }

                        case STAIRS -> {
                            stairsBlock((StairBlock) block, tex);
                            simpleBlockItem(block, models().stairs(name(block), tex, tex, tex));
                        }

                        case WALL -> {
                            wallBlock((WallBlock) block, tex);
                            itemModels().wallInventory(name(block), tex);
                        }

                        case FENCE -> {
                            fenceBlock((FenceBlock) block, tex);
                            itemModels().fenceInventory(name(block), tex);
                        }

                        case FENCE_GATE -> {
                            fenceGateBlock((FenceGateBlock) block, tex);
                            simpleBlockItem(block, models().fenceGate(name(block), tex));
                        }

                        case DOOR -> {
                            ResourceLocation bottom = blockLoc(block, "_bottom");
                            ResourceLocation top = blockLoc(block, "_top");
                            doorBlockWithRenderType((DoorBlock) block, bottom, top, "cutout");
                            // Door items use a flat item texture, not the block model
                        }

                        case TRAPDOOR -> {
                            ResourceLocation trapTex = blockLoc(block, "");
                            trapdoorBlockWithRenderType((TrapDoorBlock) block, trapTex, true, "cutout");
                            itemModels().trapdoorBottom(name(block), trapTex);
                        }

                        case BUTTON -> {
                            buttonBlock((ButtonBlock) block, tex);
                            itemModels().buttonInventory(name(block), tex);
                        }

                        case PRESSURE_PLATE -> {
                            pressurePlateBlock((PressurePlateBlock) block, tex);
                            simpleBlockItem(block, models().pressurePlate(name(block), tex));
                        }

                        case PILLAR -> {
                            ResourceLocation side = blockLoc(block, "");
                            ResourceLocation top = blockLoc(block, "_top");
                            logBlock((RotatedPillarBlock) block);
                            simpleBlockItem(block, models().cubeColumn(name(block), side, top));
                        }

                        case CROSS -> {
                            ResourceLocation crossTex = blockLoc(block, "");
                            simpleBlock(block, models().cross(name(block), crossTex).renderType("cutout"));
                            itemModels().basicItem(block.asItem());
                        }

                        case ORIENTABLE -> {
                            ResourceLocation front = blockLoc(block, "_front");
                            ResourceLocation side = blockLoc(block, "_side");
                            ResourceLocation top = blockLoc(block, "_top");
                            horizontalBlock(block, models().orientable(name(block), side, front, top));
                            simpleBlockItem(block, models().orientable(name(block), side, front, top));
                        }

                        case CUSTOM -> {
                            BiConsumer<BlockStateProvider, Block> custom =
                                    (BiConsumer<BlockStateProvider, Block>) entry.getCustomProvider();
                            if (custom != null) {
                                custom.accept(this, block);
                            }
                        }
                    }
                }
            }

            private String name(Block block) {
                return block.builtInRegistryHolder().key().location().getPath();
            }

            private ResourceLocation blockLoc(Block block, String suffix) {
                ResourceLocation rl = block.builtInRegistryHolder().key().location();
                return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), "block/" + rl.getPath() + suffix);
            }
        };
    }
}
