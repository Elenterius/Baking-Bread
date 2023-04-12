package com.github.elenterius.bakingbread.api;

public interface IGrain {

	GrainClassification getClassification();

	/**
	 * @return ARGB tint color
	 */
	int getColor();

	/**
	 * @return un-localized name of the grain
	 */
	String getDescriptionId();
}
