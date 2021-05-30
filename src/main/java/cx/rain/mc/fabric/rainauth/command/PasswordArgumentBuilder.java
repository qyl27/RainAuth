package cx.rain.mc.fabric.rainauth.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;

public class PasswordArgumentBuilder<SOURCE, TYPE>
        extends ArgumentBuilder<SOURCE, PasswordArgumentBuilder<SOURCE, TYPE>> {
    private final String name;
    private final ArgumentType<TYPE> type;
    private SuggestionProvider<SOURCE> suggestionsProvider = null;

    private PasswordArgumentBuilder(String name, ArgumentType<TYPE> type) {
        this.name = name;
        this.type = type;
    }

    public static <SOURCE, TYPE> PasswordArgumentBuilder<SOURCE, TYPE> create(String name, ArgumentType<TYPE> type) {
        return new PasswordArgumentBuilder<>(name, type);
    }

    @Override
    protected PasswordArgumentBuilder<SOURCE, TYPE> getThis() {
        return this;
    }

    public String getName() {
        return name;
    }

    public ArgumentType<TYPE> getType() {
        return type;
    }

    public SuggestionProvider<SOURCE> getSuggestionsProvider() {
        return suggestionsProvider;
    }

    @Override
    public CommandNode<SOURCE> build() {
        final PasswordCommandNode<SOURCE, TYPE> result =
                new PasswordCommandNode<>(getName(), getType(), getCommand(),
                        getRequirement(), getRedirect(), getRedirectModifier(),
                        isFork(), getSuggestionsProvider());

        for (final CommandNode<SOURCE> argument : getArguments()) {
            result.addChild(argument);
        }

        return result;
    }
}
