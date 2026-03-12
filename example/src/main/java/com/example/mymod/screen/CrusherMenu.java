package com.example.mymod.screen;

import com.example.mymod.block.custom.CrusherBlockEntity;
import net.futuristicidiot.modbase.screen.BaseMenu;
import net.futuristicidiot.modbase.screen.SlotDefinition;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;

public class CrusherMenu extends BaseMenu<CrusherBlockEntity> {
    public CrusherMenu(int id, Inventory inv, FriendlyByteBuf data) {
        this(id, inv, (CrusherBlockEntity) inv.player.level().getBlockEntity(data.readBlockPos()));
    }

    public CrusherMenu(int id, Inventory inv, CrusherBlockEntity entity) {
        super(ModMenus.CRUSHER.get(), id, inv, entity, entity.getData());
    }

    public int getScaledProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        return maxProgress != 0 && progress != 0 ? progress * 24 / maxProgress : 0;
    }

    @Override
    protected List<SlotDefinition> getSlotDefinitions() {
        return List.of(
                SlotDefinition.input(CrusherBlockEntity.INPUT_SLOT, 56, 35),
                SlotDefinition.output(CrusherBlockEntity.OUTPUT_SLOT, 116, 35)
        );
    }
}
