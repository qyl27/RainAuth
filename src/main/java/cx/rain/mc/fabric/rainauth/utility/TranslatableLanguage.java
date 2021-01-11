package cx.rain.mc.fabric.rainauth.utility;

import com.google.common.collect.ImmutableMap;
import cx.rain.mc.fabric.rainauth.RainAuth;
import net.minecraft.client.font.TextVisitFactory;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.util.Language;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class TranslatableLanguage extends Language {
    private Map<String, String> map;

    public TranslatableLanguage(String localeIn) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        BiConsumer<String, String> biConsumer = builder::put;

        try {
            InputStream is = this.getClass()
                    .getResourceAsStream("/assets/" + RainAuth.MODID + "/lang/" + localeIn + ".json");
            load(is, biConsumer);
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        map = builder.build();
    }

    @Override
    public String get(String key) {
        return map.getOrDefault(key, key);
    }

    @Override
    public boolean hasTranslation(String key) {
        return map.containsKey(key);
    }

    @Override
    public boolean isRightToLeft() {
        return false;
    }

    @Override
    public OrderedText reorder(StringVisitable text) {
        return (visitor) -> text.visit((style, string) ->
                TextVisitFactory.visitFormatted(string, style, visitor)
                        ? Optional.empty()
                        : StringVisitable.TERMINATE_VISIT, Style.EMPTY).isPresent();
    }
}
