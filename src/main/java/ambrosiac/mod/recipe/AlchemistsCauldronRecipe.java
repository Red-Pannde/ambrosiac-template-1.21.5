package ambrosiac.mod.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
@SuppressWarnings("deprecation")
public class AlchemistsCauldronRecipe implements Recipe<AlchemistsCauldronRecipeInput> {

    private final List<Ingredient> ingredients;
    private final ItemStack result;
    @Nullable
    private IngredientPlacement ingredientPlacement;
    public AlchemistsCauldronRecipe(List<Ingredient> ingredients, ItemStack result) {
        this.ingredients = ingredients;
        this.result = result;
    }



    @Override
    public boolean matches(AlchemistsCauldronRecipeInput input, World world) {
        if (input.getStackCount() != this.ingredients.size()) {
            return false;
        } else {
            return input.size() == 1 && this.ingredients.size() == 1
                    ? this.ingredients.getFirst().test(input.getStackInSlot(0))
                    : input.getRecipeMatcher().isCraftable(this, null);
        }
    }

    @Override
    public ItemStack craft(AlchemistsCauldronRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        return this.result.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<AlchemistsCauldronRecipeInput>> getSerializer() {
        return ModRecipes.ALCHEMISTS_CAULDRON_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<AlchemistsCauldronRecipeInput>> getType() {
        return ModRecipes.ALCHEMISTS_CAULDRON_RECIPE_TYPE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = IngredientPlacement.forShapeless(this.ingredients);
        }
        return this.ingredientPlacement;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return null;
    }
    public static class Serializer implements RecipeSerializer<AlchemistsCauldronRecipe> {
        private static final MapCodec<AlchemistsCauldronRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.CODEC.listOf(1, 3).fieldOf("ingredients").forGetter(recipe -> recipe.ingredients),
                                ItemStack.CODEC.fieldOf("result").forGetter(recipe -> recipe.result)

                        )
                        .apply(instance, AlchemistsCauldronRecipe::new)
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
