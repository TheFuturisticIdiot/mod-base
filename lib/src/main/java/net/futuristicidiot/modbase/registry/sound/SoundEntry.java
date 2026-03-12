package net.futuristicidiot.modbase.registry.sound;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SoundEntry implements Supplier<SoundEvent> {
    private RegistryObject<SoundEvent> registryObject;
    private final String name;
    private String subtitleKey; // null = auto-generated, "" = no subtitle
    private final List<String> files = new ArrayList<>();
    private boolean streaming = false;

    public SoundEntry(String name) {
        this.name = name;
    }

    /** Override the auto-generated subtitle key. */
    public SoundEntry subtitle(String key) {
        this.subtitleKey = key;
        return this;
    }

    /** Disable subtitle generation for this sound. */
    public SoundEntry noSubtitle() {
        this.subtitleKey = "";
        return this;
    }

    /** Set a custom sound file path (relative to sounds/, without .ogg). */
    public SoundEntry file(String path) {
        this.files.add(path);
        return this;
    }

    /** Set multiple sound file variants (Forge picks randomly). */
    public SoundEntry files(String... paths) {
        for (String path : paths) {
            this.files.add(path);
        }
        return this;
    }

    /** Mark this sound as streaming (for long sounds like music/ambient). */
    public SoundEntry stream() {
        this.streaming = true;
        return this;
    }

    public void bind(RegistryObject<SoundEvent> registryObject) {
        this.registryObject = registryObject;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns the subtitle key. If not explicitly set, auto-generates as
     * "subtitles.modid.name". Returns null if noSubtitle() was called.
     */
    public String getSubtitleKey(String modId) {
        if (subtitleKey == null) {
            return "subtitles." + modId + "." + name;
        }
        return subtitleKey.isEmpty() ? null : subtitleKey;
    }

    /** Returns the sound file paths. If none set, defaults to the sound name. */
    public List<String> getFiles() {
        return files.isEmpty() ? List.of(name) : files;
    }

    public boolean isStreaming() {
        return streaming;
    }

    @Override
    public SoundEvent get() {
        return registryObject.get();
    }
}
