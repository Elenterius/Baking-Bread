package com.github.elenterius.bakingbread.init;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.item.DoughItem;
import com.github.elenterius.bakingbread.item.FlourItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
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

	@SubscribeEvent
	public static void onRegisterItemColor(final RegisterColorHandlersEvent.Item event) {
		event.register((stack, tintIndex) -> tintIndex == 0 && stack.getItem() instanceof FlourItem flour ? flour.getTintColor(stack) : 0xFFFFFFFF,
			ModItems.findItems(FlourItem.class).toArray(FlourItem[]::new));
		event.register((stack, tintIndex) -> tintIndex == 0 && stack.getItem() instanceof DoughItem dough ? dough.getTintColor(stack) : 0xFFFFFFFF,
			ModItems.findItems(DoughItem.class).toArray(DoughItem[]::new));
	}

}
