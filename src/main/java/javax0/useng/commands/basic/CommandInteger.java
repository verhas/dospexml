package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.NamedCommand;
import javax0.useng.commands.TextArgumentManager;
import javax0.useng.support.Convert;

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