package com.github.elenterius.bakingbread.datagen.models;

import com.github.elenterius.bakingbread.BakingBreadMod;
import com.github.elenterius.bakingbread.block.GlassJarBlock;
import com.github.elenterius.bakingbread.block.GlassJarBlock.Contents;
import com.github.elenterius.bakingbread.init.ModBlocks;
import java.util.Objects;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(DataGenerator generator, ExistingFileHelper fileHelper) {
		super(generator, BakingBreadMod.MOD_ID, fileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		glassJarBlock(ModBlocks.GLASS_JAR.get());
	}

	public void glassJarBlock(GlassJarBlock block) {
		ModelFile.ExistingModelFile glassJarModel = models().getExistingFile(blockModel(block));
		ResourceLocation waterTexture = new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/water_still");

		MultiPartBlockStateBuilder builder = getMultipartBuilder(block).part().modelFile(glassJarModel).addModel().end();

		for (int i = 1; i <= 12; i++) {
			BlockModelBuilder sourdoughModel = jarContentsHeight("sourdough", i).renderType("cutout");
			builder.part().modelFile(sourdoughModel).addModel().condition(GlassJarBlock.CONTENTS, Contents.SOURDOUGH).condition(GlassJarBlock.LEVEL, i);

			BlockModelBuilder waterModel = jarContentsHeight("water_contents", i, waterTexture, waterTexture, waterTexture, waterTexture).renderType("translucent");
			builder.part().modelFile(waterModel).addModel().condition(GlassJarBlock.CONTENTS, Contents.WATER, Contents.WILD_YEAST).condition(GlassJarBlock.LEVEL, i);
		}

		simpleBlockItem(block, glassJarModel);
	}

	private ResourceLocation id(Block block) {
		return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block));
	}

	private ResourceLocation asset(String asset) {
		return modLoc(ModelProvider.BLOCK_FOLDER + "/" + asset);
	}

	public BlockModelBuilder jarContentsHeight(String name, int i) {
		ResourceLocation sideTexture = asset(name + "_side");
		return jarContentsHeight(name, i, sideTexture, sideTexture, asset(name + "_top"), asset(name + "_bottom"));
	}

	public BlockModelBuilder jarContentsHeight(String name, int i, ResourceLocation textureParticle, ResourceLocation textureSide, ResourceLocation textureUp, ResourceLocation textureDown) {
		ModelFile.ExistingModelFile existingModel = models().getExistingFile(asset("jar_contents_" + i));
		return models().getBuilder(name + "_" + i).parent(existingModel)
			.texture("side", textureSide)
			.texture("up", textureUp)
			.texture("down", textureDown)
			.texture("particle", textureParticle);
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
