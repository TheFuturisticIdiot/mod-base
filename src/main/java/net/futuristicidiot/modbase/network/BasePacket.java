package net.futuristicidiot.modbase.network;

/**
 * Base class for all packets. Users extend this, declare fields,
 * and override handle(). The base handles serialisation automatically.
 */
public abstract class BasePacket {
    /**
     * Called when the packet is received. Override this to handle the packet.
     */
    public abstract void handle();
}
