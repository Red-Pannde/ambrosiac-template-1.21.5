package ambrosiac.mod.screens;

import ambrosiac.mod.Ambrosiac;
import ambrosiac.mod.blocks.entities.AlchemistsCauldronBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class AlchemistsCauldronScreenHandler extends ScreenHandler {
    private final Inventory inventory;


    public AlchemistsCauldronScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(4));
    }


        @Override
        public ItemStack quickMove (PlayerEntity player,int slot){
            return null;
        }

        @Override
        public boolean canUse (PlayerEntity player){
            return false;
        }


    }

