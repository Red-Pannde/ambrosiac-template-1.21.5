package ambrosiac.mod.blocks;

import ambrosiac.mod.Ambrosiac;
import ambrosiac.mod.blocks.activated.ActivatedDahliaBlock;
import ambrosiac.mod.blocks.activated.ActivatedPeaceLilyBlock;
import ambrosiac.mod.blocks.activated.ActivatedSwissChardBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.component.ComponentMap;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    private ComponentMap.Builder components;

    private static Block register( String name, Block block, boolean shouldRegisterItem) {
        // Create a registry key for the block
        RegistryKey<Block> blockKey = keyOfBlock(name);
        // Create the block instance

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            RegistryKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Ambrosiac.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Ambrosiac.MOD_ID, name));
    }

    public static final Block DAHLIA = register(
            "dahlia",
            new DahliaBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AZALEA).noCollision().dynamicBounds()),
            true
    );
    public static final Block ALCHEMISTS_CAULDRON = register(
            "alchemists_cauldron",
            new AlchemistsCauldronBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.METAL).dynamicBounds()),
            true
    );
    public static final Block SWISS_CHARD = register(
            "swiss_chard",
            new SwissChardBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AZALEA).dynamicBounds().noCollision().nonOpaque()),
            false
    );
    public static final Block PEACE_LILY = register(
            "peace_lily",
            new PeaceLilyBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AZALEA).dynamicBounds().noCollision().nonOpaque().ticksRandomly()),
            true
    );
    public static final Block ACTIVATED_PEACE_LILY = register(
            "activated_peace_lily",
            new ActivatedPeaceLilyBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AZALEA).dynamicBounds().noCollision().nonOpaque().ticksRandomly()),
            false
    );
    public static final Block ACTIVATED_DAHLIA = register(
            "activated_dahlia",
            new ActivatedDahliaBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AZALEA).dynamicBounds().noCollision().nonOpaque().ticksRandomly()),
            false
    );
    public static final Block ACTIVATED_SWISS_CHARD = register(
            "activated_swiss_chard",
            new ActivatedSwissChardBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AZALEA).dynamicBounds().noCollision().nonOpaque().ticksRandomly()),
            false
    );


    public static void initialize() {


        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register((itemGroup) -> {
            itemGroup.add(ModBlocks.DAHLIA.asItem());
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register((itemGroup) -> {
            itemGroup.add(ModBlocks.ALCHEMISTS_CAULDRON.asItem());
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register((itemGroup) -> {
            itemGroup.add(ModBlocks.SWISS_CHARD.asItem());
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register((itemGroup) -> {
            itemGroup.add(ModBlocks.PEACE_LILY.asItem());
        });

    }
}
