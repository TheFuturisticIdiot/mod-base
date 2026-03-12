package net.futuristicidiot.modbase.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * A generic screen used automatically for menus that don't have a custom screen.
 * Renders a background texture using the naming convention: textures/gui/{menu_registry_name}.png
 * Falls back to the vanilla chest texture if the custom texture doesn't exist.
 */
public class GenericScreen extends AbstractContainerScreen<AbstractContainerMenu> {
    private final ResourceLocation texture;

    public GenericScreen(AbstractContainerMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        // Derive texture from the menu type's registry name
        ResourceLocation menuId = net.minecraftforge.registries.ForgeRegistries.MENU_TYPES.getKey(menu.getType());
        if (menuId != null) {
            this.texture = ResourceLocation.fromNamespaceAndPath(menuId.getNamespace(), "textures/gui/" + menuId.getPath() + ".png");
        } else {
            this.texture = ResourceLocation.withDefaultNamespace("textures/gui/container/generic_54.png");
        }
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(texture, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
