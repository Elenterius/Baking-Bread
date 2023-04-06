package com.github.elenterius.bakingbread.init;

import com.github.elenterius.bakingbread.BakingBreadMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = BakingBreadMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CommonSetupHandler {

	private CommonSetupHandler() {
	}

	@SubscribeEvent
	public static void onSetup(final FMLCommonSetupEvent event) {

	}

}
