package ambrosiac.mod.items;

import ambrosiac.mod.Ambrosiac;
import ambrosiac.mod.blocks.ModBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;


import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

    public class ModItems {
        public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
            // Create the item key.
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Ambrosiac.MOD_ID, name));

            // Create the item instance.
            Item item = itemFactory.apply(settings.registryKey(itemKey));

            // Register the item.
            Registry.register(Registries.ITEM, itemKey, item);

            return item;
        }
        public static final Item ATTRIBUTE_WAND = register("attribute_wand", Attribute_Wand::new, new Attribute_Wand.Settings().maxCount(1));


        public static void initialize() {
            // Get the event for modifying entries in the ingredients group.
// And register an event handler that adds our suspicious item to the ingredients group.
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                    .register((itemGroup) -> itemGroup.add(ModItems.ATTRIBUTE_WAND));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL)
                    .register((itemGroup) -> itemGroup.add(ModBlocks.DAHLIA));


            // Add the suspicious substance to the composting registry with a 30% chance of increasing the composter's level.
            CompostingChanceRegistry.INSTANCE.add(ModBlocks.DAHLIA, 0.3f);
        }
    }
