package cx.rain.mc.fabric.rainauth.event.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface PlayerMoveCallback {
    Event<PlayerMoveCallback> EVENT =
            EventFactory.createArrayBacked(PlayerMoveCallback.class,
                    listeners -> (player, world, direction) -> {
                        for (PlayerMoveCallback c : listeners) {
                            if (c.accept(player, world, direction) == ActionResult.FAIL) {
                                return ActionResult.FAIL;
                            }
                        }
                        return ActionResult.PASS;
                    });

    ActionResult accept(PlayerEntity player, World world, Vec3d direction);
}
