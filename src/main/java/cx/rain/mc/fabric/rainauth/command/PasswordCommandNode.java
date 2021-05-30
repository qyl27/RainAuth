package cx.rain.mc.fabric.rainauth.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;

import java.util.function.Predicate;

public class PasswordCommandNode<SOURCE, TYPE> extends ArgumentCommandNode<SOURCE, TYPE> {
    public PasswordCommandNode(String name, ArgumentType<TYPE> type, Command<SOURCE> command,
                               Predicate<SOURCE> requirement, CommandNode<SOURCE> redirect,
                               RedirectModifier<SOURCE> modifier, boolean forks,
                               SuggestionProvider<SOURCE> customSuggestions) {
        super(name, type, command, requirement, redirect, modifier, forks, customSuggestions);
    }

    @Override
    public boolean isValidInput(String input) {
        final StringReader reader = new StringReader(input);
        parse(reader);
        return !reader.canRead() || reader.peek() == ' ';
    }

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
}
