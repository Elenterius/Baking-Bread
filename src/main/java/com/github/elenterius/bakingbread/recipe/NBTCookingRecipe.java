package com.github.elenterius.bakingbread.recipe;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;

public abstract class NBTCookingRecipe extends AbstractCookingRecipe {

	private NBTCookingRecipe(RecipeType<? extends AbstractCookingRecipe> type, ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
		super(type, id, group, ingredient, result, experience, cookingTime);
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public ItemStack assemble(Container container) {
		ItemStack ingredient = container.getItem(0);
		ItemStack copy = result.copy();
		copyNBT(ingredient.getOrCreateTag(), result.getOrCreateTag());
		return copy;
	}

	public abstract void copyNBT(CompoundTag ingredientTag, CompoundTag resultTag);

	@Override
	public ItemStack getResultItem() {
		return ItemStack.EMPTY;
	}

	public ItemStack getResultRaw() {
		return result;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public abstract class Campfire extends NBTCookingRecipe {

		protected Campfire(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
			super(RecipeType.CAMPFIRE_COOKING, id, group, ingredient, result, experience, cookingTime);
		}

		@Override
		public ItemStack getToastSymbol() {
			return new ItemStack(Blocks.CAMPFIRE);
		}
	}

	public abstract static class Smelting extends NBTCookingRecipe {

		protected Smelting(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
			super(RecipeType.SMELTING, id, group, ingredient, result, experience, cookingTime);
		}

		@Override
		public ItemStack getToastSymbol() {
			return new ItemStack(Blocks.FURNACE);
		}
	}

	public abstract static class Smoking extends NBTCookingRecipe {

		protected Smoking(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
			super(RecipeType.SMOKING, id, group, ingredient, result, experience, cookingTime);
		}

		@Override
		public ItemStack getToastSymbol() {
			return new ItemStack(Blocks.SMOKER);
		}
	}

	public abstract static class Blasting extends NBTCookingRecipe {

		protected Blasting(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
			super(RecipeType.BLASTING, id, group, ingredient, result, experience, cookingTime);
		}

		@Override
		public ItemStack getToastSymbol() {
			return new ItemStack(Blocks.BLAST_FURNACE);
		}
	}
}
