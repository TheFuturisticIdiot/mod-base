package net.futuristicidiot.modbase.datagen;

import net.futuristicidiot.modbase.datagen.entry.ModelEntry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemModelGen {
    private static final List<ModelEntry> PENDING = new ArrayList<>();

    protected static ModelEntry item(String name) {
        return item(name, "generated");
    }

    protected static ModelEntry item(String name, String type) {
        ModelEntry entry = new ModelEntry(name, type);
        PENDING.add(entry);
        return entry;
    }

    public static ItemModelProvider createProvider(DataGenerator gen, String modId, ExistingFileHelper fileHelper) {
        return new ItemModelProvider(gen.getPackOutput(), modId, fileHelper) {
            @Override
            protected void registerModels() {
                for (ModelEntry entry : PENDING) {
                    ResourceLocation parent;
                    switch (entry.getType()) {
                        case "handheld" -> parent = ResourceLocation.withDefaultNamespace("item/handheld");
                        default -> parent = ResourceLocation.withDefaultNamespace("item/generated");
                    }
                    withExistingParent(entry.getName(), parent)
                            .texture("layer0", ResourceLocation.fromNamespaceAndPath(modId, "item/" + entry.getName()));
                }
            }
        };
    }
}
