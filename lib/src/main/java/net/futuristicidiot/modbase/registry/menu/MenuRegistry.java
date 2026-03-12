package net.futuristicidiot.modbase.registry.menu;

import net.futuristicidiot.modbase.screen.GenericScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public abstract class MenuRegistry {
    private static final List<PendingMenu<?>> PENDING = new ArrayList<>();

    protected static <T extends AbstractContainerMenu> MenuEntry<T> menu(String name, IContainerFactory<T> factory) {
        MenuEntry<T> entry = new MenuEntry<>();
        PENDING.add(new PendingMenu<>(name, entry, factory));
        return entry;
    }

    @SuppressWarnings("unchecked")
    public static void init(String modId, IEventBus modBus) {
        DeferredRegister<MenuType<?>> register = DeferredRegister.create(ForgeRegistries.MENU_TYPES, modId);

        for (PendingMenu<?> pending : PENDING) {
            registerEntry(register, (PendingMenu<AbstractContainerMenu>) pending);
        }

        register.register(modBus);

        // Auto-register screens on client setup
        modBus.addListener((FMLClientSetupEvent event) -> event.enqueueWork(() -> {
            for (PendingMenu<?> pending : PENDING) {
                registerScreen(pending);
            }
        }));
    }

    @SuppressWarnings("unchecked")
    private static <T extends AbstractContainerMenu> void registerEntry(
            DeferredRegister<MenuType<?>> register, PendingMenu<T> pending) {

        RegistryObject<MenuType<T>> regObj = (RegistryObject<MenuType<T>>) (RegistryObject<?>)
                register.register(pending.name, () -> IForgeMenuType.create(pending.factory));

        pending.entry.bind(regObj);
    }

    @SuppressWarnings("unchecked")
    private static void registerScreen(PendingMenu<?> pending) {
        MenuEntry<?> entry = pending.entry;
        if (entry.hasCustomScreen()) {
            MenuScreens.register(
                    (MenuType<AbstractContainerMenu>) (MenuType<?>) entry.get(),
                    (MenuScreens.ScreenConstructor<AbstractContainerMenu, ?>) entry.getScreenFactory()
            );
        } else {
            MenuScreens.register(
                    (MenuType<AbstractContainerMenu>) (MenuType<?>) entry.get(),
                    GenericScreen::new
            );
        }
    }

    private record PendingMenu<T extends AbstractContainerMenu>(
            String name, MenuEntry<T> entry, IContainerFactory<T> factory) {}
}
