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

	private static ModelFile.UncheckedModelFile generatedItemModel() {
		return new ModelFile.UncheckedModelFile("item/generated");
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

	@Override
	protected void registerModels() {
		basicItem(ModItems.DOUGH);
		basicItem(ModItems.SOURDOUGH_STARTER);
		basicItem(ModItems.WILD_YEAST_STARTER);

		loafItem(ModItems.OVAL_DOUGH_SHAPE, "oval");
		loafItem(ModItems.TIN_DOUGH_SHAPE, "tin");
		loafItem(ModItems.BATON_DOUGH_SHAPE, "baton");
		loafItem(ModItems.ROLL_DOUGH_SHAPE, "roll");

		loafItem(ModItems.BREAD_OVAL, "oval");
		loafItem(ModItems.BREAD_BATON, "baton");
		loafItem(ModItems.BREAD_ROLL, "roll");

		ModItems.findEntries(FlourItem.class).forEach(this::flourItem);
	}

	protected ItemModelBuilder basicItem(ResourceLocation item, String subFolder) {
		return getBuilder(item.toString()).parent(generatedItemModel())
			.texture(LAYER_0_TEXTURE, new ResourceLocation(item.getNamespace(), ITEM_FOLDER + "/" + subFolder + "/" + item.getPath()));
	}

	protected <T extends Item> ItemModelBuilder loafItem(RegistryObject<T> registryObject, String shape) {
		return loafItem(registryObject.getId(), shape);
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

	protected ItemModelBuilder loafItem(ResourceLocation item, String shape) {
		return getBuilder(item.toString()).parent(generatedItemModel())
			.texture(LAYER_0_TEXTURE, new ResourceLocation(item.getNamespace(), ITEM_FOLDER + "/loaf/" + shape));
	}

	protected ItemModelBuilder flourItem(RegistryObject<? extends FlourItem> registryObject) {
		FlourItem item = registryObject.get();
		return getBuilder(item.toString()).parent(generatedItemModel())
			.texture(LAYER_0_TEXTURE, new ResourceLocation(registryObject.getId().getNamespace(), ITEM_FOLDER + "/flour"));
	}
}
