package com.example.mymod.block;

import com.example.mymod.block.custom.CrateBlockEntity;
import com.example.mymod.block.custom.CrusherBlockEntity;
import net.futuristicidiot.modbase.registry.blockentity.BlockEntityEntry;
import net.futuristicidiot.modbase.registry.blockentity.BlockEntityRegistry;

public class ModBlockEntities extends BlockEntityRegistry {
    public static BlockEntityEntry<CrateBlockEntity> CRATE = blockEntity("crate", CrateBlockEntity::new, ModBlocks.CRATE);
    public static BlockEntityEntry<CrusherBlockEntity> CRUSHER = blockEntity("crusher", CrusherBlockEntity::new, ModBlocks.CRUSHER);

    // Basic:
    // blockEntity("name", MyBlockEntity::new, ModBlocks.MY_BLOCK)

    // Multiple valid blocks:
    // blockEntity("name", MyBlockEntity::new, ModBlocks.BLOCK_A, ModBlocks.BLOCK_B)

    // Access with: ModBlockEntities.CRATE.get() returns BlockEntityType<CrateBlockEntity>
}
