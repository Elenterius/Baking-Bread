package com.github.elenterius.bakingbread.recipe;

import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public abstract class NBTShapedRecipe extends ShapedRecipe {

	public NBTShapedRecipe(ResourceLocation id, String group, int width, int height, NonNullList<Ingredient> recipeItems, ItemStack result) {
		super(id, group, width, height, recipeItems, result);
	}

	@Override
	public ItemStack assemble(CraftingContainer container) {
		List<ItemStack> ingredients = IntStream
			.range(0, container.getContainerSize())
			.mapToObj(container::getItem).filter(stack -> !stack.isEmpty()).toList();

		ItemStack assembled = getResultItem().copy();
		applyNBT(ingredients, assembled);
		return assembled;
	}

	public abstract void applyNBT(List<ItemStack> ingredients, ItemStack assembledResult);

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public abstract RecipeSerializer<?> getSerializer();

}
