package dospexml.commands.basic;

import dospexml.api.CommandContext;
import dospexml.api.CommandResult;
import dospexml.api.NamedCommand;

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
