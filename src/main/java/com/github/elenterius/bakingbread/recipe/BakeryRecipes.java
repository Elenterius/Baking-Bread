package com.github.elenterius.bakingbread.recipe;

import com.github.elenterius.bakingbread.init.ModRecipeSerializers;
import com.github.elenterius.bakingbread.item.ITintColorHolder;
import com.github.elenterius.bakingbread.util.ColorUtil.ARGB32;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class BakeryRecipes {

	public static class BreadBaking extends NBTCookingRecipe.Smelting {

		public BreadBaking(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
			super(id, group, ingredient, result, experience, cookingTime);
		}

		@Override
		public void applyNBT(ItemStack ingredient, ItemStack assembledResult) {
			ITintColorHolder.setColor(assembledResult, ITintColorHolder.getColor(ingredient));
		}

		@Override
		public RecipeSerializer<?> getSerializer() {
			return ModRecipeSerializers.BREAD_BAKING.get();
		}
	}

	public static class DoughKneading extends NBTShapedRecipe {

		public DoughKneading(ResourceLocation id, String group, int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result) {
			super(id, group, width, height, recipeItems, result);
		}

		@Override
		public void applyNBT(List<ItemStack> ingredients, ItemStack assembledResult) {
			int[] colors = ingredients.stream().mapToInt(ITintColorHolder::getColor).toArray();
			int color = ARGB32.mixSubtractive(colors);
			ITintColorHolder.setColor(assembledResult, color);
		}

		@Override
		public RecipeSerializer<?> getSerializer() {
			return ModRecipeSerializers.SHAPED_DOUGH.get();
		}
	}
}
