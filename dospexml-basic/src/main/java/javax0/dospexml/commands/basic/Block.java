package javax0.dospexml.commands.basic;

import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;

public class Block implements Command<Object> {

    @Override
    public CommandResult<Object> execute(CommandContext ctx) {
        CommandResult<?> result = CommandResult.VOID;
        for (final var node : ctx.nodeList()) {
            result = ctx.process(node);
        }
        return (CommandResult<Object>) result;
    }
}
