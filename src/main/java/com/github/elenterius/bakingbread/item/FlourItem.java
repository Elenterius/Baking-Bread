package com.github.elenterius.bakingbread.item;

import com.github.elenterius.bakingbread.api.IGrain;
import com.github.elenterius.bakingbread.item.ITintColorHolder.TintedItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FlourItem extends Item implements TintedItem {

	private final IGrain grain;

	public FlourItem(Properties properties, IGrain grain) {
		super(properties);
		this.grain = grain;
	}

	public IGrain getGrain() {
		return grain;
	}

	@Override
	public int getTintColor(ItemStack stack) {
		return grain.getColor();
	}

	@Override
	public void setTintColor(ItemStack stack, int color) {
		//do nothing
	}

	@Override
	protected String getOrCreateDescriptionId() {
		return super.getOrCreateDescriptionId();
	}
}
