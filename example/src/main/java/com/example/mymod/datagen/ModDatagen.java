package com.example.mymod.datagen;

import net.futuristicidiot.modbase.datagen.DatagenBase;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "examplemod", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagen extends DatagenBase {
    public ModDatagen() {
        use(
                ModItemModels.class,
                ModBlockStates.class,
                ModRecipes.class,
                ModBlockLoot.class,
                ModLang.class
        );
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        new ModDatagen().run(event);
    }
}
