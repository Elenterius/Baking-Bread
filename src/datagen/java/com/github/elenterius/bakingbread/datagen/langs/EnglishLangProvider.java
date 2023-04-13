package com.github.elenterius.bakingbread.datagen.langs;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.api.Grain;
import com.github.elenterius.bakingbread.init.ModItems;
import com.github.elenterius.bakingbread.item.FlourItem;
import java.util.Locale;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import org.codehaus.plexus.util.StringUtils;

public class EnglishLangProvider extends LanguageProvider {

	private static String fileName(Locale locale) {
		return (locale.getLanguage() + "_" + locale.getCountry()).toLowerCase(Locale.ENGLISH);
	}

	public EnglishLangProvider(DataGenerator gen) {
		super(gen, BakingBreadMod.MOD_ID, fileName(Locale.US));
	}

	@Override
	protected void addTranslations() {
		addItem(ModItems.DOUGH, "Dough");
		addItem(ModItems.SOURDOUGH_STARTER, "Sourdough Starter");
		addItem(ModItems.WILD_YEAST_STARTER, "Wild Yeast Starter");
		addItem(ModItems.OVAL_LOAF, "Oval Loaf");
		addItem(ModItems.TIN_LOAF, "Tin Loaf");
		addItem(ModItems.WAND_LOAF, "Baton / Baguette");
		addItem(ModItems.ROLL, "Roll");

		addItem(ModItems.WHEAT_FLOUR, "Wheat Flour");
		addItem(ModItems.RYE_FLOUR, "Rye Flour");
		addItem(ModItems.SPELT_FLOUR, "Spelt Flour");
		addItem(ModItems.EMMER_FLOUR, "Emmer Flour");
		addItem(ModItems.EINKORN_FLOUR, "Einkorn Flour");
		addItem(ModItems.BARLEY_FLOUR, "Barley Flour");
		addItem(ModItems.OAT_FLOUR, "Oat Flour");
		addItem(ModItems.MILLET_FLOUR, "Millet Flour");
		addItem(ModItems.MAIZE_FLOUR, "Maize Flour");
		addItem(ModItems.RICE_FLOUR, "Rice Flour");
		addItem(ModItems.AMARANTH_FLOUR, "Amaranth Flour");
		addItem(ModItems.BUCKWHEAT_FLOUR, "Buckwheat Flour");
		addItem(ModItems.QUINOA_FLOUR, "Quinoa Flour");

		ModItems.findItems(FlourItem.class).forEach(this::addGrain);
	}

	protected void addGrain(FlourItem item) {
		if (item.getGrain() instanceof Grain grain) {
			String name = StringUtils.capitaliseAllWords(grain.getNameId().replace("_", " "));
			add(grain.getDescriptionId(), name);
		}
	}
}
