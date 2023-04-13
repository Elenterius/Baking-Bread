package com.github.elenterius.bakingbread.init;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.item.BreadItem;
import com.github.elenterius.bakingbread.item.DoughItem;
import com.github.elenterius.bakingbread.item.FlourItem;
import com.github.elenterius.bakingbread.item.ITintColorHolder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BakingBreadMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetupHandler {

	public static final int LIGHT_COLOR = 0xFF_CECECE;
	public static final int BURNED_COLOR = 0xFF_505050;

	private ClientSetupHandler() {
	}

	@SubscribeEvent
	public static void onSetup(final FMLClientSetupEvent event) {

	}

	@SubscribeEvent
	public static void onRegisterItemColor(final RegisterColorHandlersEvent.Item event) {
		event.register((stack, tintIndex) -> tintIndex == 0 ? ITintColorHolder.getColor(stack) : 0xFF_FFFFFF, ModItems.findItems(FlourItem.class).toArray(FlourItem[]::new));
		event.register((stack, tintIndex) -> tintIndex == 0 ? ITintColorHolder.getColor(stack) : 0xFF_FFFFFF, ModItems.findItems(DoughItem.class).toArray(DoughItem[]::new));
		event.register((stack, tintIndex) -> tintIndex == 0 ? ITintColorHolder.getColor(stack) : 0xFF_FFFFFF, ModItems.findItems(BreadItem.class).toArray(BreadItem[]::new));
	}

}
