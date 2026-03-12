package com.example.mymod.block.custom;

import com.example.mymod.block.ModBlockEntities;
import net.futuristicidiot.modbase.block.BaseEntityBlock;
import net.futuristicidiot.modbase.registry.block.BlockProps;
import net.futuristicidiot.modbase.registry.blockentity.BlockEntityEntry;

public class CrateBlock extends BaseEntityBlock {
    public CrateBlock() {
        super(BlockProps.create().strength(2.5f));
    }

    @Override
    protected BlockEntityEntry<?> getBlockEntity() {
        return ModBlockEntities.CRATE;
    }
}
