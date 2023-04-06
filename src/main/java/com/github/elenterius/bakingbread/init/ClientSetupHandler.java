package com.github.elenterius.bakingbread.init;

import com.github.elenterius.bakingbread.BakingBreadMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BakingBreadMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetupHandler {

	private ClientSetupHandler() {
	}

	@SubscribeEvent
	public static void onSetup(final FMLClientSetupEvent event) {

	}

}
