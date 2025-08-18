package ambrosiac.mod;

import ambrosiac.mod.blocks.Dahlia;
import ambrosiac.mod.blocks.ModBlocks;
import ambrosiac.mod.screens.AlchemistsCauldronScreenHandler;
import ambrosiac.mod.screens.ModScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class AmbrosiacClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DAHLIA, RenderLayer.getCutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SWISS_CHARD, RenderLayer.getCutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PEACE_LILY, RenderLayer.getCutoutMipped());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ALCHEMISTS_CAULDRON, RenderLayer.getTranslucent());

	}
}