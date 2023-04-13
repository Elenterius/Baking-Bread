package com.github.elenterius.bakingbread.recipe;

import com.github.elenterius.bakingbread.api.IGrain;
import com.github.elenterius.bakingbread.init.ModRecipeSerializers;
import com.github.elenterius.bakingbread.item.DoughItem;
import com.github.elenterius.bakingbread.item.FlourItem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class DoughRecipe extends CustomRecipe {

	public DoughRecipe(ResourceLocation id) {
		super(id);
	}

	@Override
	public boolean matches(CraftingContainer container, Level level) {
		ItemStack waterBucket = ItemStack.EMPTY;
		boolean hasFlour = false;

		for (int i = 0; i < container.getContainerSize(); i++) {
			ItemStack stack = container.getItem(i);
			if (stack.isEmpty()) {
				continue;
			}

			Item item = stack.getItem();
			if (item == Items.WATER_BUCKET) {
				if (!waterBucket.isEmpty()) {
					return false;
				}
				waterBucket = stack;
			} else if (item instanceof DyeItem) {
			} else {
				if (!(item instanceof FlourItem)) {
					return false;
				}
				hasFlour = true;
			}
		}

		return !waterBucket.isEmpty() && hasFlour;
	}

	@Override
	public ItemStack assemble(CraftingContainer container) {
		ItemStack waterBucket = ItemStack.EMPTY;
		List<IGrain> flours = new ArrayList<>();
		List<ItemStack> ingredients = new ArrayList<>();

		for (int i = 0; i < container.getContainerSize(); i++) {
			ItemStack stack = container.getItem(i);
			if (stack.isEmpty()) {
				continue;
			}

			Item item = stack.getItem();
			if (item == Items.WATER_BUCKET) {
				if (!waterBucket.isEmpty()) {
					return ItemStack.EMPTY;
				}
				waterBucket = stack;
			} else if (item instanceof DyeItem) {
				ingredients.add(stack);
			} else {
				if (!(item instanceof FlourItem flourItem)) {
					return ItemStack.EMPTY;
				}
				flours.add(flourItem.getGrain());
			}
		}

		return !waterBucket.isEmpty() && !flours.isEmpty() ? DoughItem.create(flours, ingredients) : ItemStack.EMPTY;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipeSerializers.DOUGH.get();
	}

}
