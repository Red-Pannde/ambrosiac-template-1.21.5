package ambrosiac.mod.blocks.activated;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidDrainable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiFunction;

public class ActivatedPeaceLilyBlock extends Block implements FluidDrainable{
    public ActivatedPeaceLilyBlock(Settings settings) {
        super(settings);
    }
    public void surroundingCheck(World world, BlockPos pos, int distance, float probability, int maxOperations, BiFunction<BlockPos, BlockState, Boolean> supplier) {
        int operations = 0;
        if (world.getRandom().nextFloat() <= probability) {
            while (operations <= maxOperations) {

                for (int i = -distance; i <= distance; i++) {
                    for (int j = -distance; j <= distance; j++) {
                        for (int k = -distance; k <= distance; k++) {

                            BlockPos newPos = pos.add(i, j, k);
                            BlockState state = world.getBlockState(newPos);
                            if (supplier.apply(newPos, state)) {
                                operations++;
                            }
                        }
                    }
                }
                operations++;
            }
        }
    }
    public void createMud(World world, BlockPos pos) {
        surroundingCheck(world, pos, 3, 1.0f, 10, (newPos, state) -> {
            if (state.isOf(Blocks.DIRT) && world.getBlockState(newPos.up()).isAir()) {
                world.setBlockState(newPos, Blocks.MUD.getDefaultState());
                return true;
            }
            return false;
        });
    }

    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        createMud(world, pos);

    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;

    }


    @Override
    public ItemStack tryDrainFluid(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos, BlockState state) {
        return new ItemStack(Items.WATER_BUCKET);

    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Fluids.WATER.getBucketFillSound();

    }

}
