package com.example.mymod.config;

import net.futuristicidiot.modbase.config.BoolEntry;
import net.futuristicidiot.modbase.config.ConfigBase;
import net.futuristicidiot.modbase.config.IntEntry;

public class ModServerConfig extends ConfigBase.Server {
    static BoolEntry ENABLE_FEATURE = bool("enable_feature", true);
    static IntEntry MAX_COUNT = integer("max_count", 10, 0, 100);

    // Same types as client: bool(), integer(), string()
    // Server configs are per-world, stored in saves/<world>/serverconfig/
}
