package com.example.mymod.sound;

import net.futuristicidiot.modbase.registry.sound.SoundEntry;
import net.futuristicidiot.modbase.registry.sound.SoundRegistry;

public class ModSounds extends SoundRegistry {
    public static SoundEntry EXAMPLE_SOUND = sound("example_sound");

    // Access with: ModSounds.EXAMPLE_SOUND.get()
}
