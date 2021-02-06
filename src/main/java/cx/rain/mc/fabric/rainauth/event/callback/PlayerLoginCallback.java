package cx.rain.mc.fabric.rainauth.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public interface PlayerLoginCallback {
    Event<PlayerLoginCallback> EVENT =
            EventFactory.createArrayBacked(PlayerLoginCallback.class,
                    listeners -> (connection, player) -> {
                        for (PlayerLoginCallback c : listeners) {
                            if (c.accept(connection, player) == ActionResult.FAIL) {
                                return ActionResult.FAIL;
                            }
                        }
                        return ActionResult.PASS;
                    });

    ActionResult accept(ClientConnection connection, ServerPlayerEntity player);
}
