package ambrosiac.mod.widgets;

import ambrosiac.mod.Ambrosiac;
import ambrosiac.mod.blocks.entities.AlchemistsCauldronBlockEntity;
import com.microsoft.aad.msal4j.IClientAssertion;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

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
