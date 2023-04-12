package com.github.elenterius.bakingbread.api;

import com.github.elenterius.bakingbread.BakingBreadMod;
import java.util.Objects;

public final class Grain implements IGrain {

	private final String nameId;
	private final GrainClassification classification;
	private final int color;


	public Grain(String name, GrainClassification classification, int color) {
		this.nameId = name;
		this.classification = classification;
		this.color = color;
	}

	@Override
	public GrainClassification getClassification() {
		return classification;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public String getDescriptionId() {
		return "grain." + BakingBreadMod.MOD_ID + "." + nameId;
	}

	public String getNameId() {
		return nameId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		var that = (Grain) obj;
		return Objects.equals(this.nameId, that.nameId) &&
			Objects.equals(this.classification, that.classification) &&
			this.color == that.color;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nameId, classification, color);
	}

	@Override
	public String toString() {
		return "Grain[" +
			"name=" + nameId + ", " +
			"classification=" + classification + ", " +
			"color=" + color + ']';
	}

}
