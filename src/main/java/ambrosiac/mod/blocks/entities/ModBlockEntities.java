package ambrosiac.mod.blocks.entities;

import ambrosiac.mod.Ambrosiac;
import ambrosiac.mod.blocks.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<AlchemistsCauldronBlockEntity> ALCHEMISTS_CAULDRON_BLOCK_ENTITY =
            register("alchemy_crafting", AlchemistsCauldronBlockEntity::new, ModBlocks.ALCHEMISTS_CAULDRON);

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.of(Ambrosiac.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
    public static void initialize() {

    }

}
