package ambrosiac.mod.items;

import ambrosiac.mod.Ambrosiac;
import ambrosiac.mod.blocks.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {

    public static Item register(String id, Item item) {
        // Create the item key.
        Identifier itemID = Identifier.of(Ambrosiac.MOD_ID, id);

        // Create the item instance.


        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        return registeredItem;
    }

    public static final Item ATTRIBUTE_WAND = register("attribute_wand", new AttributeWand(new Item.Settings().maxCount(1)));

    public static final Item ACTIVATED_PEACE_LILY = register
            ("activated_peace_lily", new BlockItem(ModBlocks.ACTIVATED_PEACE_LILY, new Item.Settings().component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)));

    public static final Item ACTIVATED_DAHLIA = register
            ("activated_dahlia",new BlockItem(ModBlocks.ACTIVATED_DAHLIA, new Item.Settings().component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)));


    public static final Item ACTIVATED_SWISS_CHARD_SEEDS = register
            ("activated_swiss_chard_seeds", new BlockItem(ModBlocks.ACTIVATED_SWISS_CHARD, new Item.Settings().component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)));

    public static final Item SWISS_CHARD_SEEDS = register
            ("swiss_chard_seeds", new BlockItem(ModBlocks.SWISS_CHARD, new Item.Settings()));


    public static final Item ACTIVATED_SWISS_CHARD_LEAF = register
            ("activated_swiss_chard_leaf", new Item( new Item.Settings().component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)));
    public static final Item SWISS_CHARD_LEAF = register("swiss_chard_leaf", new Item(new Item.Settings()));

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, Identifier.of(Ambrosiac.MOD_ID, "item_group"), AMBROSIAC_ITEM_GROUP);
        // Get the event for modifying entries in the ingredients group.
// And register an event handler that adds our suspicious item to the ingredients group.
        // Add the suspicious substance to the composting registry with a 30% chance of increasing the composter's level.
        CompostingChanceRegistry.INSTANCE.add(ModBlocks.DAHLIA, 0.3f);
    }
    public static final ItemGroup AMBROSIAC_ITEM_GROUP =  FabricItemGroup.builder()
            .icon(() -> new ItemStack(SWISS_CHARD_LEAF))
            .displayName(Text.translatableWithFallback("itemGroup.ambrosiac", "Ambrosiac"))
            .entries((displayContext, entries) -> {
                entries.add(ATTRIBUTE_WAND);
                entries.add(ModBlocks.DAHLIA.asItem());
                entries.add(ACTIVATED_DAHLIA);
                entries.add(ModBlocks.PEACE_LILY.asItem());
                entries.add(ACTIVATED_PEACE_LILY);
                entries.add(SWISS_CHARD_LEAF);
                entries.add(ACTIVATED_SWISS_CHARD_LEAF);
                entries.add(SWISS_CHARD_SEEDS);
                entries.add(ACTIVATED_SWISS_CHARD_SEEDS);
            })
            .build();
}
