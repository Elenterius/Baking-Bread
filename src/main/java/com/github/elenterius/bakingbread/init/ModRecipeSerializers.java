package com.github.elenterius.bakingbread.init;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.recipe.DoughBakingRecipe;
import com.github.elenterius.bakingbread.recipe.DoughMixingRecipe;
import com.github.elenterius.bakingbread.recipe.DoughMouldingRecipe;
import com.github.elenterius.bakingbread.recipe.NBTCookingSerializer;
import com.github.elenterius.bakingbread.recipe.NBTShapedSerializer;
import java.util.function.Supplier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModRecipeSerializers {

	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BakingBreadMod.MOD_ID);

	public static final RegistryObject<SimpleRecipeSerializer<DoughMixingRecipe>> DOUGH_MIXING = register("crafting_special_dough_mixing", () -> new SimpleRecipeSerializer<>(DoughMixingRecipe::new));
	public static final RegistryObject<NBTShapedSerializer<DoughMouldingRecipe>> DOUGH_MOULDING = register("crafting_special_dough_moulding",
		() -> new NBTShapedSerializer<>(DoughMouldingRecipe::new));

	public static final RegistryObject<NBTCookingSerializer<DoughBakingRecipe>> DOUGH_BAKING = register("cooking_special_dough_baking",
		() -> new NBTCookingSerializer<>(DoughBakingRecipe::new, 100));

	private ModRecipeSerializers() {
	}

	private static <S extends RecipeSerializer<R>, R extends Recipe<?>> RegistryObject<S> register(String name, Supplier<S> supplier) {
		return RECIPE_SERIALIZERS.register(name, supplier);
	}

}
