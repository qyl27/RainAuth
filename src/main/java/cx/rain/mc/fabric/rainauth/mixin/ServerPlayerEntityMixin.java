package cx.rain.mc.fabric.rainauth.mixin;

import cx.rain.mc.fabric.rainauth.interfaces.ITranslatable;
import cx.rain.mc.fabric.rainauth.interfaces.ITranslatablePacket;
import cx.rain.mc.fabric.rainauth.utility.TranslatableLanguage;
import net.minecraft.network.packet.c2s.play.ClientSettingsC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements ITranslatable {
    private TranslatableLanguage language;

    @Inject(at = @At("HEAD"), method = "setClientSettings")
    public void beforeSetClientSettings(ClientSettingsC2SPacket packet, CallbackInfo ci) {
        //language = new TranslatableLanguage(((ITranslatablePacket) packet).getLanguage());
        language = new TranslatableLanguage("zh_cn");
    }

    @Override
    public TranslatableLanguage getLanguage() {
        return language;
    }
}
