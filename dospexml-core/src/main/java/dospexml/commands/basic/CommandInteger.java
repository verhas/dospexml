package dospexml.commands.basic;

import dospexml.api.CommandContext;
import dospexml.api.CommandResult;
import dospexml.api.NamedCommand;
import dospexml.commands.TextArgumentManager;
import javax0.dospexml.support.Convert;

import java.util.List;

public class CommandInteger implements NamedCommand<Integer> {

    public ArgumentManager argumentManager() {
        return TextArgumentManager.INSTANCE;
    }

    @Override
    public CommandResult<Integer> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var result = results.get(0);
        if (result.type() == Integer.class) {
            return (CommandResult<Integer>) result;
        }
        return CommandResult.simple(Convert.toInt(result));
    }

    @Override
    public String name() {
        return "Integer";
    }
}