package net.futuristicidiot.modbase.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * Base screen that renders a background texture and supports a progress bar override.
 * For simple GUIs, just extend this and pass the texture. Override renderProgress() for progress bars.
 */
public abstract class BaseScreen<T extends BaseMenu<?>> extends AbstractContainerScreen<T> {
    private final ResourceLocation texture;

    public BaseScreen(T menu, Inventory inv, Component title, ResourceLocation texture) {
        super(menu, inv, title);
        this.texture = texture;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(texture, x, y, 0, 0, imageWidth, imageHeight);

        renderProgress(graphics, x, y);
    }

    /**
     * Override to draw progress bars, arrows, or other dynamic elements.
     */
    protected void renderProgress(GuiGraphics graphics, int x, int y) {
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
