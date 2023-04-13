package com.github.elenterius.bakingbread.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class NBTShapedSerializer<T extends NBTShapedRecipe> implements RecipeSerializer<T> {

	private final ShapedBakery<T> factory;

	public NBTShapedSerializer(ShapedBakery<T> factory) {
		this.factory = factory;
	}

	@Override
	public T fromJson(ResourceLocation recipeId, JsonObject json) {
		ShapedRecipe shapedRecipe = RecipeSerializer.SHAPED_RECIPE.fromJson(recipeId, json);
		return factory.create(recipeId, shapedRecipe.getGroup(), shapedRecipe.getWidth(), shapedRecipe.getHeight(), shapedRecipe.getIngredients(), shapedRecipe.getResultItem());
	}

	@Override
	public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
		int width = buffer.readVarInt();
		int height = buffer.readVarInt();
		String group = buffer.readUtf();

		NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
		ingredients.replaceAll(ignored -> Ingredient.fromNetwork(buffer));

		ItemStack result = buffer.readItem();
		return factory.create(recipeId, group, width, height, ingredients, result);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, T recipe) {
		buffer.writeVarInt(recipe.getWidth());
		buffer.writeVarInt(recipe.getHeight());
		buffer.writeUtf(recipe.getGroup());

		for (Ingredient ingredient : recipe.getIngredients()) {
			ingredient.toNetwork(buffer);
		}

		buffer.writeItem(recipe.getResultItem());
	}

	public interface ShapedBakery<T extends NBTShapedRecipe> {

		T create(ResourceLocation id, String group, int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result);
	}
}
