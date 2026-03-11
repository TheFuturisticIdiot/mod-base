package net.futuristicidiot.modbase.registry.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public abstract class SoundRegistry {
    private static final List<SoundEntry> PENDING = new ArrayList<>();

    protected static SoundEntry sound(String name) {
        SoundEntry entry = new SoundEntry(name);
        PENDING.add(entry);
        return entry;
    }

    public static void init(String modId, IEventBus modBus) {
        DeferredRegister<SoundEvent> register = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, modId);
        for (SoundEntry entry : PENDING) {
            String name = entry.getName();
            RegistryObject<SoundEvent> regObj = register.register(name,
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(modId, name)));
            entry.bind(regObj);
        }
        register.register(modBus);
    }

    public static List<SoundEntry> getEntries() {
        return PENDING;
    }
}
