package ambrosiac.mod.blocks.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class Alchemists_CauldronBlockEntity extends BlockEntity implements ImplementedInventory{
    public Alchemists_CauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALCHEMISTS_CAULDRON_BLOCK_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return null;
    }
}
