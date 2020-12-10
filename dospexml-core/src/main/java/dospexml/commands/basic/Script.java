package dospexml.commands.basic;

import dospexml.api.CommandContext;
import dospexml.api.CommandResult;
import dospexml.api.NamedCommand;

import java.util.List;

public class Script<T> implements NamedCommand<T> {

    @Override
    public CommandResult<T> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        return (CommandResult<T>) results.get(results.size() - 1);
    }
}
