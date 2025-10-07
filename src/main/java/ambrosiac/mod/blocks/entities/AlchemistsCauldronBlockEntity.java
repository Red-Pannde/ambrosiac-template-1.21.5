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
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AlchemistsCauldronBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {
    private static final int[] INGREDIENT_SLOTS = new int[]{0, 1, 2};
    private static final int OUTPUT_SLOT = 3;
    private World world;

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    public AlchemistsCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALCHEMISTS_CAULDRON_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public @Nullable World getWorld() {
        return world;
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
        return new AlchemistsCauldronScreenHandler(syncId, playerInventory, this.pos, this);
    }

    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        return isValid(slot, stack);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return slot != 3;
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

    public boolean canCraft(RegistryWrapper.WrapperLookup registries,
                            RecipeEntry<AlchemistsCauldronRecipe> recipe,
                            AlchemistsCauldronRecipeInput recipeInput,
                            DefaultedList<ItemStack> inventory) {
        Item result = (recipe.value().craft(recipeInput, registries)).getItem();
        boolean recipeMatches = recipe.value().matches(recipeInput, world);
        boolean isOutputEmpty = this.getStack(OUTPUT_SLOT).isEmpty();
        boolean isOutputCorrectAndNotFull = (this.getStack(OUTPUT_SLOT).getItem().equals(result) && this.getStack(OUTPUT_SLOT).getCount() < getMaxCount(result.getDefaultStack()));

        boolean canInsertItemIntoOutput = isOutputEmpty || isOutputCorrectAndNotFull;
        if (recipe != null && canInsertItemIntoOutput && recipeMatches) {
            return true;
        } else return false;
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<AlchemistsCauldronRecipe>> recipeEntry = getRecipe();
        return recipeEntry.isPresent();
    }

    private Optional<RecipeEntry<AlchemistsCauldronRecipe>> getRecipe() {
        if (world != null) {
            return world.getServer().getRecipeManager().getFirstMatch(ModRecipes.ALCHEMISTS_CAULDRON_RECIPE_TYPE,
                    new AlchemistsCauldronRecipeInput(getIngredients()), world);
        } else return Optional.empty();
    }

    public void craftRecipe(DynamicRegistryManager dynamicRegistryManager, RecipeEntry<AlchemistsCauldronRecipe> recipe, AlchemistsCauldronRecipeInput recipeInput) {
        for (int i = 0; i < OUTPUT_SLOT; i++) {
            AlchemistsCauldronBlockEntity.this.removeStack(i, 1);
        }
        Item output = recipe.value().craft(recipeInput, dynamicRegistryManager).getItem();
        ItemStack result = new ItemStack(output, this.getStack(OUTPUT_SLOT).getCount() + 1);
        this.setStack(OUTPUT_SLOT, result);

    }

    public void tryCraft() {
        if (hasRecipe() && canCraft(world.getRegistryManager(), getRecipe().get(), new AlchemistsCauldronRecipeInput(getIngredients()), inventory)) {
            craftRecipe(world.getRegistryManager(), getRecipe().get(), new AlchemistsCauldronRecipeInput(getIngredients()));
            toUpdatePacket();

        }
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

}
