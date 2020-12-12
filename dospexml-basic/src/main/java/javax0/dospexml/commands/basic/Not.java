package javax0.dospexml.commands.basic;

import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;

import java.util.List;

public class Not implements Command<Boolean> {
    @Override
    public CommandResult<Boolean> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var result = results.get(0);
        if (result.type() != Boolean.class) {
            throw ctx.exception("Not needs a Boolean argument");
        }
        return CommandResult.simple(!(Boolean) result.get(), Boolean.class);
    }
}
