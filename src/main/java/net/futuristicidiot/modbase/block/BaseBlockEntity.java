package net.futuristicidiot.modbase.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base block entity with optional inventory support.
 * Pass inventorySize > 0 to enable inventory, caps, save/load, and drops.
 * Pass 0 for a block entity with no inventory.
 * Override createMenu() to enable GUI interaction.
 */
public abstract class BaseBlockEntity extends BlockEntity implements MenuProvider {
    private final int inventorySize;
    protected final ItemStackHandler itemHandler;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize) {
        super(type, pos, state);
        this.inventorySize = inventorySize;
        this.itemHandler = inventorySize > 0 ? createInventory(inventorySize) : null;
    }

    public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        this(type, pos, state, 0);
    }

    // --- Inventory ---

    public boolean hasInventory() {
        return inventorySize > 0 && itemHandler != null;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    /**
     * Override to customise inventory behaviour (e.g. isItemValid).
     */
    protected ItemStackHandler createInventory(int size) {
        return new ItemStackHandler(size) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    /**
     * Drops all inventory contents into the world. Called automatically by BaseEntityBlock on remove.
     */
    public void drops() {
        if (!hasInventory()) return;
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        if (level != null) {
            Containers.dropContents(level, worldPosition, container);
        }
    }

    // --- Capabilities ---

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (hasInventory() && cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (hasInventory()) {
            lazyItemHandler = LazyOptional.of(() -> itemHandler);
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (hasInventory()) {
            lazyItemHandler.invalidate();
        }
    }

    // --- Save / Load ---

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (hasInventory()) {
            tag.put("inventory", itemHandler.serializeNBT());
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (hasInventory() && tag.contains("inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("inventory"));
        }
    }

    // --- Client Sync ---

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    /**
     * Marks dirty and sends an update to clients.
     */
    protected void sync() {
        setChanged();
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    // --- Ticking ---

    /**
     * Override for server-side tick logic.
     */
    public void tick(Level level, BlockPos pos, BlockState state) {}

    // --- MenuProvider ---

    @Override
    public Component getDisplayName() {
        return getBlockState().getBlock().getName();
    }

    /**
     * Override to provide a menu for GUI interaction.
     * Return null if this block entity has no GUI.
     */
    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return null;
    }
}
