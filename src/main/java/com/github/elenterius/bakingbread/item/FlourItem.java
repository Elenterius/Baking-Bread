package com.github.elenterius.bakingbread.item;

import com.github.elenterius.bakingbread.api.IGrain;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FlourItem extends Item {

	private final IGrain grain;

	public FlourItem(Properties properties, IGrain grain) {
		super(properties);
		this.grain = grain;
	}

	public IGrain getGrain() {
		return grain;
	}

	public int getTintColor(ItemStack stack) {
		return grain.getColor();
	}

	@Override
	protected String getOrCreateDescriptionId() {
		return super.getOrCreateDescriptionId();
	}
}
