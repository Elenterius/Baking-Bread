package com.github.elenterius.bakingbread.datagen.models;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.init.ModBlocks;
import java.util.Objects;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
		super(generator, BakingBreadMod.MOD_ID, fileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		existingBlockWithItem(ModBlocks.GLASS_JAR);
	}

	private ResourceLocation id(Block block) {
		return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block));
	}

	public ResourceLocation blockModel(Block block) {
		ResourceLocation id = id(block);
		return new ResourceLocation(id.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + id.getPath());
	}

	public ResourceLocation blockModel(RegistryObject<Block> registryObject) {
		ResourceLocation id = registryObject.getId();
		return new ResourceLocation(id.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + id.getPath());
	}

	public ResourceLocation extend(ResourceLocation id, String suffix) {
		return new ResourceLocation(id.getNamespace(), id.getPath() + suffix);
	}

	public void simpleBlockItem(Block block) {
		String name = id(block).getPath();
		itemModels().getBuilder(name).parent(models().getBuilder(name));
	}

	public void simpleBlockItem(RegistryObject<Block> registryObject) {
		String name = registryObject.getId().getPath();
		itemModels().getBuilder(name).parent(models().getBuilder(name));
	}

	public void simpleBlockWithItem(RegistryObject<Block> registryObject) {
		Block block = registryObject.get();
		ModelFile model = cubeAll(block);
		simpleBlock(block, model);
		simpleBlockItem(block, model);
	}

	public void existingBlock(Block block) {
		existingBlock(block, blockModel(block));
	}

	public void existingBlock(Block block, ResourceLocation existingModel) {
		ModelFile.ExistingModelFile modelFile = models().getExistingFile(existingModel);
		simpleBlock(block, modelFile);
	}

	public void existingBlockWithItem(Block block) {
		ModelFile.ExistingModelFile existingModel = models().getExistingFile(blockModel(block));
		simpleBlock(block, existingModel);
		simpleBlockItem(block, existingModel);
	}

	public void existingBlockWithItem(RegistryObject<Block> registryObject) {
		Block block = registryObject.get();
		ModelFile.ExistingModelFile existingModel = models().getExistingFile(blockModel(block));
		simpleBlock(block, existingModel);
		simpleBlockItem(block, existingModel);
	}

}
