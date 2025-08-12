package ambrosiac.mod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.function.BiFunction;

public class Peace_LilyBlock extends Block {
    public Peace_LilyBlock(Settings settings) {
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
surroundingCheck(world, pos, 3, 0.5f, 10, (newPos, state) -> {
    if (state.getBlock().equals(Blocks.DIRT)) {
        world.setBlockState(newPos, Blocks.MUD.getDefaultState());
                return true;
    }
    return false;
});
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        createMud(world, pos);
    }
}
