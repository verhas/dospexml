package javax0.dospexml.support;

import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.api.ExecutionException;
import javax0.dospexml.api.NamedCommand;

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
