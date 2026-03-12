package net.futuristicidiot.modbase.datagen;

import net.futuristicidiot.modbase.registry.sound.SoundEntry;
import net.futuristicidiot.modbase.registry.sound.SoundRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class SoundGen {
    public static SoundDefinitionsProvider createProvider(DataGenerator gen, String modId, ExistingFileHelper fileHelper) {
        return new SoundDefinitionsProvider(gen.getPackOutput(), modId, fileHelper) {
            @Override
            public void registerSounds() {
                for (SoundEntry entry : SoundRegistry.getEntries()) {
                    SoundDefinition def = SoundDefinition.definition();

                    String subtitle = entry.getSubtitleKey(modId);
                    if (subtitle != null) {
                        def.subtitle(subtitle);
                    }

                    for (String file : entry.getFiles()) {
                        ResourceLocation soundFile = ResourceLocation.fromNamespaceAndPath(modId, file);
                        def.with(SoundDefinition.Sound
                                .sound(soundFile, SoundDefinition.SoundType.SOUND)
                                .stream(entry.isStreaming()));
                    }

                    add(ResourceLocation.fromNamespaceAndPath(modId, entry.getName()), def);
                }
            }
        };
    }
}
