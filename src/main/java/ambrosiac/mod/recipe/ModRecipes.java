package ambrosiac.mod.recipe;

import ambrosiac.mod.Ambrosiac;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static final RecipeSerializer<AlchemistsCauldronRecipe> ALCHEMISTS_CAULDRON_RECIPE_SERIALIZER =
            Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(Ambrosiac.MOD_ID, "alchemists_cauldron_infusing"), new AlchemistsCauldronRecipe.Serializer() {
            });
    public static final RecipeType<AlchemistsCauldronRecipe> ALCHEMISTS_CAULDRON_RECIPE_TYPE = Registry.register(Registries.RECIPE_TYPE, Identifier.of(Ambrosiac.MOD_ID, "kettle_brewing"), new RecipeType<AlchemistsCauldronRecipe>() {
        @Override
        public String toString() {
            return "alchemists_cauldron_infusing";
        }
    });

    public static void registerRecipes() {
    }
}

