# mod-base

Forge 1.20.1 library that handles registries, config, networking, datagen, block entities, and menus so you can focus on content.

## Using as a dependency

Add JitPack and modbase to your `build.gradle`:

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.TheFuturisticIdiot:mod-base:1.0.0'
}
```

## Getting started

Check the `example/` folder for a working mod that uses every feature. You can copy it as a starting point for your own mod — just edit `gradle.properties` and rename the package.

Or download the template zip from the latest [release](https://github.com/TheFuturisticIdiot/mod-base/releases) for a ready-to-use standalone project.

## What it handles

- **Registries** — items, blocks, block entities, menus, sounds, creative tabs, packets
- **Config** — client and server configs with bool, int, string entries
- **Networking** — auto-serialised packets, no FriendlyByteBuf needed
- **Datagen** — item models, block states, recipes, loot tables, lang files (en_us auto-generated)
- **Block entities** — base class with inventory, capabilities, save/load, client sync, drops
- **Menus/Screens** — base menu with auto shift-click, slot grid helpers, generic screen fallback

Your mod class extends `ModBase`, lists your classes in `use()`, and the library handles everything else.

## Repo structure

- `lib/` — the library source (published to JitPack)
- `example/` — a working example mod (not published, use as reference or template)
