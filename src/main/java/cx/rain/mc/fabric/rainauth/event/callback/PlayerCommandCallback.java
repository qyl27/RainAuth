package cx.rain.mc.fabric.rainauth.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

public interface PlayerCommandCallback {
    Event<PlayerCommandCallback> EVENT =
            EventFactory.createArrayBacked(PlayerCommandCallback.class,
                    listeners -> (command, player) -> {
                        for (PlayerCommandCallback c : listeners) {
                            TypedActionResult<String> result =  c.accept(command, player);
                            if (result.getResult() == ActionResult.FAIL) {
                                return result;
                            }
                        }
                        return TypedActionResult.pass("");
                    });

    TypedActionResult<String> accept(String command, ServerPlayerEntity player);
}
