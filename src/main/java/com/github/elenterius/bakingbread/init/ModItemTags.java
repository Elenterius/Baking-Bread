package com.github.elenterius.bakingbread.init;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.api.GrainClassification;
import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ModItemTags {

	public static final TagKey<Item> BREAD_CEREAL = tag(GrainClassification.BREAD_CEREAL);
	public static final TagKey<Item> OTHER_CEREAL = tag(GrainClassification.OTHER_CEREAL);
	public static final TagKey<Item> PSEUDO_CEREAL = tag(GrainClassification.PSEUDO_CEREAL);

	private ModItemTags() {
	}

	private static TagKey<Item> tag(GrainClassification classification) {
		return TagKey.create(Registry.ITEM_REGISTRY, BakingBreadMod.createRL(classification.name().toLowerCase(Locale.ENGLISH)));
	}

}
