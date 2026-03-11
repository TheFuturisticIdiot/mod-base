package com.example.mymod;

import com.example.mymod.block.ModBlocks;
import com.example.mymod.config.ModClientConfig;
import com.example.mymod.config.ModServerConfig;
import com.example.mymod.datagen.*;
import com.example.mymod.item.ModItems;
import com.example.mymod.network.ModPackets;
import com.example.mymod.sound.ModSounds;
import com.example.mymod.tab.ModTabs;
import net.futuristicidiot.modbase.ModBase;
import net.minecraftforge.fml.common.Mod;

@Mod(MyMod.MOD_ID)
public class MyMod extends ModBase {
    public static final String MOD_ID = "examplemod";

    @Override
    protected String getModId() {
        return MOD_ID;
    }

    @Override
    protected void register() {
        use(
                ModItems.class,
                ModBlocks.class,
                ModSounds.class,
                ModTabs.class,
                ModPackets.class,
                ModClientConfig.class,
                ModServerConfig.class
        );
    }

    @Override
    protected void datagen() {
        use(
                ModItemModels.class,
                ModBlockStates.class,
                ModRecipes.class,
                ModBlockLoot.class,
                ModLang.class
        );
    }
}
