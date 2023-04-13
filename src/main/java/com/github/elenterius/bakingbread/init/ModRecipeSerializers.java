package com.github.elenterius.bakingbread.init;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.recipe.BakeryRecipes;
import com.github.elenterius.bakingbread.recipe.DoughRecipe;
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

	public static final RegistryObject<SimpleRecipeSerializer<DoughRecipe>> DOUGH = register("crafting_special_dough", () -> new SimpleRecipeSerializer<>(DoughRecipe::new));
	public static final RegistryObject<NBTShapedSerializer<BakeryRecipes.DoughKneading>> SHAPED_DOUGH = register("crafting_special_shaped_dough",
		() -> new NBTShapedSerializer<>(BakeryRecipes.DoughKneading::new));

	public static final RegistryObject<NBTCookingSerializer<BakeryRecipes.BreadBaking>> BREAD_BAKING = register("baking_special_bread",
		() -> new NBTCookingSerializer<>(BakeryRecipes.BreadBaking::new, 1000));

	private ModRecipeSerializers() {
	}

	private static <S extends RecipeSerializer<R>, R extends Recipe<?>> RegistryObject<S> register(String name, Supplier<S> supplier) {
		return RECIPE_SERIALIZERS.register(name, supplier);
	}

}
