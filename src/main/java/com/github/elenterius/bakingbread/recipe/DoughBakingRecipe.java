package com.github.elenterius.bakingbread.recipe;

import com.github.elenterius.bakingbread.init.ModRecipeSerializers;
import com.github.elenterius.bakingbread.item.ITintColorHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class DoughBakingRecipe extends NBTCookingRecipe.Smelting {

	public DoughBakingRecipe(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
		super(id, group, ingredient, result, experience, cookingTime);
	}

	@Override
	public void applyNBT(ItemStack ingredient, ItemStack assembledResult) {
		ITintColorHolder.setColor(assembledResult, ITintColorHolder.getColor(ingredient));
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.DOUGH_BAKING.get();
	}
}
