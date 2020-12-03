package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.NamedCommand;

import java.util.List;

public class Script<T> implements NamedCommand<T> {

    @Override
    public CommandResult<T> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        return (CommandResult<T>) results.get(results.size() - 1);
    }
}
