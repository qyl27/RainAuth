package cx.rain.mc.fabric.rainauth.mixin.packet;

import cx.rain.mc.fabric.rainauth.interfaces.ITranslatablePacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientSettingsC2SPacketMixin.class)
public abstract class ClientSettingsC2SPacketMixin implements ITranslatablePacket {
    @Shadow
    private String language;

    @Override
    public String getLanguage() {
        return language;
    }
}
