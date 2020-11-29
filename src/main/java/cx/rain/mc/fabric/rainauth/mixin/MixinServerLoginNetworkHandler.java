package cx.rain.mc.fabric.rainauth.mixin;

import com.mojang.authlib.GameProfile;
import cx.rain.mc.fabric.rainauth.event.callback.PlayerLeaveCallback;
import cx.rain.mc.fabric.rainauth.event.callback.PlayerLoginCallback;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.server.network.ServerLoginNetworkHandler;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLoginNetworkHandler.class)
public abstract class MixinServerLoginNetworkHandler {
    @Final
    @Shadow
    public ClientConnection connection;

    @Shadow
    private GameProfile profile;

    @Inject(method = "onHello", at = @At("HEAD"), cancellable = true)
    public void beforeOnHello(LoginHelloC2SPacket packet, CallbackInfo ci) {
        if (PlayerLoginCallback.EVENT.invoker().accept(packet.getProfile(), connection)
                == ActionResult.FAIL) {
            ci.cancel();
        }
    }

    @Inject(method = "onDisconnected", at = @At("HEAD"))
    public void beforeOnDisconnected(Text reason, CallbackInfo ci) {
        PlayerLeaveCallback.EVENT.invoker().accept(profile, connection, reason);
    }
}
