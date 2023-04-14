package com.github.elenterius.bakingbread.init;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.api.Grains;
import com.github.elenterius.bakingbread.item.BreadItem;
import com.github.elenterius.bakingbread.item.DoughItem;
import com.github.elenterius.bakingbread.item.FlourItem;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BakingBreadMod.MOD_ID);

	//bread cereals
	public static final RegistryObject<FlourItem> WHEAT_FLOUR = registerItem("wheat_flour", props -> new FlourItem(props, Grains.WHEAT));
	public static final RegistryObject<FlourItem> RYE_FLOUR = registerItem("rye_flour", props -> new FlourItem(props, Grains.RYE));
	public static final RegistryObject<FlourItem> SPELT_FLOUR = registerItem("spelt_flour", props -> new FlourItem(props, Grains.SPELT));
	public static final RegistryObject<FlourItem> EMMER_FLOUR = registerItem("emmer_flour", props -> new FlourItem(props, Grains.EMMER));
	public static final RegistryObject<FlourItem> EINKORN_FLOUR = registerItem("einkorn_flour", props -> new FlourItem(props, Grains.EINKORN));
	public static final RegistryObject<FlourItem> BARLEY_FLOUR = registerItem("barley_flour", props -> new FlourItem(props, Grains.BARLEY));
	public static final RegistryObject<FlourItem> OAT_FLOUR = registerItem("oat_flour", props -> new FlourItem(props, Grains.OAT));
	public static final RegistryObject<FlourItem> MILLET_FLOUR = registerItem("millet_flour", props -> new FlourItem(props, Grains.MILLET));
	public static final RegistryObject<FlourItem> MAIZE_FLOUR = registerItem("maize_flour", props -> new FlourItem(props, Grains.MAIZE));
	public static final RegistryObject<FlourItem> RICE_FLOUR = registerItem("rice_flour", props -> new FlourItem(props, Grains.RICE));
	public static final RegistryObject<FlourItem> AMARANTH_FLOUR = registerItem("amaranth_flour", props -> new FlourItem(props, Grains.AMARANTH));
	public static final RegistryObject<FlourItem> BUCKWHEAT_FLOUR = registerItem("buckwheat_flour", props -> new FlourItem(props, Grains.BUCKWHEAT));
	public static final RegistryObject<FlourItem> QUINOA_FLOUR = registerItem("quinoa_flour", props -> new FlourItem(props, Grains.QUINOA));

	public static final RegistryObject<Item> SOURDOUGH_STARTER = registerItem("sourdough_starter");
	public static final RegistryObject<Item> WILD_YEAST_STARTER = registerItem("wild_yeast_starter");

	public static final RegistryObject<DoughItem> DOUGH = registerItem("dough", DoughItem::new);
	public static final RegistryObject<DoughItem> OVAL_DOUGH_SHAPE = registerItem("oval_dough_shape", DoughItem::new);
	public static final RegistryObject<DoughItem> TIN_DOUGH_SHAPE = registerItem("tin_dough_shape", DoughItem::new);
	public static final RegistryObject<DoughItem> BATON_DOUGH_SHAPE = registerItem("baton_dough_shape", DoughItem::new);
	public static final RegistryObject<DoughItem> ROLL_DOUGH_SHAPE = registerItem("roll_dough_shape", DoughItem::new);

	public static final RegistryObject<BreadItem> OVAL_BREAD = registerItem("bread_oval", props -> new BreadItem(props.food(ModFoods.OVAL_BREAD)));
	public static final RegistryObject<BreadItem> BATON_BREAD = registerItem("bread_baton", props -> new BreadItem(props.food(ModFoods.BATON_BREAD)));
	public static final RegistryObject<BreadItem> BREAD_ROLL = registerItem("bread_roll", props -> new BreadItem(props.food(ModFoods.ROLL)));

	public static final RegistryObject<BlockItem> GLASS_JAR = registerBlockItem(ModBlocks.GLASS_JAR);

	private ModItems() {
	}

	public static <T extends Item> Stream<T> findItems(Class<T> clazz) {
		return ModItems.ITEMS.getEntries().stream()
			.map(RegistryObject::get)
			.filter(clazz::isInstance)
			.map(clazz::cast);
	}

	public static <T extends Item> Stream<RegistryObject<T>> findEntries(Class<T> clazz) {
		//noinspection unchecked
		return ModItems.ITEMS.getEntries().stream()
			.filter(registryObject -> clazz.isInstance(registryObject.get()))
			.map(registryObject -> (RegistryObject<T>) registryObject);
	}

	private static Item.Properties createProperties() {
		return new Item.Properties().tab(BakingBreadMod.CREATIVE_TAB);
	}

	private static <T extends Item> RegistryObject<T> registerItem(String name, Function<Properties, T> factory) {
		return ITEMS.register(name, () -> factory.apply(createProperties()));
	}

	private static RegistryObject<Item> registerItem(String name) {
		return ITEMS.register(name, () -> new Item(createProperties()));
	}

	private static RegistryObject<Item> registerItem(String name, Rarity rarity) {
		return ITEMS.register(name, () -> new Item(createProperties().rarity(rarity)));
	}

	private static <T extends Block> RegistryObject<BlockItem> registerBlockItem(RegistryObject<T> blockHolder) {
		return registerBlockItem(blockHolder, BlockItem::new);
	}

	private static <T extends Block, I extends BlockItem> RegistryObject<I> registerBlockItem(RegistryObject<T> blockHolder, Function<T, I> factory) {
		return ITEMS.register(blockHolder.getId().getPath(), () -> factory.apply(blockHolder.get()));
	}

	private static <T extends Block, I extends BlockItem> RegistryObject<I> registerBlockItem(RegistryObject<T> blockHolder, IBlockItemFactory<I> factory) {
		return ITEMS.register(blockHolder.getId().getPath(), () -> factory.create(blockHolder.get(), createProperties()));
	}

	private static <T extends Block, I extends BlockItem> RegistryObject<I> registerBlockItem(RegistryObject<T> blockHolder, IBlockItemFactory<I> factory, Rarity rarity) {
		return ITEMS.register(blockHolder.getId().getPath(), () -> factory.create(blockHolder.get(), createProperties().rarity(rarity)));
	}

	interface IBlockItemFactory<I extends BlockItem> {

		I create(Block block, Item.Properties properties);
	}
}
