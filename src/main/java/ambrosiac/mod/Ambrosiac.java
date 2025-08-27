package ambrosiac.mod;


import ambrosiac.mod.blocks.ModBlocks;
import ambrosiac.mod.blocks.entities.ModBlockEntities;
import ambrosiac.mod.items.ModItems;
import ambrosiac.mod.potion.ModPotions;
import ambrosiac.mod.recipe.ModRecipes;
import ambrosiac.mod.screens.ModScreenHandler;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ambrosiac implements ModInitializer {
	public static final String MOD_ID = "ambrosiac";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModItems.initialize();
		ModBlocks.initialize();
		ModPotions.registerPotions();
		ModRecipes.registerRecipes();
		ModScreenHandler.initialize();
		ModBlockEntities.initialize();
		LOGGER.info("Hello Fabric world!");

    }

}