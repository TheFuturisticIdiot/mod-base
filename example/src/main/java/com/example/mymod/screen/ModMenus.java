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
    // If a menu has no .screen() set, a GenericScreen is used automatically.
    // You only need to write a screen class for custom rendering like progress bars.

    // The crate uses GenericScreen (no screen class needed).
    // The crusher uses a custom CrusherScreen for its progress bar.
    // See CrusherScreen.java for an example custom screen.
}
