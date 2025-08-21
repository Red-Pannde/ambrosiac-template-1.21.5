package ambrosiac.mod.screens;

import ambrosiac.mod.Ambrosiac;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandler implements ModInitializer {
    public static final ScreenHandlerType<AlchemistsCauldronScreenHandler> ALCHEMISTS_CAULDRON_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(Ambrosiac.MOD_ID, "alchemists_cauldron_screen_handler"), new ExtendedScreenHandlerType<>(AlchemistsCauldronScreenHandler::new, BlockPos.PACKET_CODEC));


    @Override
    public void onInitialize() {

    }
    public static void initialize() {

    }
}
