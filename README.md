# mod-base

Forge 1.20.1 mod template. Handles registries, config, networking, and datagen so you can focus on content.

## Setup

1. Generate a repo from this template (or fork it)
2. Edit `gradle.properties` — set your mod ID, name, group, version
3. Rename the `com.example.mymod` package to your own
4. Update the `@Mod` annotation and `MOD_ID` in your main class

## Syncing template updates

Add the template as a remote:

```
git remote add template https://github.com/TheFuturisticIdiot/mod-base.git
```

Pull updates when needed:

```
git fetch template
git merge template/main --allow-unrelated-histories
```

Resolve any conflicts in your mod-specific files (gradle.properties, your package, etc). The base package should merge cleanly.

## Structure

- `net.futuristicidiot.modbase` — base package, don't edit
- Your package — all your mod code goes here

## Usage

See the example files in `com.example.mymod` for how to use each system. Each file has comments showing available options.
