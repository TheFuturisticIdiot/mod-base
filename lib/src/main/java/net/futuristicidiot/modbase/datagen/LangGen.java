package net.futuristicidiot.modbase.datagen;

import net.futuristicidiot.modbase.ModBase;
import net.futuristicidiot.modbase.registry.block.BlockEntry;
import net.futuristicidiot.modbase.registry.block.BlockRegistry;
import net.futuristicidiot.modbase.registry.item.ItemEntry;
import net.futuristicidiot.modbase.registry.item.ItemRegistry;
import net.futuristicidiot.modbase.registry.tab.TabEntry;
import net.futuristicidiot.modbase.registry.tab.TabRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.*;

/**
 * en_us lang gen. Auto-generates from registry names, user overrides with lang().
 * Extend LangGen.Lang("locale") for other languages.
 */
public abstract class LangGen {
    private static final Map<String, Map<String, String>> LANG_DATA = new LinkedHashMap<>();
    private static final String EN_US = "en_us";

    private final String locale;

    protected LangGen() {
        this.locale = EN_US;
        LANG_DATA.computeIfAbsent(locale, k -> new LinkedHashMap<>());
    }

    protected LangGen(String locale) {
        this.locale = locale;
        LANG_DATA.computeIfAbsent(locale, k -> new LinkedHashMap<>());
    }

    protected void lang(ItemEntry entry, String name) {
        String regName = entry.getRegistryObject().getId().getPath();
        String modId = entry.getRegistryObject().getId().getNamespace();
        LANG_DATA.get(locale).put("item." + modId + "." + regName, name);
    }

    protected void lang(BlockEntry entry, String name) {
        String regName = entry.getRegistryObject().getId().getPath();
        String modId = entry.getRegistryObject().getId().getNamespace();
        LANG_DATA.get(locale).put("block." + modId + "." + regName, name);
    }

    protected void lang(TabEntry entry, String name) {
        String modId = ModBase.id();
        LANG_DATA.get(locale).put("creativetab." + modId + "." + entry.getTabName(), name);
    }

    protected void lang(String key, String name) {
        LANG_DATA.get(locale).put(key, name);
    }

    /**
     * For other languages. No auto-generation, fully manual.
     */
    public static abstract class Lang extends LangGen {
        protected Lang(String locale) {
            super(locale);
        }
    }

    private static String autoName(String registryName) {
        String[] parts = registryName.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) sb.append(" ");
            sb.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1));
        }
        return sb.toString();
    }

    public static List<LanguageProvider> createProviders(DataGenerator gen, String modId) {
        List<LanguageProvider> providers = new ArrayList<>();

        // Always generate en_us
        Set<String> locales = new LinkedHashSet<>();
        locales.add(EN_US);
        locales.addAll(LANG_DATA.keySet());

        for (String locale : locales) {
            providers.add(new LanguageProvider(gen.getPackOutput(), modId, locale) {
                @Override
                protected void addTranslations() {
                    Map<String, String> overrides = LANG_DATA.getOrDefault(locale, new LinkedHashMap<>());

                    if (locale.equals(EN_US)) {
                        // Auto-generate item names
                        for (String name : ItemRegistry.getRegisteredNames()) {
                            String key = "item." + modId + "." + name;
                            add(key, overrides.getOrDefault(key, autoName(name)));
                        }

                        // Auto-generate block names
                        for (String name : BlockRegistry.getRegisteredNames()) {
                            String key = "block." + modId + "." + name;
                            add(key, overrides.getOrDefault(key, autoName(name)));
                        }

                        // Auto-generate tab names
                        for (String name : TabRegistry.getRegisteredNames()) {
                            String key = "creativetab." + modId + "." + name;
                            add(key, overrides.getOrDefault(key, autoName(name)));
                        }

                        // Add custom keys not covered by auto-gen
                        for (Map.Entry<String, String> entry : overrides.entrySet()) {
                            String key = entry.getKey();
                            if (!key.startsWith("item." + modId + ".")
                                    && !key.startsWith("block." + modId + ".")
                                    && !key.startsWith("creativetab." + modId + ".")) {
                                add(key, entry.getValue());
                            }
                        }
                    } else {
                        // Other languages: only user-provided
                        for (Map.Entry<String, String> entry : overrides.entrySet()) {
                            add(entry.getKey(), entry.getValue());
                        }
                    }
                }
            });
        }

        return providers;
    }
}
