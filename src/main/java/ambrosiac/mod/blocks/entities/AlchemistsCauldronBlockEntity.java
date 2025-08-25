package ambrosiac.mod.blocks.entities;

import ambrosiac.mod.items.ModItems;
import ambrosiac.mod.recipe.AlchemistsCauldronRecipe;
import ambrosiac.mod.recipe.AlchemistsCauldronRecipeInput;
import ambrosiac.mod.recipe.ModRecipes;
import ambrosiac.mod.screens.AlchemistsCauldronScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AlchemistsCauldronBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {
    private static final int[] INGREDIENT_SLOTS = new int[]{0, 1, 2};
    private static final int OUTPUT_SLOT = 3;

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    public AlchemistsCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALCHEMISTS_CAULDRON_BLOCK_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    public DefaultedList<ItemStack> getIngredients() {
        DefaultedList<ItemStack> ingredients = DefaultedList.ofSize(3);
        for (int i = 0; i < 3; i++) {
            ItemStack itemStack = inventory.get(i);
            ingredients.add(itemStack);
        }
        return ingredients;
    }


    @Override
    public Text getDisplayName() {
        return Text.translatable("Alchemist's               Cauldron");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new AlchemistsCauldronScreenHandler(syncId, playerInventory, this, this.pos);
    }

    @Override
    public void onBlockReplaced(BlockPos pos, BlockState oldState) {
        ItemScatterer.spawn(world, pos, this);
        super.onBlockReplaced(pos, oldState);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
        return this.pos;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, inventory, registries);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        Inventories.readNbt(nbt, this.inventory, registries);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }

    public boolean canInsertItemIntoOutput() {
        ItemStack output = new ItemStack(ModItems.ACTIVATED_SWISS_CHARD_LEAF);
        return (this.getStack(OUTPUT_SLOT).equals(output) || this.getStack(OUTPUT_SLOT).isEmpty()) && this.getStack(OUTPUT_SLOT).getCount() < 64;
    }

    public boolean canCraft(RegistryWrapper.WrapperLookup registries,
             RecipeEntry<AlchemistsCauldronRecipe> recipe,
             AlchemistsCauldronRecipeInput recipeInput,
             DefaultedList<ItemStack> inventory) {
        if (recipe != null) {
            ItemStack itemStack = recipe.value().craft(recipeInput, registries);
            if (itemStack.isEmpty()) {
                return false;
            }
        }
        return false;
    }

    private Optional<RecipeEntry<AlchemistsCauldronRecipe>> getRecipe() {
        if (world != null) {
            return world.getServer().getRecipeManager().getFirstMatch(ModRecipes.ALCHEMISTS_CAULDRON_RECIPE_TYPE, new AlchemistsCauldronRecipeInput(getIngredients()), world);
        } else return Optional.empty();
    }

    public void craftRecipe(RegistryWrapper.WrapperLookup registries, RecipeEntry<AlchemistsCauldronRecipe> recipe,
                            AlchemistsCauldronRecipeInput recipeInput, DefaultedList<ItemStack> inventory) {

        int outputcount = this.getStack(OUTPUT_SLOT).getCount() + 1;
        ItemStack output = new ItemStack(ModItems.ACTIVATED_SWISS_CHARD_LEAF, outputcount);
        if (canCraft(registries, recipe, recipeInput, inventory)) {
            this.removeStack(0, 1);
            this.setStack(3, output);


        }

    }

}
