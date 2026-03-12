package com.example.mymod.config;

import net.futuristicidiot.modbase.config.BoolEntry;
import net.futuristicidiot.modbase.config.ConfigBase;
import net.futuristicidiot.modbase.config.IntEntry;

public class ModClientConfig extends ConfigBase.Client {
    static BoolEntry SHOW_HUD = bool("show_hud", true)
            .comment("Whether to show the HUD overlay");

    static IntEntry HUD_OPACITY = integer("hud_opacity", 100, 0, 100)
            .comment("HUD opacity percentage");

    // Available types: bool(), integer(), string(), decimal(), enumValue(), list()
    // All support .comment("description")
    // Access with: ModClientConfig.SHOW_HUD.get()
}
