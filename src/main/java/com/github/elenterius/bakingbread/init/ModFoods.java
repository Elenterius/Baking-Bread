package com.github.elenterius.bakingbread.init;

import net.minecraft.world.food.FoodProperties;

public final class ModFoods {

	public static final float BASE_SATURATION = 0.6f / 5;
	public static final int BASE_NUTRITION = 1;

	public static final FoodProperties OVAL_BREAD = bread(6).build();
	public static final FoodProperties VANILLA_BREAD = bread(5).build();
	public static final FoodProperties BATON_BREAD = bread(5).build();
	public static final FoodProperties TIN_BREAD = bread(5).build();
	public static final FoodProperties ROLL = bread(1).fast().build();

	private ModFoods() {
	}

	private static FoodProperties.Builder bread(int multiplier) {
		return new FoodProperties.Builder()
			.nutrition(BASE_NUTRITION * multiplier)
			.saturationMod(BASE_SATURATION * multiplier);
	}

}
