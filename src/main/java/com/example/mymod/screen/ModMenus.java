package com.example.mymod.screen;

import net.futuristicidiot.modbase.registry.menu.MenuEntry;
import net.futuristicidiot.modbase.registry.menu.MenuRegistry;

public class ModMenus extends MenuRegistry {
    public static MenuEntry<CrateMenu> CRATE = menu("crate", CrateMenu::new);
    public static MenuEntry<CrusherMenu> CRUSHER = menu("crusher", CrusherMenu::new).screen(CrusherScreen::new);

    // Simple (auto generic screen, no screen class needed):
    // menu("name", MyMenu::new)

    // With custom screen (for progress bars, custom rendering):
    // menu("name", MyMenu::new).screen(MyScreen::new)

    // The base auto-registers all screens — no client event handler needed.
}
