package com.github.elenterius.bakingbread.recipe;

import com.github.elenterius.bakingbread.init.ModRecipeSerializers;
import com.github.elenterius.bakingbread.item.ITintColorHolder;
import com.github.elenterius.bakingbread.util.ColorUtil;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class DoughMouldingRecipe extends NBTShapedRecipe {

	public DoughMouldingRecipe(ResourceLocation id, String group, int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result) {
		super(id, group, width, height, recipeItems, result);
	}

	@Override
	public void applyNBT(List<ItemStack> ingredients, ItemStack assembledResult) {
		int[] colors = ingredients.stream().mapToInt(ITintColorHolder::getColor).toArray();
		int color = ColorUtil.ARGB32.mixSubtractive(colors);
		ITintColorHolder.setColor(assembledResult, color);
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.DOUGH_MOULDING.get();
	}
}
