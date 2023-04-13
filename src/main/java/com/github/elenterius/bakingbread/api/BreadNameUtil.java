package com.github.elenterius.bakingbread.api;

import static com.github.elenterius.bakingbread.api.GrainClassification.BREAD_CEREAL;
import static com.github.elenterius.bakingbread.api.GrainClassification.OTHER_CEREAL;
import static com.github.elenterius.bakingbread.api.GrainClassification.PSEUDO_CEREAL;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags.Items;


public final class BreadNameUtil {

	public static List<String> determineNameFor(List<CountHolder<IGrain>> grains) {
		return GrainCharacterizationRules.processGrainFractions(grains);
	}


	public record CountHolder<T>(T get, int count) {

	}

	class IngredientCharacterizationRules {

		/**
		 * 2.2.1 oil seeds, nuts or legumes
		 */
		public static final Rule SEEDS_RULE = (sortedIngredients, totalIngredients, affixes) -> {
			for (CountHolder<ItemStack> ingredient : sortedIngredients) {
				ItemStack stack = ingredient.get();

				// oil seeds (e.g. sesame, flaxseed, pumpkin seeds, sunflower seeds) or
				// nuts (e.g. walnuts, hazelnuts, cashews) or
				// legumes (e.g. lentils, soybeans, peanuts, lupins)

				if (stack.is(Items.SEEDS_PUMPKIN)) {
					float pct = (float) ingredient.count() / totalIngredients;
					if (pct >= 0.08f) {
						affixes.addFirst(stack.getDescriptionId());
						return true;
					}
				}
			}
			return false;
		};

		interface Rule {

			boolean apply(List<CountHolder<ItemStack>> sortedIngredients, int totalIngredients, LinkedList<String> affixes);
		}
	}

	/**
	 * Adapted from
	 * <a href="https://www.bmel.de/SharedDocs/Downloads/DE/_Ernaehrung/Lebensmittel-Kennzeichnung/LeitsaetzeBrot.html">
	 * Guiding Principles of the German food book for bread and small baked goods
	 * </a>
	 * </p>
	 */
	class GrainCharacterizationRules {

		/**
		 * Rule 2.1.3 mixed bread cereal (Mischbrot / Gray Bread)
		 */
		public static final Rule MIXED_BREAD_RULE = new Rule() {

			@Override
			public boolean apply(List<CountHolder<IGrain>> sortedGrains, int totalGrains, LinkedList<String> prefixes) {
				if (sortedGrains.size() < 2) { //at least two different grains
					return false;
				}

				if (wheatRyeRule(sortedGrains, totalGrains, prefixes)) {
					return true;
				}
				if (mixedBreadCerealRule(sortedGrains, totalGrains, prefixes)) {
					return true;
				}

				return false;
			}

			private boolean wheatRyeRule(List<CountHolder<IGrain>> sortedGrains, int totalGrains, LinkedList<String> prefixes) {
				IGrain grainA = sortedGrains.get(0).get;
				IGrain grainB = sortedGrains.get(1).get;
				if ((grainA == Grains.WHEAT && grainB == Grains.RYE) || (grainA == Grains.RYE && grainB == Grains.WHEAT)) {
					int countA = sortedGrains.get(0).count;
					int countB = sortedGrains.get(1).count;
					// 50% wheat & 50% rye
					if (countA == countB && countA == totalGrains / 2) {
						prefixes.addFirst("affix.bakingbread.mixed");
						return true;
					}
				}
				return false;
			}

			private boolean mixedBreadCerealRule(List<CountHolder<IGrain>> sortedGrains, int totalGrains, LinkedList<String> prefixes) {
				IGrain grainAboveFiftyPct = null;
				IGrain grainAboveTenPct = null;

				for (CountHolder<IGrain> sortedGrain : sortedGrains) {
					GrainClassification classification = sortedGrain.get.getClassification();
					float pct = (float) sortedGrain.count / totalGrains;

					if (classification == BREAD_CEREAL) {
						if (pct > 0.5f) {
							grainAboveFiftyPct = sortedGrain.get;
						} else if (pct > 0.1f) {
							grainAboveTenPct = sortedGrain.get;
						}
					}
				}

				if (grainAboveFiftyPct != null && grainAboveTenPct != null) {
					prefixes.addAll(0, List.of(grainAboveFiftyPct.getDescriptionId(), "affix.bakingbread.mixed"));
					return true;
				}

				return false;
			}

		};

		/**
		 * Rule 2.1.6 multi-grain bread
		 */
		public static final Rule MULTI_GRAIN_RULE = (sortedGrains, totalGrains, prefixes) -> {
			if (sortedGrains.size() < 3) { //at least three different grains
				return false;
			}

			boolean hasBreadCereal = false;
			boolean hasOtherOrPseudoCereal = false;
			int grainsAboveFivePct = 0;

			for (CountHolder<IGrain> sortedGrain : sortedGrains) {
				GrainClassification classification = sortedGrain.get.getClassification();
				float pct = (float) sortedGrain.count / totalGrains;

				if (pct >= 0.05f) {
					if (classification == BREAD_CEREAL) {
						hasBreadCereal = true;
					} else if (classification == OTHER_CEREAL || classification == PSEUDO_CEREAL) {
						hasOtherOrPseudoCereal = true;
					}
					grainsAboveFivePct++;
				}
			}

			if (hasBreadCereal && hasOtherOrPseudoCereal && grainsAboveFivePct >= 3) {
				prefixes.addFirst("affix.bakingbread.multi_grain");
				return true;
			}

			return false;
		};

		/**
		 * Rule 2.1.2 other cereal and pseudo cereal
		 */
		public static final Rule OTHER_AND_PSEUDO_CEREAL_RULE = (sortedGrains, totalGrains, prefixes) -> {
			for (CountHolder<IGrain> sortedGrain : sortedGrains) {
				IGrain grain = sortedGrain.get();
				GrainClassification classification = grain.getClassification();

				if (classification == OTHER_CEREAL || classification == PSEUDO_CEREAL) {
					float pct = (float) sortedGrain.count() / totalGrains;
					if (pct >= 0.2f) {
						prefixes.addFirst(grain.getDescriptionId());
						return true;
					}
				}
			}
			return false;
		};

		/**
		 * Rule 2.1.1 bread cereal
		 */
		public static final Rule BREAD_CEREAL_RULE = (sortedGrains, totalGrains, prefixes) -> {
			for (CountHolder<IGrain> sortedGrain : sortedGrains) {
				IGrain grain = sortedGrain.get();

				if (grain.getClassification() == BREAD_CEREAL) {
					float pct = (float) sortedGrain.count() / totalGrains;

					if (pct >= 1f && grain == Grains.WHEAT) {
						prefixes.addAll(0, List.of("affix.bakingbread.white", grain.getDescriptionId()));
						return true;
					}
					if (pct >= 0.9f) {
						prefixes.addFirst(grain.getDescriptionId());
						return true;
					}
					if (pct > 0.5f && grain == Grains.RYE) {
						prefixes.addFirst(grain.getDescriptionId());
						return true;
					}
				}
			}
			return false;
		};

		static LinkedList<String> processGrainFractions(List<CountHolder<IGrain>> grains) {
			grains.sort(Comparator.comparingInt(x -> x.count)); //ensure list is sorted
			int totalAmount = grains.stream().mapToInt(CountHolder::count).sum();
			LinkedList<String> prefixes = new LinkedList<>();

			if (BREAD_CEREAL_RULE.apply(grains, totalAmount, prefixes)) {
				return prefixes;
			}

			if (OTHER_AND_PSEUDO_CEREAL_RULE.apply(grains, totalAmount, prefixes)) {
				return prefixes;
			}

			if (MIXED_BREAD_RULE.apply(grains, totalAmount, prefixes)) {
				return prefixes;
			}

			//Rule 2.1.4 ignored
			//Rule 2.1.5 ignored

			if (MULTI_GRAIN_RULE.apply(grains, totalAmount, prefixes)) {
				return prefixes;
			}

			return prefixes;
		}

		interface Rule {

			boolean apply(List<CountHolder<IGrain>> sortedGrains, int totalGrains, LinkedList<String> prefixes);
		}
	}
}
