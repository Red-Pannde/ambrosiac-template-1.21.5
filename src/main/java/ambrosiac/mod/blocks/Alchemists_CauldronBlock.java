package ambrosiac.mod.blocks;

import ambrosiac.mod.blocks.entities.Alchemists_CauldronBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class Alchemists_CauldronBlock extends BlockWithEntity {

    public Alchemists_CauldronBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(Alchemists_CauldronBlock::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new Alchemists_CauldronBlockEntity(pos, state);
    }

}
