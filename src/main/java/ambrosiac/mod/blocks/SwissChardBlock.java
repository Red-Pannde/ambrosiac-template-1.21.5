package ambrosiac.mod.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.function.Function;

public class SwissChardBlock extends CropBlock {
    public static final MapCodec<BeetrootsBlock> CODEC = createCodec(BeetrootsBlock::new);
    public static final IntProperty AGE;
    private static final VoxelShape[] SHAPES_BY_AGE;

    static {
        AGE = Properties.AGE_2;
        SHAPES_BY_AGE = Block.createShapeArray(2, (age) -> Block.createColumnShape((double) 16.0F, (double) 0.0F, (double) (2 + age * 2)));


    }

    public SwissChardBlock(Settings settings) {
        super(settings);
    }

    public MapCodec<BeetrootsBlock> getCodec() {
        return CODEC;
    }

    protected IntProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 2;
    }

    protected ItemConvertible getSeedsItem() {
        return ModBlocks.SWISS_CHARD;
    }

    protected int getGrowthAmount(World world) {
        return super.getGrowthAmount(world) / 3;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE});
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES_BY_AGE[this.getAge(state)];
    }

    public void fillCauldronWithSnow(World world, BlockPos pos) {
        surroundingCheck(world, pos, 4, 0.3f, 10, (newPos) ->{
            BlockState state = world.getBlockState(newPos);
            if (state.getBlock() instanceof AbstractCauldronBlock cauldronBlock && world.getBlockState(newPos.up()).isAir()) {

                cauldronBlock.precipitationTick(state, world, newPos, Biome.Precipitation.SNOW);
                return true;
            }
            return false;

        });

    }

    public void freezeWaterIntoPackedIce(World world, BlockPos pos) {
        surroundingCheck(world, pos, 2, 0.1f, 10, (newPos)-> {
            FluidState state = world.getFluidState(newPos);
            if (state.getFluid() instanceof WaterFluid.Still && world.getBlockState(newPos.up()).isAir()) {

                world.setBlockState(newPos, Blocks.PACKED_ICE.getDefaultState());
                return true;
            }
            return false;
        });

    }
    public void surroundingCheck(World world, BlockPos pos, int distance, float probability, int max_Operations, Function<BlockPos, Boolean> supplier) {
        int operations = 0;
        if (world.getRandom().nextFloat() <= probability) {
            while (operations <= max_Operations) {

                for (int i = -distance; i <= distance; i++) {
                    for (int j = -distance; j <= distance; j++) {
                        for (int k = -distance; k <= distance; k++) {
                            BlockPos newPos = pos.add(i, j, k);
                            if (supplier.apply(newPos)) {
                                operations++;
                            }
                        }
                    }
                }
                operations++;
            }
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (isMature(state)) {
            fillCauldronWithSnow(world, pos);
            freezeWaterIntoPackedIce(world, pos);
        }
    }

    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }
}

