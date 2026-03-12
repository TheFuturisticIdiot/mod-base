package com.example.mymod.sound;

import net.futuristicidiot.modbase.registry.sound.SoundEntry;
import net.futuristicidiot.modbase.registry.sound.SoundRegistry;

public class ModSounds extends SoundRegistry {
    // Simple (file: sounds/example_sound.ogg, subtitle: subtitles.examplemod.example_sound):
    public static SoundEntry EXAMPLE_SOUND = sound("example_sound");

    // Custom file path (file: sounds/ui/click.ogg):
    // sound("ui_click").file("ui/click")

    // Streaming (for long sounds like music/ambient):
    // sound("ambient_loop").file("ambient/loop").stream()

    // Multiple variants (Forge picks randomly):
    // sound("hit").files("hit1", "hit2", "hit3")

    // Custom subtitle:
    // sound("bonk").subtitle("subtitles.examplemod.custom_bonk")

    // No subtitle (e.g. UI sounds):
    // sound("ui_beep").noSubtitle()
}
