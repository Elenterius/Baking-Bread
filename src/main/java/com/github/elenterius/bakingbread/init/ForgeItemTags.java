package com.github.elenterius.bakingbread.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ForgeItemTags {

	//Crafting
	//vanilla: 3x Grain (Item) = 1x Bread
	//modded:  1x Grain (Item) = 1x Dough = 1x Bread

	public static final TagKey<Item> GRAIN = tag("grain");

	public static final TagKey<Item> FLOUR = tag("flour");
	public static final TagKey<Item> FLOUR_WHEAT = tag("flour/wheat");

	public static final TagKey<Item> DOUGH = tag("dough");
	public static final TagKey<Item> DOUGH_WHEAT = tag("dough/wheat");

	public static final TagKey<Item> BREAD = tag("bread");
	public static final TagKey<Item> BREAD_WHEAT = tag("bread/wheat");

	public static final TagKey<Item> MILK = tag("milk");
	public static final TagKey<Item> MILK_BUCKET = tag("milk/milk");
	public static final TagKey<Item> MILK_BOTTLE = tag("milk/milk_bottle");

	public static final TagKey<Item> FRUITS = tag("fruits");
	public static final TagKey<Item> NUTS = tag("nuts");

	private ForgeItemTags() {
	}

	private static TagKey<Item> tag(String path) {
		return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", path));
	}

}
