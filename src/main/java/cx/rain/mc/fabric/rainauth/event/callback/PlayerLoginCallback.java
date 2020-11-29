package cx.rain.mc.fabric.rainauth.event.callback;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.ClientConnection;
import net.minecraft.util.ActionResult;

public interface PlayerLoginCallback {
    Event<PlayerLoginCallback> EVENT =
            EventFactory.createArrayBacked(PlayerLoginCallback.class,
                    listeners -> (profile, connection) -> {
                        for (PlayerLoginCallback c : listeners) {
                            if (c.accept(profile, connection) == ActionResult.FAIL) {
                                return ActionResult.FAIL;
                            }
                        }
                        return ActionResult.PASS;
                    });

    ActionResult accept(GameProfile profile, ClientConnection connection);
}
