package cx.rain.mc.fabric.rainauth.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;

public interface PlayerLeaveCallback {
    Event<PlayerLeaveCallback> EVENT =
            EventFactory.createArrayBacked(PlayerLeaveCallback.class,
                    listeners -> (player) -> {
                        for (PlayerLeaveCallback c : listeners) {
                            c.accept(player);
                        }
                    });

    void accept(ServerPlayerEntity player);
}
