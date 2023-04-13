package com.github.elenterius.bakingbread.init;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.recipe.BakingRecipes;
import com.github.elenterius.bakingbread.recipe.BakingRecipes.BreadBaking;
import com.github.elenterius.bakingbread.recipe.DoughRecipe;
import com.github.elenterius.bakingbread.recipe.NBTCookingSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModRecipeSerializers {

	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BakingBreadMod.MOD_ID);

	public static final RegistryObject<SimpleRecipeSerializer<DoughRecipe>> DOUGH = RECIPE_SERIALIZERS.register("crafting_special_dough", () -> new SimpleRecipeSerializer<>(DoughRecipe::new));
	public static final RegistryObject<NBTCookingSerializer<BreadBaking>> BREAD_BAKING = RECIPE_SERIALIZERS.register("cooking_special_bread",
		() -> new NBTCookingSerializer<>(BakingRecipes.BreadBaking::new, 1000));

	private ModRecipeSerializers() {
	}


}
