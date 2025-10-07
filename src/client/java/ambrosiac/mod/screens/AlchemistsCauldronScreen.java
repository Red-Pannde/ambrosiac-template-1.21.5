package ambrosiac.mod.screens;

import ambrosiac.mod.Ambrosiac;
import ambrosiac.mod.widgets.AlchemistsWandWidget;
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

    private static final Identifier TEXTURE = Identifier.of(Ambrosiac.MOD_ID, "screens/alchemists_cauldron_gui.png");
    private static final Identifier INFUSING_BUTTON = Identifier.of(Ambrosiac.MOD_ID, "widgets/alchemists_infusing_button.png");

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        context.drawTexture( TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 176, 166);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(INFUSING_BUTTON, x + 149, y + 58, 0, 0, 22, 20, 22, 20);

    }

    @Override
    public void init() {
        super.init();
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        AlchemistsWandWidget alchemistsWandWidget = new AlchemistsWandWidget(i + 151, j + 60, 18, 18, Text.translatable("bwaa :3"));
        addDrawableChild(alchemistsWandWidget);
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        double buttonXpos = i + 151;
        double buttonYpos = j + 58;
        if (mouseX >= buttonXpos && mouseX <= buttonXpos + 18 && mouseY >= buttonYpos && mouseY <= buttonYpos + 18) {
            this.client.interactionManager.clickButton((this.handler).syncId, 0);

        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
