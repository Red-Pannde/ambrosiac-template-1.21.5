package ambrosiac.mod.potion;

import ambrosiac.mod.Ambrosiac;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModPotions {
    public static final RegistryEntry<Potion> DOLHPINS_GRACE = registerPotion("dolphins_grace_potion", new Potion("dolphins_grace_potion", new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 3600)));
    private static RegistryEntry<Potion> registerPotion(String name, Potion potion) {
        return Registry.registerReference(Registries.POTION, Identifier.of(Ambrosiac.MOD_ID), potion);
    }
    public static void registerPotions() {
        Ambrosiac.LOGGER.info("Registering mod potions for" + Ambrosiac.MOD_ID);
    }

}
