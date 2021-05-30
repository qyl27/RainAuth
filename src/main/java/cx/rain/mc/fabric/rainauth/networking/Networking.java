package cx.rain.mc.fabric.rainauth.networking;

import cx.rain.mc.fabric.rainauth.screen.AnvilAuthScreen;
import cx.rain.mc.fabric.rainauth.utility.TranslatableLanguage;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class Networking {
    public static void openAuthGui(ServerPlayerEntity player) {
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inv, p) ->
                new AnvilAuthScreen(syncId, inv, ScreenHandlerContext.EMPTY),
                new LiteralText(TranslatableLanguage.get().get("title.rainauth.auth"))));
    }
}
