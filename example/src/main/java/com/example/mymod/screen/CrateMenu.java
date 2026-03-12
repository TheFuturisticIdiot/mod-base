package com.example.mymod.screen;

import com.example.mymod.block.custom.CrateBlockEntity;
import net.futuristicidiot.modbase.screen.BaseMenu;
import net.futuristicidiot.modbase.screen.SlotDefinition;
import net.futuristicidiot.modbase.screen.SlotType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class CrateMenu extends BaseMenu<CrateBlockEntity> {
    public CrateMenu(int id, Inventory inv, FriendlyByteBuf data) {
        this(id, inv, (CrateBlockEntity) inv.player.level().getBlockEntity(data.readBlockPos()));
    }

    public CrateMenu(int id, Inventory inv, CrateBlockEntity entity) {
        super(ModMenus.CRATE.get(), id, inv, entity);
    }

    @Override
    protected List<SlotDefinition> getSlotDefinitions() {
        // 18 slots in a 9x2 grid, starting at (8, 18)
        return slotGrid(SlotType.INPUT, 0, 18, 9, 8, 18);
    }
}
