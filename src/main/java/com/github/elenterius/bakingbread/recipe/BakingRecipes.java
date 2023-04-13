package com.github.elenterius.bakingbread.recipe;

import com.github.elenterius.bakingbread.init.ModRecipeSerializers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class BakingRecipes {

	public static class BreadBaking extends NBTCookingRecipe.Smelting {

		public BreadBaking(ResourceLocation id, String group, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
			super(id, group, ingredient, result, experience, cookingTime);
		}

		@Override
		public void copyNBT(CompoundTag ingredientTag, CompoundTag resultTag) {
			//TODO: copy NBT
		}

		@Override
		public RecipeSerializer<?> getSerializer() {
			return ModRecipeSerializers.BREAD_BAKING.get();
		}
	}
}
