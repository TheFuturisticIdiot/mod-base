package com.example.mymod.config;

import net.futuristicidiot.modbase.config.*;

import java.util.List;

public class ModServerConfig extends ConfigBase.Server {
    static BoolEntry ENABLE_FEATURE = bool("enable_feature", true)
            .comment("Whether the feature is enabled");

    static IntEntry MAX_COUNT = integer("max_count", 10, 0, 100)
            .comment("Maximum number of things");

    static DoubleEntry SPEED_MULT = decimal("speed_multiplier", 1.0, 0.1, 10.0)
            .comment("Speed multiplier for processing");

    // Enum example (uncomment and create your own enum):
    // static EnumEntry<Difficulty> DIFF = enumValue("difficulty", Difficulty.NORMAL)
    //         .comment("Difficulty setting");

    // List example:
    // static ListEntry<String> BANNED = list("banned_items", List.of("minecraft:bedrock"))
    //         .comment("Items that cannot be used");

    // Server configs are per-world, stored in saves/<world>/serverconfig/
}
