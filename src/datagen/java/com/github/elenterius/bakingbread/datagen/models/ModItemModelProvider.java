package com.github.elenterius.bakingbread.datagen.models;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.init.ModItems;
import com.github.elenterius.bakingbread.item.FlourItem;
import java.util.Locale;
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

	enum LoafShape {
		OVAL, TIN, BATON, ROLL;

		@Override
		public String toString() {
			return name().toLowerCase(Locale.ENGLISH);
		}
	}

	protected static final String LAYER_0_TEXTURE = "layer0";
	protected static final String LAYER_1_TEXTURE = "layer1";

	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, BakingBreadMod.MOD_ID, existingFileHelper);
	}

	private static ModelFile.UncheckedModelFile generatedItemModel() {
		return new ModelFile.UncheckedModelFile("item/generated");
	}

	@Override
	protected void registerModels() {
		basicItem(ModItems.DOUGH);
		basicItem(ModItems.SOURDOUGH_STARTER);
		basicItem(ModItems.WILD_YEAST_STARTER);

		rawLoafItem(ModItems.OVAL_DOUGH_SHAPE, LoafShape.OVAL);
		rawLoafItem(ModItems.TIN_DOUGH_SHAPE, LoafShape.TIN);
		rawLoafItem(ModItems.BATON_DOUGH_SHAPE, LoafShape.BATON);
		rawLoafItem(ModItems.ROLL_DOUGH_SHAPE, LoafShape.ROLL);

		bakedLoafItem(ModItems.OVAL_BREAD, LoafShape.OVAL);
		bakedLoafItem(ModItems.BATON_BREAD, LoafShape.BATON);
		bakedLoafItem(ModItems.BREAD_ROLL, LoafShape.ROLL);

		ModItems.findEntries(FlourItem.class).forEach(this::flourItem);
	}

	protected <T extends Item> ItemModelBuilder bakedLoafItem(RegistryObject<T> registryObject, LoafShape shape) {
		return rawLoafItem(registryObject.getId(), shape);
	}

	protected ItemModelBuilder bakedLoafItem(ResourceLocation item, LoafShape shape) {
		return customTexturePath(item, "baked_loaf/" + shape);
	}

	protected <T extends Item> ItemModelBuilder rawLoafItem(RegistryObject<T> registryObject, LoafShape shape) {
		return rawLoafItem(registryObject.getId(), shape);
	}

	protected ItemModelBuilder rawLoafItem(ResourceLocation item, LoafShape shape) {
		return customTexturePath(item, "raw_loaf/" + shape);
	}

	protected ItemModelBuilder flourItem(RegistryObject<? extends FlourItem> registryObject) {
		FlourItem item = registryObject.get();
		return getBuilder(item.toString()).parent(generatedItemModel())
			.texture(LAYER_0_TEXTURE, new ResourceLocation(registryObject.getId().getNamespace(), ITEM_FOLDER + "/flour"));
	}

	protected <T extends Item> ItemModelBuilder basicItem(RegistryObject<T> registryObject) {
		return basicItem(registryObject.getId());
	}

	protected <T extends Item> ItemModelBuilder basicItem(RegistryObject<T> registryObject, String subFolder) {
		return basicItem(registryObject.getId(), subFolder);
	}

	protected ItemModelBuilder basicItem(ResourceLocation item, String subFolder) {
		return getBuilder(item.toString()).parent(generatedItemModel())
			.texture(LAYER_0_TEXTURE, new ResourceLocation(item.getNamespace(), ITEM_FOLDER + "/" + subFolder + "/" + item.getPath()));
	}

	protected ItemModelBuilder customTexturePath(ResourceLocation item, String path) {
		return getBuilder(item.toString()).parent(generatedItemModel())
			.texture(LAYER_0_TEXTURE, new ResourceLocation(item.getNamespace(), ITEM_FOLDER + "/" + path));
	}

	private <T extends Item> ResourceLocation id(T item) {
		return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item));
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

}
