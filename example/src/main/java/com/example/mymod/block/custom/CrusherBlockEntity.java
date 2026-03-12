package com.example.mymod.block.custom;

import com.example.mymod.screen.CrusherMenu;
import net.futuristicidiot.modbase.block.BaseBlockEntity;
import com.example.mymod.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CrusherBlockEntity extends BaseBlockEntity {
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    public static final int MAX_PROGRESS = 60; // 3 seconds

    private int progress = 0;

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> MAX_PROGRESS;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) progress = value;
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public CrusherBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, 2); // 1 input, 1 output
    }

    public ContainerData getData() {
        return data;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new CrusherMenu(id, inv, this);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        // Placeholder tick logic - a real mod would check recipes here
        if (!itemHandler.getStackInSlot(INPUT_SLOT).isEmpty()
                && itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty()) {
            progress++;
            setChanged();
            if (progress >= MAX_PROGRESS) {
                // Would craft here
                level.playSound(null, pos, ModSounds.EXAMPLE_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                progress = 0;
            }
        } else {
            progress = 0;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("progress", progress);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        progress = tag.getInt("progress");
    }
}
