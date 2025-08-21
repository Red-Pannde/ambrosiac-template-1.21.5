package ambrosiac.mod.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AlchemistsCauldronScreen extends HandledScreen<AlchemistsCauldronScreenHandler> {
    public AlchemistsCauldronScreen(AlchemistsCauldronScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/gui/container/dispenser.png");

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
    }
    @Override
    public void init() {
        super.init();
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
