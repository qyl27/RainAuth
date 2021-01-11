package cx.rain.mc.fabric.rainauth.mixin;

import cx.rain.mc.fabric.rainauth.event.callback.PlayerMoveCallback;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "onPlayerMove", at = @At("HEAD"), cancellable = true)
    public void beforeMovePlayer(PlayerMoveC2SPacket packet, CallbackInfo ci) {
        if (PlayerMoveCallback.EVENT.invoker()
                .accept(player, player.world,
                        new Vec3d(packet.getX(player.getX()),
                                packet.getY(player.getY()),
                                packet.getZ(player.getZ())))
                == ActionResult.FAIL) {
            ci.cancel();

            ServerSidePacketRegistry.INSTANCE.sendToPlayer(player,
                    new EntityPositionS2CPacket(player));
        }
    }
}
