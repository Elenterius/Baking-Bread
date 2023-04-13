package com.github.elenterius.bakingbread.datagen.tags;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.api.Grain;
import com.github.elenterius.bakingbread.api.Grains;
import com.github.elenterius.bakingbread.init.ModItemTags;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {

	public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(dataGenerator, blockTagProvider, BakingBreadMod.MOD_ID, existingFileHelper);
	}

	public static class Forge {

		public static TagKey<Item> cropsTag(Grain grain) {
			return cropsTag(grain.getNameId());
		}

		public static TagKey<Item> cropsTag(String name) {
			return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "crops/" + name));
		}

		public static TagKey<Item> grainTag(Grain grain) {
			return grainTag(grain.getNameId());
		}

		public static TagKey<Item> grainTag(String name) {
			return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", "grain/" + name));
		}

		public static TagKey<Item> tag(String name) {
			return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", name));
		}
	}


	@Override
	protected void addTags() {
		//fix PHC2Crops tag

		//FORGE BUG/BROKEN (https://github.com/MinecraftForge/MinecraftForge/issues/8949)
		//		TagKey<Item> nuts = Forge.tag("nuts");
		//		tag(nuts)
		//			.remove(new ResourceLocation("pamhc2crops:peanutitem"))
		//			.getInternalBuilder().removeTag(new ResourceLocation("forge:nuts/peanut"), modId);

		//Grain
		addGrainCerealsTags();
		addPulsesAndLegumesTags();
		addOilseedsTags();
	}

	private void addGrainCerealsTags() {
		TagKey<Item> wheat = Forge.grainTag(Grains.WHEAT);
		tag(wheat).add(Items.WHEAT);
		TagKey<Item> rye = Forge.grainTag(Grains.RYE);
		tag(rye);
		TagKey<Item> spelt = Forge.grainTag(Grains.SPELT);
		tag(spelt);
		TagKey<Item> emmer = Forge.grainTag(Grains.EMMER);
		tag(emmer);
		TagKey<Item> einkorn = Forge.grainTag(Grains.EINKORN);
		tag(einkorn);

		TagKey<Item> barley = Forge.grainTag(Grains.BARLEY);
		tag(barley);
		TagKey<Item> oat = Forge.grainTag(Grains.OAT);
		tag(oat);

		TagKey<Item> millet = Forge.grainTag(Grains.MILLET);
		TagKey<Item> sorghum = Forge.grainTag("sorghum");
		tag(sorghum).addOptionalTag(new ResourceLocation("simplefarming:sorghum"));
		tag(millet).addTag(sorghum).addOptional(new ResourceLocation("pamhc2crops:milletitem"));

		TagKey<Item> maize = Forge.grainTag(Grains.MAIZE);
		TagKey<Item> corn = Forge.grainTag("corn");
		tag(corn);
		tag(maize).addTag(corn).addOptional(new ResourceLocation("simplefarming:corn"));

		TagKey<Item> rice = Forge.grainTag(Grains.RICE);
		tag(rice);

		TagKey<Item> amaranth = Forge.grainTag(Grains.AMARANTH);
		tag(amaranth);
		TagKey<Item> buckwheat = Forge.grainTag(Grains.BUCKWHEAT);
		tag(buckwheat);
		TagKey<Item> quinoa = Forge.grainTag(Grains.QUINOA);
		tag(quinoa);

		TagKey<Item> grain = Forge.tag("grain");
		//noinspection unchecked
		tag(grain)
			.addTags(wheat, rye, spelt, emmer, einkorn)
			.addTags(barley, oat, maize, rice)
			.addTags(millet, sorghum)
			.addTags(amaranth, buckwheat, quinoa);

		//Add Cereals

		//noinspection unchecked
		tag(ModItemTags.BREAD_CEREAL).addTags(wheat, rye, spelt, emmer, einkorn);

		//noinspection unchecked
		tag(ModItemTags.OTHER_CEREAL).addTags(barley, oat, millet, maize, rice);

		//noinspection unchecked
		tag(ModItemTags.PSEUDO_CEREAL).addTags(amaranth, buckwheat, quinoa);
	}

	private void addPulsesAndLegumesTags() {
		TagKey<Item> chickpeas = Forge.tag("pulses/chickpeas");
		tag(chickpeas);

		TagKey<Item> beans = Forge.tag("pulses/beans");
		tag(beans);

		TagKey<Item> peas = Forge.tag("pulses/peas");
		tag(peas).addOptional(new ResourceLocation("simplefarming:pea_pod")).addOptional(new ResourceLocation("pamhc2crops:peasitem"));

		TagKey<Item> lentils = Forge.tag("pulses/lentils");
		tag(lentils);

		TagKey<Item> lupins = Forge.tag("pulses/lupins");
		tag(lupins);

		TagKey<Item> peanuts = Forge.tag("pulses/peanuts");
		tag(peanuts).addOptional(new ResourceLocation("simplefarming:peanut")).addOptional(new ResourceLocation("pamhc2crops:peanutitem"));

		TagKey<Item> soybeans = Forge.tag("pulses/soybeans");
		tag(soybeans).addOptional(new ResourceLocation("simplefarming:soybean"));

		TagKey<Item> pulses = Forge.tag("pulses");
		tag(pulses).addTags(chickpeas, beans, peas, lentils, lupins, peanuts);
	}

	private void addOilseedsTags() {
		//sunflower seed
		//poppy seed
		// safflower
		// rapeseed
		// hemp seed

		TagKey<Item> flax = Forge.tag("seeds/flax"); //linseed
		tag(flax);

		TagKey<Item> sesame = Forge.tag("seeds/sesameseeds");
		tag(sesame);

		TagKey<Item> mustard = Forge.tag("seeds/mustardseeds");
		tag(mustard);

		TagKey<Item> oilseeds = Forge.tag("oilseeds");
		//noinspection unchecked
		tag(oilseeds)
			.addTags(flax, sesame, mustard)
			.addTag(Tags.Items.SEEDS_PUMPKIN)
		;
	}

	@Override
	public String getName() {
		return StringUtils.capitalize(modId) + " " + super.getName();
	}

}
