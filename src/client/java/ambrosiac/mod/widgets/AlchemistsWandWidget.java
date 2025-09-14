package ambrosiac.mod.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class AlchemistsWandWidget extends ClickableWidget {
    public AlchemistsWandWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }


    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {

    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

}
