package com.github.elenterius.bakingbread.util;

import com.scrtwpns.Mixbox;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;

public final class ColorUtil {

	private ColorUtil() {
	}

	public static int textureDiffuseColor(DyeColor dyeColor) {
		return ARGB32.fromFloatRGB(dyeColor.getTextureDiffuseColors());
	}

	public static final class ARGB32 {

		private ARGB32() {
		}

		public static int alpha(int color) {
			return color >>> 24; // == color >> 24 & 0xFF
		}

		public static int red(int color) {
			return color >> 16 & 0xFF;
		}

		public static int green(int color) {
			return color >> 8 & 0xFF;
		}

		public static int blue(int color) {
			return color & 0xFF;
		}

		public static int color(int alpha, int red, int green, int blue) {
			return alpha << 24 | red << 16 | green << 8 | blue;
		}

		public static int fromFloatRGB(float[] rgb) {
			int r = (int) (rgb[0] * 255f);
			int g = (int) (rgb[1] * 255f);
			int b = (int) (rgb[2] * 255f);
			return color(0xFF, r, g, b);
		}

		/**
		 * Mixes colors based on pigments
		 */
		public static int mixSubtractive(int colorA, int colorB, float delta) {
			return Mixbox.lerp(colorA, colorB, delta);
		}

		/**
		 * Mixes colors based on pigments
		 */
		public static int mixSubtractive(int[] colors) {
			if (colors.length == 0) {
				return 0xFF_FFFFFF;
			}
			if (colors.length == 1) {
				return colors[0];
			}

			final float t = 1f / colors.length;
			final float[] latentMix = new float[Mixbox.LATENT_SIZE];
			float alphaMix = 0;

			for (int color : colors) {
				float[] latent = Mixbox.rgbToLatent(color);
				for (int i = 0; i < latent.length; i++) {
					latentMix[i] += t * latent[i];
				}

				alphaMix += t * alpha(color);
			}

			final int alpha = Mth.clamp(Math.round(alphaMix), 0x0, 0xFF);
			final int rgb = Mixbox.latentToRgb(latentMix) & 0x00_FFFFFF;

			return alpha << 24 | rgb;
		}

		public static int multiply(int colorA, int colorB) {
			return color(
				alpha(colorA) * alpha(colorB) / 255,
				red(colorA) * red(colorB) / 255,
				green(colorA) * green(colorB) / 255,
				blue(colorA) * blue(colorB) / 255
			);
		}
	}
}
