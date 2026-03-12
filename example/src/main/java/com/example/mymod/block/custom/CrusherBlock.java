package com.example.mymod.block.custom;

import com.example.mymod.block.ModBlockEntities;
import net.futuristicidiot.modbase.block.BaseEntityBlock;
import net.futuristicidiot.modbase.registry.block.BlockProps;
import net.futuristicidiot.modbase.registry.blockentity.BlockEntityEntry;

public class CrusherBlock extends BaseEntityBlock {
    public CrusherBlock() {
        super(BlockProps.create().strength(3.5f).requiresCorrectToolForDrops());
    }

    @Override
    protected BlockEntityEntry<?> getBlockEntity() {
        return ModBlockEntities.CRUSHER;
    }
}
