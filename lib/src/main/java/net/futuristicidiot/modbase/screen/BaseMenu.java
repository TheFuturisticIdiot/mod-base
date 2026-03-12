package net.futuristicidiot.modbase.screen;

import net.futuristicidiot.modbase.block.BaseBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Base menu with automatic slot layout, player inventory, and shift-click handling.
 * User overrides getSlotDefinitions() to define machine slots.
 * Player inventory and hotbar are added automatically.
 */
public abstract class BaseMenu<T extends BaseBlockEntity> extends AbstractContainerMenu {
    public final T blockEntity;
    protected final ContainerData data;
    private final List<SlotDefinition> slotDefs;

    protected BaseMenu(MenuType<?> menuType, int id, Inventory inv, T entity) {
        this(menuType, id, inv, entity, null);
    }

    protected BaseMenu(MenuType<?> menuType, int id, Inventory inv, T entity, ContainerData data) {
        super(menuType, id);
        this.blockEntity = entity;
        this.data = data;
        this.slotDefs = getSlotDefinitions();

        // Add machine slots
        for (SlotDefinition def : slotDefs) {
            if (def.type() == SlotType.OUTPUT) {
                this.addSlot(new SlotItemHandler(entity.getItemHandler(), def.index(), def.x(), def.y()) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return false;
                    }
                });
            } else {
                this.addSlot(new SlotItemHandler(entity.getItemHandler(), def.index(), def.x(), def.y()));
            }
        }

        // Add player inventory and hotbar
        addPlayerInventory(inv, getPlayerInvX(), getPlayerInvY());
        addPlayerHotbar(inv, getHotbarX(), getHotbarY());

        if (data != null) {
            addDataSlots(data);
        }
    }

    /**
     * Define the machine slots. Use SlotDefinition.input(), .output(), .other().
     */
    protected abstract List<SlotDefinition> getSlotDefinitions();

    // --- Player inventory position (override to customise) ---

    protected int getPlayerInvX() { return 8; }
    protected int getPlayerInvY() { return 84; }
    protected int getHotbarX() { return 8; }
    protected int getHotbarY() { return 142; }

    // --- Slot grid helper ---

    /**
     * Creates a grid of SlotDefinitions.
     * @param type The slot type (INPUT, OUTPUT, OTHER)
     * @param startIndex First slot index
     * @param count Total number of slots
     * @param cols Number of columns
     * @param x X position of top-left slot
     * @param y Y position of top-left slot
     */
    protected static List<SlotDefinition> slotGrid(SlotType type, int startIndex, int count, int cols, int x, int y) {
        List<SlotDefinition> slots = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int col = i % cols;
            int row = i / cols;
            slots.add(new SlotDefinition(startIndex + i, x + col * 18, y + row * 18, type));
        }
        return slots;
    }

    // --- Still valid ---

    @Override
    public boolean stillValid(Player player) {
        return stillValid(
                net.minecraft.world.inventory.ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()),
                player,
                blockEntity.getBlockState().getBlock()
        );
    }

    // --- Shift-click handling (fully automated) ---

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copy = sourceStack.copy();

        int machineCount = slotDefs.size();

        if (index < machineCount) {
            // Machine -> Player
            if (!moveItemStackTo(sourceStack, machineCount, machineCount + 36, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            // Player -> Machine (only try INPUT and OTHER slots)
            boolean moved = false;
            for (int i = 0; i < machineCount; i++) {
                if (slotDefs.get(i).type() != SlotType.OUTPUT) {
                    if (moveItemStackTo(sourceStack, i, i + 1, false)) {
                        moved = true;
                        break;
                    }
                }
            }
            if (!moved) {
                // Move between player inventory and hotbar
                if (index < machineCount + 27) {
                    if (!moveItemStackTo(sourceStack, machineCount + 27, machineCount + 36, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!moveItemStackTo(sourceStack, machineCount, machineCount + 27, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.setByPlayer(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        return copy;
    }

    // --- Internal helpers ---

    private void addPlayerInventory(Inventory inv, int x, int y) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, x + col * 18, y + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inv, int x, int y) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inv, i, x + i * 18, y));
        }
    }
}
