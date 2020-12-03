package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.NamedCommand;
import javax0.useng.commands.TextArgumentManager;

import java.util.List;

public class CommandDouble implements NamedCommand<Double> {

    public ArgumentManager argumentManager() {
        return TextArgumentManager.INSTANCE;
    }

    @Override
    public CommandResult<Double> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var result = results.get(0);
        if (result.type() == Double.class) {
            return (CommandResult<Double>) result;
        }
        if (result.type() == String.class) {
            final var d = Double.parseDouble((String) result.get());
            return CommandResult.simple(d, Double.class);
        }
        return CommandResult.simple((Double) result.get(), Double.class);
    }

    @Override
    public String name() {
        return "Double";
    }
}