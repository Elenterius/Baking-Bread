package com.github.elenterius.bakingbread.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class NBTCookingSerializer<T extends NBTCookingRecipe> implements RecipeSerializer<T> {

	private final int defaultCookingTime;
	private final NBTBakery<T> factory;

	public NBTCookingSerializer(NBTBakery<T> factory, int defaultCookingTime) {
		this.defaultCookingTime = defaultCookingTime;
		this.factory = factory;
	}

	@Override
	public T fromJson(ResourceLocation recipeId, JsonObject json) {
		String group = GsonHelper.getAsString(json, "group", "");
		JsonElement jsonElement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
		Ingredient ingredient = Ingredient.fromJson(jsonElement);

		if (!json.has("result")) {
			throw new JsonSyntaxException("Missing result, expected to find a string or object");
		}

		ItemStack result;
		if (json.get("result").isJsonObject()) {
			result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
		} else {
			ResourceLocation key = new ResourceLocation(GsonHelper.getAsString(json, "result"));
			result = new ItemStack(Registry.ITEM.getOptional(key).orElseThrow(() -> new IllegalStateException("Item: " + key + " does not exist")));
		}

		float experience = GsonHelper.getAsFloat(json, "experience", 0);
		int cookingTime = GsonHelper.getAsInt(json, "cookingtime", defaultCookingTime);
		return this.factory.create(recipeId, group, ingredient, result, experience, cookingTime);
	}

	@Override
	public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
		String group = buffer.readUtf();
		Ingredient ingredient = Ingredient.fromNetwork(buffer);
		ItemStack result = buffer.readItem();
		float experience = buffer.readFloat();
		int cookingTime = buffer.readVarInt();
		return factory.create(recipeId, group, ingredient, result, experience, cookingTime);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, T recipe) {
		buffer.writeUtf(recipe.getGroup());
		recipe.getIngredient().toNetwork(buffer);
		buffer.writeItem(recipe.getResultRaw());
		buffer.writeFloat(recipe.getExperience());
		buffer.writeVarInt(recipe.getCookingTime());
	}

	public interface NBTBakery<T extends NBTCookingRecipe> {

		T create(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int cookingTime);
	}
}
