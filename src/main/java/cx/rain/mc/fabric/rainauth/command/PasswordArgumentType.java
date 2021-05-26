package cx.rain.mc.fabric.rainauth.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class PasswordArgumentType implements ArgumentType<String> {
    private static final Collection<String> EXAMPLES = Arrays.asList("123456", "abcABC", "asdfgh");

    public static PasswordArgumentType password() {
        return new PasswordArgumentType();
    }

    @Override
    public String parse(StringReader reader) {
        int argBeginning = reader.getCursor();
        if (!reader.canRead()) {
            reader.skip();
        }

        while (reader.canRead()
                && reader.peek() != ' ') {
            reader.skip();
        }

        return reader.getString().substring(argBeginning, reader.getCursor());
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return builder.suggest("<Password>").buildFuture();
    }
}
