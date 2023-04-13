package com.github.elenterius.bakingbread.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public sealed interface ITintColorHolder<T> {

	/**
	 * @return ARGB32 color
	 */
	static int getColor(ItemStack stack) {
		return stack.getItem() instanceof ITintColorHolder.TintedItem holder ? holder.getTintColor(stack) : 0xFF_FFFFFF;
	}

	static void setColor(ItemStack stack, int color) {
		if (stack.getItem() instanceof ITintColorHolder.TintedItem holder) {
			holder.setTintColor(stack, color);
		}
	}

	/**
	 * @return ARGB32 color
	 */
	int getTintColor(T holder);

	/**
	 * @param color ARGB32 color
	 */
	void setTintColor(T holder, int color);

	non-sealed interface TintedItem extends ITintColorHolder<ItemStack> {

		String COLOR_TAG = "TintColor";

		@Override
		default int getTintColor(ItemStack stack) {
			CompoundTag tag = stack.getOrCreateTag();
			return tag.contains(COLOR_TAG, Tag.TAG_ANY_NUMERIC) ? tag.getInt(COLOR_TAG) : 0xFF_FFFFFF;
		}

		@Override
		default void setTintColor(ItemStack stack, int color) {
			stack.getOrCreateTag().putInt(COLOR_TAG, color);
		}
	}
}
