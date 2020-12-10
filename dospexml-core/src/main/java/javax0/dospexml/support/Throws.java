package javax0.dospexml.support;

import dospexml.api.CommandContext;
import dospexml.api.CommandResult;
import dospexml.api.ExecutionException;
import dospexml.api.NamedCommand;

public class Throws implements NamedCommand<Void> {

    @Override
    public CommandResult<Void> execute(CommandContext ctx) {
        for (final var node : ctx.nodeList()) {
            try {
                ctx.process(node);
            } catch (ExecutionException ee) {
                return CommandResult.VOID;
            }
        }
        throw new AssertionError("Throws did not throw exception.");
    }
}
