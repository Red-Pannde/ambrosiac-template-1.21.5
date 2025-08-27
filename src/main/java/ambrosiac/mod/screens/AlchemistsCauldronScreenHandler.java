package ambrosiac.mod.screens;

import ambrosiac.mod.blocks.entities.AlchemistsCauldronBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.CrafterOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AlchemistsCauldronScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final BlockPos blockPos;
    private final BlockEntity alchemistsCauldronBlockEntity;



    public AlchemistsCauldronScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, pos, playerInventory.player.getWorld().getBlockEntity(pos));
    }

    public AlchemistsCauldronScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos, BlockEntity alchemistsCauldronBlockEntity) {
        super(ModScreenHandler.ALCHEMISTS_CAULDRON_SCREEN_HANDLER, syncId);
        this.inventory = (Inventory) alchemistsCauldronBlockEntity;
        this.blockPos = pos;
        this.alchemistsCauldronBlockEntity = alchemistsCauldronBlockEntity;
        // some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player);

        // This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
        // This will not render the background of the slots however, this is the Screens job
        int m;
        int l;
        // Our inventory
       /* for (m = 0; m < 2; ++m) {
            for (l = 0; l < 2; ++l) {
                this.addSlot(new Slot(inventory, l + m * 2, 62 + l * 18, 17 + m * 18));
            }
        }
        */
        this.addSlot(new Slot(inventory, 0, 75, 8));
        this.addSlot(new Slot(inventory, 1, 109, 39));
        this.addSlot(new Slot(inventory, 2, 64, 61));

        this.addSlot(new Slot(inventory, 3, 80, 35) {
            public boolean canInsert (ItemStack stack){
                return false;
            }
        });
        // The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        // The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }


    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack originalStack = slot2.getStack();
            newStack = originalStack.copy();
            if (slot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public boolean onButtonClick(PlayerEntity player, int id) {
        int INFUSING_BUTTON_ID = 0;
        if (id == INFUSING_BUTTON_ID) {
            World world1 = player.getWorld();
            AlchemistsCauldronBlockEntity blockEntity = (AlchemistsCauldronBlockEntity) world1.getBlockEntity(blockPos);
            blockEntity.tryCraft();
            return true;
        }
        return false;
    }

    @Override
    protected void dropInventory(PlayerEntity player, Inventory inventory) {
    }
}
