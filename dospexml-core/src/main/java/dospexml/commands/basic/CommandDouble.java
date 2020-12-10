package dospexml.commands.basic;

import dospexml.api.CommandContext;
import dospexml.api.CommandResult;
import dospexml.api.NamedCommand;
import dospexml.commands.TextArgumentManager;
import javax0.dospexml.support.Convert;

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