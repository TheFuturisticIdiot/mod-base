package net.futuristicidiot.modbase.registry.sound;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SoundEntry implements Supplier<SoundEvent> {
    private RegistryObject<SoundEvent> registryObject;
    private final String name;

    public SoundEntry(String name) {
        this.name = name;
    }

    public void bind(RegistryObject<SoundEvent> registryObject) {
        this.registryObject = registryObject;
    }

    public String getName() {
        return name;
    }

    @Override
    public SoundEvent get() {
        return registryObject.get();
    }
}
