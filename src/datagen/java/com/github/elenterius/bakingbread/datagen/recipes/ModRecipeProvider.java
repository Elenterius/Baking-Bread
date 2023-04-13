package com.github.elenterius.bakingbread.datagen.recipes;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.api.Grain;
import com.github.elenterius.bakingbread.api.IGrain;
import com.github.elenterius.bakingbread.datagen.tags.ModItemTagsProvider;
import com.github.elenterius.bakingbread.init.ModItems;
import com.github.elenterius.bakingbread.init.ModRecipeSerializers;
import com.github.elenterius.bakingbread.item.FlourItem;
import com.github.elenterius.bakingbread.recipe.BakeryRecipes;
import java.util.Arrays;
import java.util.function.Consumer;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	protected static ItemPredicate createPredicate(ItemLike item) {
		return ItemPredicate.Builder.item().of(item).build();
	}

	protected static ItemPredicate createPredicate(TagKey<Item> tag) {
		return ItemPredicate.Builder.item().of(tag).build();
	}

	protected static InventoryChangeTrigger.TriggerInstance hasItems(ItemLike... itemProviders) {
		ItemPredicate[] predicates = Arrays.stream(itemProviders).map(ModRecipeProvider::createPredicate).toArray(ItemPredicate[]::new);
		return inventoryTrigger(predicates);
	}

	protected static String hasName(ItemLike itemLike) {
		return "has_" + itemName(itemLike);
	}

	protected static String itemName(ItemLike itemLike) {
		ResourceLocation key = ForgeRegistries.ITEMS.getKey(itemLike.asItem());
		return key != null ? key.getPath() : "unknown";
	}

	protected static String hasName(TagKey<Item> tag) {
		return "has_" + tagName(tag);
	}

	protected static String tagName(TagKey<Item> tag) {
		return tag.location().getPath().replace("/", "_");
	}

	protected static ResourceLocation simpleRecipeId(ItemLike itemLike) {
		return BakingBreadMod.createRL(itemName(itemLike));
	}

	protected static ResourceLocation conversionRecipeId(ItemLike result, ItemLike ingredient) {
		return BakingBreadMod.createRL(itemName(result) + "_from_" + itemName(ingredient));
	}

	protected static ResourceLocation conversionRecipeId(ItemLike result, TagKey<Item> ingredient) {
		return BakingBreadMod.createRL(itemName(result) + "_from_" + tagName(ingredient));
	}


	protected static ResourceLocation smeltingRecipeId(ItemLike itemLike) {
		return BakingBreadMod.createRL(itemName(itemLike) + "_from_smelting");
	}

	protected static ResourceLocation blastingRecipeId(ItemLike itemLike) {
		return BakingBreadMod.createRL(itemName(itemLike) + "_from_blasting");
	}

	@Override
	public String getName() {
		return StringUtils.capitalize(BakingBreadMod.MOD_ID) + " " + super.getName();
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		registerWorkbenchRecipes(consumer);
		registerCookingRecipes(consumer);
	}

	private void registerCookingRecipes(Consumer<FinishedRecipe> consumer) {
//		SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.STONE_POWDER.get()), Items.GLASS_PANE, 0.01F, 100).unlockedBy(hasName(ModItems.STONE_POWDER.get()), has(ModItems.STONE_POWDER.get()))
//			.save(consumer, getBlastingRecipeId(Items.GLASS_PANE));

		NBTCookingRecipeBuilder
			.cooking(Ingredient.of(ModItems.ROLL_DOUGH_SHAPE.get()), ModItems.BREAD_ROLL.get(), 0.01F, 100, ModRecipeSerializers.BREAD_BAKING.get())
			.save(consumer, BakingBreadMod.createRL("bread_roll_baking"));

		NBTCookingRecipeBuilder
			.cooking(Ingredient.of(ModItems.OVAL_DOUGH_SHAPE.get()), ModItems.BREAD_OVAL.get(), 0.01F * 5, 100 * 5, ModRecipeSerializers.BREAD_BAKING.get())
			.save(consumer, BakingBreadMod.createRL("oval_bread_baking"));

		NBTCookingRecipeBuilder
			.cooking(Ingredient.of(ModItems.BATON_DOUGH_SHAPE.get()), ModItems.BREAD_BATON.get(), 0.01F * 5, 100 * 5, ModRecipeSerializers.BREAD_BAKING.get())
			.save(consumer, BakingBreadMod.createRL("baton_bread_baking"));
	}

	private void registerWorkbenchRecipes(Consumer<FinishedRecipe> consumer) {
		ModItems.findItems(FlourItem.class).forEach(item -> grainToFlourRecipe(item, consumer));
		SpecialRecipeBuilder.special(ModRecipeSerializers.DOUGH.get()).save(consumer, BakingBreadMod.createRL("dough").toString());

		WorkbenchRecipeBuilder.shapedDough(ModItems.OVAL_DOUGH_SHAPE.get(), 4)
			.define('D', ModItems.DOUGH.get())
			.pattern(" D ")
			.pattern("DDD")
			.unlockedBy(hasName(ModItems.DOUGH.get()), has(ModItems.DOUGH.get())).save(consumer);

		WorkbenchRecipeBuilder.shapedDough(ModItems.BATON_DOUGH_SHAPE.get(), 2)
			.define('D', ModItems.DOUGH.get())
			.pattern(" D")
			.pattern("D ")
			.unlockedBy(hasName(ModItems.DOUGH.get()), has(ModItems.DOUGH.get())).save(consumer);

		WorkbenchRecipeBuilder.shapedDough(ModItems.ROLL_DOUGH_SHAPE.get(), 5)
			.define('D', ModItems.DOUGH.get())
			.pattern("D ")
			.unlockedBy(hasName(ModItems.DOUGH.get()), has(ModItems.DOUGH.get())).save(consumer);
	}

	protected void grainToFlourRecipe(FlourItem item, Consumer<FinishedRecipe> consumer) {
		IGrain igrain = item.getGrain();
		if (igrain instanceof Grain grain) {
			TagKey<Item> tagKey = ModItemTagsProvider.Forge.grainTag(grain);

			ResourceLocation id = BakingBreadMod.createRL("flour/" + itemName(item) + "_from_" + grain.getNameId() + "_grain");

			WorkbenchRecipeBuilder.shapeless(item)
				.requires(tagKey)
				.unlockedBy(hasName(tagKey), has(tagKey))
				.save(consumer, id);
		}
	}

	static final class WorkbenchRecipeBuilder {

		private WorkbenchRecipeBuilder() {
		}

		public static NBTShapedRecipeBuilder<BakeryRecipes.DoughKneading> shapedDough(ItemLike result) {
			return NBTShapedRecipeBuilder.shaped(result, ModRecipeSerializers.SHAPED_DOUGH.get());
		}

		public static NBTShapedRecipeBuilder<BakeryRecipes.DoughKneading> shapedDough(ItemLike result, int count) {
			return NBTShapedRecipeBuilder.shaped(result, count, ModRecipeSerializers.SHAPED_DOUGH.get());
		}

		public static ShapedRecipeBuilder shaped(ItemLike result) {
			return ShapedRecipeBuilder.shaped(result);
		}

		public static ShapedRecipeBuilder shaped(ItemLike result, int count) {
			return ShapedRecipeBuilder.shaped(result, count);
		}

		public static ShapelessRecipeBuilder shapeless(ItemLike result) {
			return ShapelessRecipeBuilder.shapeless(result);
		}

		public static ShapelessRecipeBuilder shapeless(ItemLike result, int count) {
			return ShapelessRecipeBuilder.shapeless(result, count);
		}
	}

}
