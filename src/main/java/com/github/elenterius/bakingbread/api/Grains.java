package com.github.elenterius.bakingbread.api;

import static com.github.elenterius.bakingbread.api.GrainClassification.BREAD_CEREAL;
import static com.github.elenterius.bakingbread.api.GrainClassification.OTHER_CEREAL;
import static com.github.elenterius.bakingbread.api.GrainClassification.PSEUDO_CEREAL;

public final class Grains {

	public static final Grain WHEAT = new Grain("wheat", BREAD_CEREAL, 0xff_f5deb3); //Weizen
	public static final Grain RYE = new Grain("rye", BREAD_CEREAL, 0xff_f0e6d3); //Roggen
	public static final Grain SPELT = new Grain("spelt", BREAD_CEREAL, 0xff_e2b98d); //Dinkel
	public static final Grain EMMER = new Grain("emmer", BREAD_CEREAL, 0xff_e9e6c7); //Emmer
	public static final Grain EINKORN = new Grain("einkorn", BREAD_CEREAL, 0xff_cdb592); //Einkorn

	public static final Grain BARLEY = new Grain("barley", OTHER_CEREAL, 0xff_dfcea3); //Gerste (Hordeum)
	public static final Grain OAT = new Grain("oat", OTHER_CEREAL, 0xff_f6e3cd); //Hafer (Avena)
	public static final Grain MILLET = new Grain("millet", OTHER_CEREAL, 0xff_b28b77); //Hirse (sorghum bicolor, proso millet)
	public static final Grain MAIZE = new Grain("maize", OTHER_CEREAL, 0xff_f9d877); //Mais, also called corn flour
	public static final Grain RICE = new Grain("rice", OTHER_CEREAL, 0xff_d7d1cc);

	public static final Grain AMARANTH = new Grain("amaranth", PSEUDO_CEREAL, 0xff_cd98b2);
	public static final Grain BUCKWHEAT = new Grain("buckwheat", PSEUDO_CEREAL, 0xff_efe2d1); //Buchweizen (Fagopyrum)
	public static final Grain QUINOA = new Grain("quinoa", PSEUDO_CEREAL, 0xff_cfb597); //Quinoa (Chenopodium quinoa)

}
