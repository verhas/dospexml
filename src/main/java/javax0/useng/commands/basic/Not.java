package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;

import java.util.List;

public class Not implements NamedCommand<Boolean> {
    @Override
    public CommandResult<Boolean> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var result = results.get(0);
        if (result.type() != Boolean.class) {
            throw ctx.exception("Not needs a Boolean argument");
        }
        return CommandResult.simple(!(Boolean) result.get(), Boolean.class);
    }
}
