package javax0.dospexml.commands.basic;

import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.api.NamedCommand;

import java.util.List;

public class Script<T> implements NamedCommand<T> {

    @Override
    public CommandResult<T> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        return (CommandResult<T>) results.get(results.size() - 1);
    }
}
