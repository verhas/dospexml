package javax0.dospexml.commands.basic;

import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.api.NamedCommand;
import javax0.dospexml.commands.TextArgumentManager;
import javax0.dospexml.support.Convert;

import java.util.List;

public class CommandLong implements NamedCommand<Long> {

    public ArgumentManager argumentManager() {
        return TextArgumentManager.INSTANCE;
    }

    @Override
    public CommandResult<Long> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var result = results.get(0);
        if (result.type() == Long.class) {
            return (CommandResult<Long>) result;
        }
        return CommandResult.simple(Convert.toLong(result));
    }

    @Override
    public String name() {
        return "Long";
    }
}
