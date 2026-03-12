package com.example.mymod.network;

import net.futuristicidiot.modbase.registry.packet.PacketEntry;
import net.futuristicidiot.modbase.registry.packet.PacketRegistry;

public class ModPackets extends PacketRegistry {
    static PacketEntry<ExamplePacket> EXAMPLE_PACKET = packet("example_packet", ExamplePacket::new);

    // Send with:
    // ModPackets.EXAMPLE_PACKET.sendToServer(new ExamplePacket(42, "hello"));
    // ModPackets.EXAMPLE_PACKET.sendToPlayer(new ExamplePacket(42, "hello"), player);
    // ModPackets.EXAMPLE_PACKET.sendToAll(new ExamplePacket(42, "hello"));
}
