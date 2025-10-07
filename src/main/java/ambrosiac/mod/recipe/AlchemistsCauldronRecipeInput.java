package ambrosiac.mod.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class AlchemistsCauldronRecipeInput implements RecipeInput {
    private final DefaultedList<ItemStack> stacks;
    private final RecipeMatcher matcher = new RecipeMatcher();
    private final int stackCount;

    public AlchemistsCauldronRecipeInput(DefaultedList<ItemStack> stacks) {
        this.stacks = stacks;
        int i = 0;
        for (ItemStack itemStack : stacks) {
            if (!itemStack.isEmpty()) {
                i++;
                this.matcher.addInput(itemStack, 1);
            }
        }
        this.stackCount = i;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.stacks.get(slot);
    }

    public List<ItemStack> getStacks() {
        return this.stacks;
    }
    public int getStackCount() {
        return this.stackCount;
    }

    public RecipeMatcher getRecipeMatcher() {
        return this.matcher;
    }


    @Override
    public int getSize() {
        return this.stacks.size();
    }
}
