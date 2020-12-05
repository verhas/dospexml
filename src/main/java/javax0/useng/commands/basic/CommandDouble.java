package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.NamedCommand;
import javax0.useng.commands.TextArgumentManager;
import javax0.useng.support.Convert;

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
        return CommandResult.simple(Convert.toDouble(result));
    }

    @Override
    public String name() {
        return "Double";
    }
}