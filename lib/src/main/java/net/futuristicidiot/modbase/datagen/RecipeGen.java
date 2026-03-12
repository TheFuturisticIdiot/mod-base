package net.futuristicidiot.modbase.datagen;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class RecipeGen {
    private static final List<Consumer<Consumer<FinishedRecipe>>> PENDING = new ArrayList<>();
    private static final Map<String, Integer> NAME_COUNTS = new HashMap<>();

    protected static ShapedBuilder shaped(Supplier<? extends ItemLike> result) {
        return new ShapedBuilder(result);
    }

    protected static ShapelessBuilder shapeless(Supplier<? extends ItemLike> result) {
        return new ShapelessBuilder(result);
    }

    protected static SmeltingBuilder smelting(Supplier<? extends ItemLike> result) {
        return new SmeltingBuilder(result);
    }

    private static String generateName(ItemLike item, String type) {
        ResourceLocation rl = item.asItem().builtInRegistryHolder().key().location();
        String base = rl.getPath() + "_" + type;
        int count = NAME_COUNTS.getOrDefault(base, 0);
        NAME_COUNTS.put(base, count + 1);
        if (count == 0) return base;
        return base + "_" + count;
    }

    private static InventoryChangeTrigger.TriggerInstance hasItem(ItemLike item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item);
    }

    public static RecipeProvider createProvider(DataGenerator gen, String modId) {
        return new RecipeProvider(gen.getPackOutput()) {
            @Override
            protected void buildRecipes(Consumer<FinishedRecipe> writer) {
                for (Consumer<Consumer<FinishedRecipe>> recipe : PENDING) {
                    recipe.accept(writer);
                }
            }
        };
    }

    public static class ShapedBuilder {
        private final Supplier<? extends ItemLike> result;
        private final List<String> patterns = new ArrayList<>();
        private final Map<Character, Ingredient> keys = new HashMap<>();
        private int count = 1;

        ShapedBuilder(Supplier<? extends ItemLike> result) {
            this.result = result;
            PENDING.add(this::build);
        }

        public ShapedBuilder pattern(String pattern) {
            patterns.add(pattern);
            return this;
        }

        public ShapedBuilder define(char key, ItemLike item) {
            keys.put(key, Ingredient.of(item));
            return this;
        }

        public ShapedBuilder define(char key, Supplier<? extends ItemLike> item) {
            keys.put(key, Ingredient.of(item.get()));
            return this;
        }

        public ShapedBuilder count(int count) {
            this.count = count;
            return this;
        }

        private void build(Consumer<FinishedRecipe> writer) {
            ItemLike resultItem = result.get();
            ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, resultItem, count);
            for (String p : patterns) {
                builder.pattern(p);
            }
            for (Map.Entry<Character, Ingredient> e : keys.entrySet()) {
                builder.define(e.getKey(), e.getValue());
            }
            builder.unlockedBy("has_item", hasItem(resultItem));
            String name = generateName(resultItem, "shaped");
            builder.save(writer, ResourceLocation.fromNamespaceAndPath(
                    resultItem.asItem().builtInRegistryHolder().key().location().getNamespace(), name));
        }
    }

    public static class ShapelessBuilder {
        private final Supplier<? extends ItemLike> result;
        private final List<Ingredient> ingredients = new ArrayList<>();
        private int count = 1;

        ShapelessBuilder(Supplier<? extends ItemLike> result) {
            this.result = result;
            PENDING.add(this::build);
        }

        public ShapelessBuilder requires(ItemLike item) {
            ingredients.add(Ingredient.of(item));
            return this;
        }

        public ShapelessBuilder requires(Supplier<? extends ItemLike> item) {
            ingredients.add(Ingredient.of(item.get()));
            return this;
        }

        public ShapelessBuilder count(int count) {
            this.count = count;
            return this;
        }

        private void build(Consumer<FinishedRecipe> writer) {
            ItemLike resultItem = result.get();
            ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, resultItem, count);
            for (Ingredient ing : ingredients) {
                builder.requires(ing);
            }
            builder.unlockedBy("has_item", hasItem(resultItem));
            String name = generateName(resultItem, "shapeless");
            builder.save(writer, ResourceLocation.fromNamespaceAndPath(
                    resultItem.asItem().builtInRegistryHolder().key().location().getNamespace(), name));
        }
    }

    public static class SmeltingBuilder {
        private final Supplier<? extends ItemLike> result;
        private Ingredient input;
        private int time = 200;
        private float xp = 0.1f;

        SmeltingBuilder(Supplier<? extends ItemLike> result) {
            this.result = result;
            PENDING.add(this::build);
        }

        public SmeltingBuilder input(ItemLike item) {
            this.input = Ingredient.of(item);
            return this;
        }

        public SmeltingBuilder input(Supplier<? extends ItemLike> item) {
            this.input = Ingredient.of(item.get());
            return this;
        }

        public SmeltingBuilder time(int ticks) {
            this.time = ticks;
            return this;
        }

        public SmeltingBuilder xp(float xp) {
            this.xp = xp;
            return this;
        }

        private void build(Consumer<FinishedRecipe> writer) {
            ItemLike resultItem = result.get();
            String name = generateName(resultItem, "smelting");
            SimpleCookingRecipeBuilder.smelting(input, RecipeCategory.MISC, resultItem, xp, time)
                    .unlockedBy("has_item", hasItem(resultItem))
                    .save(writer, ResourceLocation.fromNamespaceAndPath(
                            resultItem.asItem().builtInRegistryHolder().key().location().getNamespace(), name));
        }
    }
}
