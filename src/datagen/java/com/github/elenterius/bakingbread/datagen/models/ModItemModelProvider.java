package com.github.elenterius.bakingbread.datagen.models;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.init.ModItems;
import com.github.elenterius.bakingbread.item.FlourItem;
import java.util.Objects;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

	protected static final String LAYER_0_TEXTURE = "layer0";
	protected static final String LAYER_1_TEXTURE = "layer1";

	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, BakingBreadMod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		basicItem(ModItems.DOUGH);
		basicItem(ModItems.SOURDOUGH_STARTER);
		basicItem(ModItems.WILD_YEAST_STARTER);
		basicItem(ModItems.OVAL_LOAF);
		basicItem(ModItems.TIN_LOAF);
		basicItem(ModItems.WAND_LOAF);
		basicItem(ModItems.ROLL);

		ModItems.findEntries(FlourItem.class).forEach(this::flourItem);
	}

	private <T extends Item> ResourceLocation id(T item) {
		return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item));
	}

	protected <T extends Item> ItemModelBuilder basicItem(RegistryObject<T> registryObject) {
		return basicItem(registryObject.getId());
	}

	protected <T extends Item> ItemModelBuilder basicItem(RegistryObject<T> registryObject, String subFolder) {
		return basicItem(registryObject.getId(), subFolder);
	}

	protected ItemModelBuilder basicItem(ResourceLocation item, String subFolder) {
		return getBuilder(item.toString())
			.parent(new ModelFile.UncheckedModelFile("item/generated"))
			.texture(LAYER_0_TEXTURE, new ResourceLocation(item.getNamespace(), ITEM_FOLDER + "/" + subFolder + "/" + item.getPath()));
	}

	protected <T extends Item> ItemModelBuilder overlayItem(T item) {
		return overlayItem(id(item));
	}

	protected <T extends Item> ItemModelBuilder overlayItem(RegistryObject<T> registryObject) {
		return overlayItem(registryObject.getId());
	}

	protected ItemModelBuilder overlayItem(ResourceLocation item) {
		return basicItem(item).texture(LAYER_1_TEXTURE, new ResourceLocation(item.getNamespace(), ITEM_FOLDER + "/" + item.getPath() + "_overlay"));
	}

	protected ItemModelBuilder flourItem(RegistryObject<? extends FlourItem> registryObject) {
		FlourItem item = registryObject.get();
		return getBuilder(item.toString())
			.parent(new ModelFile.UncheckedModelFile("item/generated"))
			.texture(LAYER_0_TEXTURE, new ResourceLocation(registryObject.getId().getNamespace(), ITEM_FOLDER + "/flour"));
	}
}
