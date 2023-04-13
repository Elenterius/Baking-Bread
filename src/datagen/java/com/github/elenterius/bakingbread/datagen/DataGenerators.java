package com.github.elenterius.bakingbread.datagen;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.datagen.langs.EnglishLangProvider;
import com.github.elenterius.bakingbread.datagen.models.ModBlockStateProvider;
import com.github.elenterius.bakingbread.datagen.models.ModItemModelProvider;
import com.github.elenterius.bakingbread.datagen.recipes.ModRecipeProvider;
import com.github.elenterius.bakingbread.datagen.tags.ModBlockTagsProvider;
import com.github.elenterius.bakingbread.datagen.tags.ModItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BakingBreadMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {

	private DataGenerators() {
	}

	@SubscribeEvent
	public static void gatherData(final GatherDataEvent event) {
		final DataGenerator generator = event.getGenerator();
		final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		ModBlockTagsProvider blockTags = new ModBlockTagsProvider(generator, existingFileHelper);
		generator.addProvider(true, blockTags);
		generator.addProvider(true, new ModItemTagsProvider(generator, blockTags, existingFileHelper));

		generator.addProvider(true, new ModRecipeProvider(generator));
//		generator.addProvider(true, new ModLootTableProvider(generator));

		generator.addProvider(true, new ModBlockStateProvider(generator, existingFileHelper));
		generator.addProvider(true, new ModItemModelProvider(generator, existingFileHelper));

		generator.addProvider(true, new EnglishLangProvider(generator));
	}

}
