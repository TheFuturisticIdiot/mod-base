package net.futuristicidiot.modbase.registry.packet;

import net.futuristicidiot.modbase.internal.PacketSerialiser;
import net.futuristicidiot.modbase.network.BasePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class PacketRegistry {
    private static final String PROTOCOL_VERSION = "1";
    private static final List<PacketEntry<?>> PENDING = new ArrayList<>();
    private static SimpleChannel channel;

    @SuppressWarnings("unchecked")
    protected static <T extends BasePacket> PacketEntry<T> packet(String name, Supplier<T> factory) {
        // Create a temporary instance to get the class
        T temp = factory.get();
        PacketEntry<T> entry = new PacketEntry<>((Class<T>) temp.getClass());
        PENDING.add(entry);
        return entry;
    }

    public static void init(String modId, IEventBus modBus) {
        channel = NetworkRegistry.newSimpleChannel(
                ResourceLocation.fromNamespaceAndPath(modId, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );

        int id = 0;
        for (PacketEntry<?> entry : PENDING) {
            registerPacket(entry, id++);
            entry.bind(channel);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T extends BasePacket> void registerPacket(PacketEntry<T> entry, int id) {
        Class<T> clazz = entry.getPacketClass();
        channel.registerMessage(
                id,
                clazz,
                PacketSerialiser::encode,
                buf -> PacketSerialiser.decode(clazz, buf),
                (packet, ctx) -> {
                    ctx.get().enqueueWork(packet::handle);
                    ctx.get().setPacketHandled(true);
                }
        );
    }
}
