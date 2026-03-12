package net.futuristicidiot.modbase.registry.packet;

import net.futuristicidiot.modbase.network.BasePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketEntry<T extends BasePacket> {
    private final Class<T> packetClass;
    private SimpleChannel channel;

    public PacketEntry(Class<T> packetClass) {
        this.packetClass = packetClass;
    }

    public void bind(SimpleChannel channel) {
        this.channel = channel;
    }

    public Class<T> getPacketClass() {
        return packetClass;
    }

    public void sendToServer(T packet) {
        channel.sendToServer(packet);
    }

    public void sendToPlayer(T packet, ServerPlayer player) {
        channel.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public void sendToAll(T packet) {
        channel.send(PacketDistributor.ALL.noArg(), packet);
    }
}
