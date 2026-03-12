package com.example.mymod.network;

import net.futuristicidiot.modbase.network.BasePacket;

public class ExamplePacket extends BasePacket {
    int value;
    String message;

    // No-arg constructor required for deserialisation
    public ExamplePacket() {}

    public ExamplePacket(int value, String message) {
        this.value = value;
        this.message = message;
    }

    @Override
    public void handle() {
        // Handle the packet here
    }

    // Supported field types: int, long, float, double, boolean, byte, short,
    // String, UUID, BlockPos, CompoundTag, ItemStack, ResourceLocation
}
