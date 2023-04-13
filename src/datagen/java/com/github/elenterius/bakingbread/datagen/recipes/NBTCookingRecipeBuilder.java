package com.github.elenterius.bakingbread.datagen.recipes;

import com.github.elenterius.bakingbread.recipe.NBTCookingRecipe;
import com.github.elenterius.bakingbread.recipe.NBTCookingSerializer;
import com.google.gson.JsonObject;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class NBTCookingRecipeBuilder<T extends NBTCookingRecipe> {

	private final Item result;
	private final Ingredient ingredient;
	private final float experience;
	private final int cookingTime;
	private final NBTCookingSerializer<T> serializer;
	@Nullable
	private String group;

	private NBTCookingRecipeBuilder(ItemLike result, Ingredient ingredient, float experience, int cookingTime, NBTCookingSerializer<T> serializer) {
		this.result = result.asItem();
		this.ingredient = ingredient;
		this.experience = experience;
		this.cookingTime = cookingTime;
		this.serializer = serializer;
	}

	public static <T extends NBTCookingRecipe> NBTCookingRecipeBuilder<T> cooking(Ingredient ingredient, ItemLike result, float experience, int cookingTime, NBTCookingSerializer<T> serializer) {
		return new NBTCookingRecipeBuilder<>(result, ingredient, experience, cookingTime, serializer);
	}

	public NBTCookingRecipeBuilder<T> group(@Nullable String groupName) {
		group = groupName;
		return this;
	}

	public void save(Consumer<FinishedRecipe> consumer, ResourceLocation recipeId) {
		consumer.accept(new FinishedRecipe() {
			public void serializeRecipeData(JsonObject json) {
				if (group != null && !group.isEmpty()) {
					json.addProperty("group", group);
				}

				json.add("ingredient", ingredient.toJson());
				json.addProperty("result", Registry.ITEM.getKey(result).toString());
				json.addProperty("experience", experience);
				json.addProperty("cookingtime", cookingTime);
			}

			public RecipeSerializer<?> getType() {
				return serializer;
			}

			public ResourceLocation getId() {
				return recipeId;
			}

			@Nullable
			public JsonObject serializeAdvancement() {
				return null;
			}

			public ResourceLocation getAdvancementId() {
				return new ResourceLocation("");
			}
		});
	}

}
