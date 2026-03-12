# mod-base

Forge 1.20.1 library that handles registries, config, networking, datagen, block entities, and menus so you can focus on content.

## Quick start

Download the template zip from the latest [release](https://github.com/TheFuturisticIdiot/mod-base/releases), edit `gradle.properties` with your mod's details, and start adding content.

Or check the `example/` folder for a working reference mod.

## What it handles

- **Registries** - items, blocks, block entities, menus, sounds, creative tabs, packets
- **Config** - client and server configs with bool, int, string entries
- **Networking** - auto-serialised packets, no FriendlyByteBuf needed
- **Datagen** - item models, block states, recipes, loot tables, lang files (en_us auto-generated)
- **Block entities** - base class with inventory, capabilities, save/load, client sync, drops
- **Menus/Screens** - base menu with auto shift-click, slot grid helpers, generic screen fallback

## How it works

Your mod class extends `ModBase` and lists your classes in `register()`:

```java
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
                ModBlockEntities.class,
                ModMenus.class,
                ModSounds.class,
                ModTabs.class,
                ModPackets.class,
                ModClientConfig.class,
                ModServerConfig.class
        );
    }
}
```

Datagen is a separate standalone class:

```java
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
```

## Using as a dependency

If you're adding mod-base to an existing project, you need both the dependency and the unpack setup.

Add JitPack, the dependency, and JarInJar for production builds:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    minecraft "net.minecraftforge:forge:${minecraft_version}-${forge_version}"

    def modbaseDep = "com.github.TheFuturisticIdiot:mod-base:${modbase_version}"
    implementation fg.deobf(modbaseDep)
    jarJar(modbaseDep) {
        jarJar.ranged(it, "[${modbase_version},)")
    }
}
```

Then add the unpack setup so Forge can load modbase on the correct classloader in dev runs:

```gradle
configurations {
    modbaseLib {
        canBeConsumed = false
        canBeResolved = true
    }
}
dependencies {
    modbaseLib fg.deobf("com.github.TheFuturisticIdiot:mod-base:${modbase_version}")
}

// Unpack modbase jar so ForgeGradle can load it as a mod
tasks.register('unpackModbase', Copy) {
    from { configurations.modbaseLib.collect { zipTree(it) } }
    into layout.buildDirectory.dir('modbase-unpacked')
}
tasks.named('compileJava').configure { dependsOn 'unpackModbase' }

sourceSets {
    modbase {
        output.resourcesDir = layout.buildDirectory.dir('modbase-unpacked')
        java.srcDirs = []
    }
}

minecraft.runs.configureEach {
    mods {
        modbase {
            source sourceSets.modbase
        }
    }
}
```

Without the unpack setup, modbase ends up on the wrong classloader and can't see Forge classes at runtime.

## Repo structure

- `lib/` - the library source (published to JitPack)
- `example/` - a working example mod (not published, use as reference or template)
