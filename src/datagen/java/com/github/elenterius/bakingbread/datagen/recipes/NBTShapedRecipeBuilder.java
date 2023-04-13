package com.github.elenterius.bakingbread.datagen.recipes;

import com.github.elenterius.bakingbread.recipe.NBTShapedRecipe;
import com.github.elenterius.bakingbread.recipe.NBTShapedSerializer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

public class NBTShapedRecipeBuilder<T extends NBTShapedRecipe> implements RecipeBuilder {

	private final Item result;
	private final int count;
	private final List<String> pattern = Lists.newArrayList();
	private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
	private final Advancement.Builder advancement = Advancement.Builder.advancement();
	private final NBTShapedSerializer<T> serializer;
	@Nullable
	private String group;

	private NBTShapedRecipeBuilder(ItemLike result, int count, NBTShapedSerializer<T> serializer) {
		this.result = result.asItem();
		this.count = count;
		this.serializer = serializer;
	}

	public static <T extends NBTShapedRecipe> NBTShapedRecipeBuilder<T> shaped(ItemLike result, NBTShapedSerializer<T> serializer) {
		return shaped(result, 1, serializer);
	}

	public static <T extends NBTShapedRecipe> NBTShapedRecipeBuilder<T> shaped(ItemLike result, int count, NBTShapedSerializer<T> serializer) {
		return new NBTShapedRecipeBuilder<>(result, count, serializer);
	}

	public NBTShapedRecipeBuilder<T> define(Character symbol, TagKey<Item> tag) {
		return define(symbol, Ingredient.of(tag));
	}

	public NBTShapedRecipeBuilder<T> define(Character symbol, ItemLike item) {
		return define(symbol, Ingredient.of(item));
	}

	public NBTShapedRecipeBuilder<T> define(Character symbol, Ingredient ingredient) {
		if (key.containsKey(symbol)) {
			throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
		} else if (symbol == ' ') {
			throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
		} else {
			key.put(symbol, ingredient);
			return this;
		}
	}

	public NBTShapedRecipeBuilder<T> pattern(String pattern) {
		if (!this.pattern.isEmpty() && pattern.length() != this.pattern.get(0).length()) {
			throw new IllegalArgumentException("Pattern must be the same width on every line!");
		} else {
			this.pattern.add(pattern);
			return this;
		}
	}

	public NBTShapedRecipeBuilder<T> unlockedBy(String criterionName, CriterionTriggerInstance criterionTrigger) {
		advancement.addCriterion(criterionName, criterionTrigger);
		return this;
	}

	public NBTShapedRecipeBuilder<T> group(@Nullable String groupName) {
		group = groupName;
		return this;
	}

	public Item getResult() {
		return this.result;
	}

	public void save(Consumer<FinishedRecipe> consumer, ResourceLocation recipeId) {
		ensureValid(recipeId);
		advancement.parent(ROOT_RECIPE_ADVANCEMENT)
			.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
			.rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);

		ResourceLocation advancementId = new ResourceLocation(recipeId.getNamespace(), "recipes/" + result.getItemCategory().getRecipeFolderName() + "/" + recipeId.getPath());
		consumer.accept(new FinishedRecipe() {
			@Override
			public void serializeRecipeData(JsonObject json) {
				if (group != null && !group.isEmpty()) {
					json.addProperty("group", group);
				}

				JsonArray patternJson = new JsonArray();
				for (String s : pattern) {
					patternJson.add(s);
				}
				json.add("pattern", patternJson);

				JsonObject ingredientsKeys = new JsonObject();
				for (Map.Entry<Character, Ingredient> entry : key.entrySet()) {
					ingredientsKeys.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
				}
				json.add("key", ingredientsKeys);

				JsonObject resultJson = new JsonObject();
				resultJson.addProperty("item", Registry.ITEM.getKey(result).toString());
				if (count > 1) {
					resultJson.addProperty("count", count);
				}

				json.add("result", resultJson);
			}

			@Override
			public ResourceLocation getId() {
				return recipeId;
			}

			@Override
			public RecipeSerializer<?> getType() {
				return serializer;
			}

			@Nullable
			@Override
			public JsonObject serializeAdvancement() {
				return advancement.serializeToJson();
			}

			@Nullable
			@Override
			public ResourceLocation getAdvancementId() {
				return advancementId;
			}
		});
	}

	private void ensureValid(ResourceLocation id) {
		if (pattern.isEmpty()) {
			throw new IllegalStateException("No pattern is defined for shaped recipe " + id + "!");
		} else {
			Set<Character> set = Sets.newHashSet(key.keySet());
			set.remove(' ');

			for (String s : this.pattern) {
				for (int i = 0; i < s.length(); ++i) {
					char c0 = s.charAt(i);
					if (!key.containsKey(c0) && c0 != ' ') {
						throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c0 + "'");
					}
					set.remove(c0);
				}
			}

			if (!set.isEmpty()) {
				throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
			} else if (pattern.size() == 1 && pattern.get(0).length() == 1) {
				throw new IllegalStateException("Shaped recipe " + id + " only takes in a single item - should it be a shapeless recipe instead?");
			} else if (advancement.getCriteria().isEmpty()) {
				throw new IllegalStateException("No way of obtaining recipe " + id);
			}
		}
	}

}
