package com.example.mymod.screen;

import com.example.mymod.MyMod;
import net.futuristicidiot.modbase.screen.BaseScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CrusherScreen extends BaseScreen<CrusherMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/gui/crusher.png");

    public CrusherScreen(CrusherMenu menu, Inventory inv, Component title) {
        super(menu, inv, title, TEXTURE);
    }

    @Override
    protected void renderProgress(GuiGraphics graphics, int x, int y) {
        if (menu.getScaledProgress() > 0) {
            // Draw progress arrow from texture (176, 0) to (80, 35) in the GUI
            graphics.blit(TEXTURE, x + 80, y + 35, 176, 0, menu.getScaledProgress(), 17);
        }
    }
}
