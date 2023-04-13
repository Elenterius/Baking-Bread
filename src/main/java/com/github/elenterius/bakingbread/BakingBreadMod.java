package com.github.elenterius.bakingbread;

import com.github.elenterius.bakingbread.init.ModBlocks;
import com.github.elenterius.bakingbread.init.ModItems;
import com.github.elenterius.bakingbread.init.ModRecipeSerializers;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(BakingBreadMod.MOD_ID)
public final class BakingBreadMod {

	public static final String MOD_ID = "bakingbread";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(MOD_ID) {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModItems.GLASS_JAR.get());
		}
	};

	public BakingBreadMod() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModBlocks.BLOCKS.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
		ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
	}

	public static ResourceLocation createRL(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
