package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.NamedCommand;

public class Block implements NamedCommand<Object> {

    @Override
    public CommandResult<Object> execute(CommandContext ctx) {
        CommandResult<?> result = CommandResult.VOID;
        for (final var node : ctx.nodeList()) {
            result = ctx.process(node);
        }
        return (CommandResult<Object>) result;
    }
}
