package net.futuristicidiot.modbase.registry.menu;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MenuEntry<T extends AbstractContainerMenu> implements Supplier<MenuType<T>> {
    private RegistryObject<MenuType<T>> registryObject;
    private MenuScreens.ScreenConstructor<T, ?> screenFactory;

    public void bind(RegistryObject<MenuType<T>> registryObject) {
        this.registryObject = registryObject;
    }

    /**
     * Set a custom screen factory for this menu. Optional — if not set, a generic screen is used.
     */
    public <S extends Screen & MenuAccess<T>> MenuEntry<T> screen(MenuScreens.ScreenConstructor<T, S> factory) {
        this.screenFactory = factory;
        return this;
    }

    public boolean hasCustomScreen() {
        return screenFactory != null;
    }

    public MenuScreens.ScreenConstructor<T, ?> getScreenFactory() {
        return screenFactory;
    }

    @Override
    public MenuType<T> get() {
        return registryObject.get();
    }

    public RegistryObject<MenuType<T>> getRegistryObject() {
        return registryObject;
    }
}
