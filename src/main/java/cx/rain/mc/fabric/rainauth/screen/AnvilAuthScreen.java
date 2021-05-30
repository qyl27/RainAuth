package cx.rain.mc.fabric.rainauth.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class AnvilAuthScreen extends AnvilScreenHandler {
    public AnvilAuthScreen(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(syncId, inventory, context);
    }
}
