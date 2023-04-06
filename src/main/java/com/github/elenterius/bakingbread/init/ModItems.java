package com.github.elenterius.bakingbread.init;

import com.github.elenterius.bakingbread.BakingBreadMod;
import java.util.function.Function;
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

	public static final RegistryObject<BlockItem> EXAMPLE_BLOCK_ITEM = registerBlockItem(ModBlocks.EXAMPLE_BLOCK, BlockItem::new);

	private ModItems() {
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
