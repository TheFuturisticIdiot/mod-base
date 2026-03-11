package net.futuristicidiot.modbase.internal;

import net.futuristicidiot.modbase.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Handles automatic serialisation/deserialisation of BasePacket fields.
 */
public class PacketSerialiser {

    public static void encode(BasePacket packet, FriendlyByteBuf buf) {
        for (Field field : getPacketFields(packet.getClass())) {
            try {
                field.setAccessible(true);
                Object value = field.get(packet);
                writeField(buf, field.getType(), value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to encode packet field: " + field.getName(), e);
            }
        }
    }

    public static <T extends BasePacket> T decode(Class<T> clazz, FriendlyByteBuf buf) {
        try {
            T packet = clazz.getDeclaredConstructor().newInstance();
            for (Field field : getPacketFields(clazz)) {
                field.setAccessible(true);
                Object value = readField(buf, field.getType());
                field.set(packet, value);
            }
            return packet;
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode packet: " + clazz.getSimpleName(), e);
        }
    }

    private static List<Field> getPacketFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            int mods = field.getModifiers();
            if (!Modifier.isStatic(mods) && !Modifier.isTransient(mods)) {
                fields.add(field);
            }
        }
        return fields;
    }

    private static void writeField(FriendlyByteBuf buf, Class<?> type, Object value) {
        if (type == int.class || type == Integer.class) {
            buf.writeInt((int) value);
        } else if (type == long.class || type == Long.class) {
            buf.writeLong((long) value);
        } else if (type == float.class || type == Float.class) {
            buf.writeFloat((float) value);
        } else if (type == double.class || type == Double.class) {
            buf.writeDouble((double) value);
        } else if (type == boolean.class || type == Boolean.class) {
            buf.writeBoolean((boolean) value);
        } else if (type == byte.class || type == Byte.class) {
            buf.writeByte((byte) value);
        } else if (type == short.class || type == Short.class) {
            buf.writeShort((short) value);
        } else if (type == String.class) {
            buf.writeUtf((String) value);
        } else if (type == UUID.class) {
            buf.writeUUID((UUID) value);
        } else if (type == net.minecraft.core.BlockPos.class) {
            buf.writeBlockPos((net.minecraft.core.BlockPos) value);
        } else if (type == net.minecraft.nbt.CompoundTag.class) {
            buf.writeNbt((net.minecraft.nbt.CompoundTag) value);
        } else if (type == net.minecraft.world.item.ItemStack.class) {
            buf.writeItem((net.minecraft.world.item.ItemStack) value);
        } else if (type == net.minecraft.resources.ResourceLocation.class) {
            buf.writeResourceLocation((net.minecraft.resources.ResourceLocation) value);
        } else {
            throw new UnsupportedOperationException("Unsupported packet field type: " + type.getSimpleName());
        }
    }

    private static Object readField(FriendlyByteBuf buf, Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return buf.readInt();
        } else if (type == long.class || type == Long.class) {
            return buf.readLong();
        } else if (type == float.class || type == Float.class) {
            return buf.readFloat();
        } else if (type == double.class || type == Double.class) {
            return buf.readDouble();
        } else if (type == boolean.class || type == Boolean.class) {
            return buf.readBoolean();
        } else if (type == byte.class || type == Byte.class) {
            return buf.readByte();
        } else if (type == short.class || type == Short.class) {
            return buf.readShort();
        } else if (type == String.class) {
            return buf.readUtf();
        } else if (type == UUID.class) {
            return buf.readUUID();
        } else if (type == net.minecraft.core.BlockPos.class) {
            return buf.readBlockPos();
        } else if (type == net.minecraft.nbt.CompoundTag.class) {
            return buf.readNbt();
        } else if (type == net.minecraft.world.item.ItemStack.class) {
            return buf.readItem();
        } else if (type == net.minecraft.resources.ResourceLocation.class) {
            return buf.readResourceLocation();
        } else {
            throw new UnsupportedOperationException("Unsupported packet field type: " + type.getSimpleName());
        }
    }
}
