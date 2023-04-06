package com.github.elenterius.bakingbread.init;

import com.github.elenterius.bakingbread.BakingBreadMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BakingBreadMod.MOD_ID);

	public static final RegistryObject<Block> GLASS_JAR = BLOCKS.register("glass_jar", () -> new Block(BlockBehaviour.Properties.of(Material.GLASS)));

	private ModBlocks() {
	}

}
