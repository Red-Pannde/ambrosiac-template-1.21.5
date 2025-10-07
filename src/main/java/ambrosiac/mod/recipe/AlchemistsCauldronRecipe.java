package ambrosiac.mod.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class AlchemistsCauldronRecipe implements Recipe<AlchemistsCauldronRecipeInput> {

    private final DefaultedList<Ingredient> ingredients;
    private final ItemStack result;
    public AlchemistsCauldronRecipe(DefaultedList<Ingredient> ingredients, ItemStack result) {
        this.ingredients = ingredients;
        this.result = result;
    }



    @Override
    public boolean matches(AlchemistsCauldronRecipeInput input, World world) {
        if (world.isClient) {
            return false;
        }
        if (input.getStackCount() != this.ingredients.size()) {
            return false;
        } else {
            return input.getSize() == 1 && this.ingredients.size() == 1
                    ? this.ingredients.getFirst().test(input.getStackInSlot(0))
                    : input.getRecipeMatcher().match(this, null);
        }
    }

    @Override
    public ItemStack craft(AlchemistsCauldronRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return null;
    }

    @Override
    public RecipeSerializer<? extends Recipe<AlchemistsCauldronRecipeInput>> getSerializer() {
        return ModRecipes.ALCHEMISTS_CAULDRON_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<AlchemistsCauldronRecipeInput>> getType() {
        return ModRecipes.ALCHEMISTS_CAULDRON_RECIPE_TYPE;
    }


    public DefaultedList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ItemStack getResult() {
        return result;
    }

    public static class Serializer implements RecipeSerializer<AlchemistsCauldronRecipe> {
        private static final MapCodec<AlchemistsCauldronRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.DISALLOW_EMPTY_CODEC.listOf(1, 3).fieldOf("ingredients").forGetter(recipe -> recipe.ingredients),
                                ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result)

                        )
                        .apply(instance,  AlchemistsCauldronRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, AlchemistsCauldronRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC.collect(PacketCodecs.toList()),
                recipe -> recipe.ingredients,
                ItemStack.PACKET_CODEC,
                recipe -> recipe.result,
                AlchemistsCauldronRecipe::new

        );



        @Override
        public MapCodec<AlchemistsCauldronRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, AlchemistsCauldronRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
