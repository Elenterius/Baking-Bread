package com.github.elenterius.bakingbread.block;

import com.github.elenterius.bakingbread.item.FlourItem;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class GlassJarBlock extends Block {

	public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 16, 15);
	public static final EnumProperty<Contents> CONTENTS = EnumProperty.create("contents", Contents.class);
	protected static final int MIN_LEVEL = 0;
	protected static final int MAX_LEVEL = 12;
	public static final IntegerProperty LEVEL = IntegerProperty.create("level", MIN_LEVEL, MAX_LEVEL);

	public GlassJarBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(CONTENTS, Contents.EMPTY).setValue(LEVEL, MIN_LEVEL));
	}

	private static void insert(BlockState state, Level level, BlockPos pos, Player player, ItemStack stack, Contents contents, int contentsLevel) {
		if (!level.isClientSide) {
			state = state.setValue(LEVEL, contentsLevel + 1).setValue(CONTENTS, contents);
			level.setBlockAndUpdate(pos, state);

			player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}
		}
	}

	public static int getTintColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos) {
		Contents contents = state.getValue(CONTENTS);
		if (contents == Contents.WATER || contents == Contents.WILD_YEAST) {
			return 0x43D5EE;
//			level.getBiome(pos).value().getWaterColor();
//			return ForgeRegistries.BIOMES.getHolder(Biomes.LUSH_CAVES).map(Holder::get).map(Biome::getWaterColor).orElse(0xFF_0000FF);
		}
		return 0xFF_FFFFFF;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(CONTENTS, LEVEL);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);
		int contentsLevel = state.getValue(LEVEL);
		Contents contents = state.getValue(CONTENTS);

		if (contentsLevel < MAX_LEVEL) {
			if (stack.is(Items.WATER_BUCKET) && (contents == Contents.EMPTY || contents == Contents.WATER)) {
				insert(state, level, pos, player, stack, Contents.WATER, contentsLevel);
				return InteractionResult.sidedSuccess(level.isClientSide);
			} else if (stack.getItem() instanceof FlourItem && (contents == Contents.EMPTY || contents == Contents.SOURDOUGH)) {
				insert(state, level, pos, player, stack, Contents.SOURDOUGH, contentsLevel);
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
		}

		return InteractionResult.PASS;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	public enum Contents implements StringRepresentable {
		EMPTY("empty"),
		WATER("water"),
		WILD_YEAST("wild_yeast"),
		SOURDOUGH("sourdough");

		private final String name;

		Contents(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return getSerializedName();
		}

		public String getSerializedName() {
			return name;
		}
	}
}
