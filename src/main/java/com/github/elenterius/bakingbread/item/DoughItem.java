package com.github.elenterius.bakingbread.item;

import com.github.elenterius.bakingbread.api.IGrain;
import com.github.elenterius.bakingbread.init.ModItems;
import com.github.elenterius.bakingbread.util.ColorUtil;
import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DoughItem extends Item {

	protected static final String COLOR_TAG = "Color";

	public DoughItem(Properties properties) {
		super(properties);
	}

	public int getTintColor(ItemStack stack) {
		return stack.getOrCreateTag().getInt(COLOR_TAG);
	}

	public void setTintColor(ItemStack stack, int color) {
		stack.getOrCreateTag().putInt(COLOR_TAG, color);
	}

	public static ItemStack create(List<IGrain> flours, List<ItemStack> ingredients) {
		DoughItem dough = ModItems.DOUGH.get();
		ItemStack stack = new ItemStack(dough, flours.size());

		//TODO: set flour/dough type

		int[] colors = IntStream.concat(
			flours.stream().mapToInt(IGrain::getColor),
			ingredients.stream()
				.map(ItemStack::getItem)
				.filter(DyeItem.class::isInstance).map(DyeItem.class::cast)
				.mapToInt(dye -> ColorUtil.textureDiffuseColor(dye.getDyeColor()))
		).toArray();
		int color = ColorUtil.ARGB32.mixSubtractive(colors);

		dough.setTintColor(stack, color);

		return stack;
	}
}
