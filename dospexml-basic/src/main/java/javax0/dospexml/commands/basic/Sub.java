package javax0.dospexml.commands.basic;

import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.api.ExecutionException;
import javax0.dospexml.api.NamedCommand;
import org.w3c.dom.Node;

public class Sub implements NamedCommand<Void> {
    public CommandResult<Void> execute(CommandContext ctx) {
        final var name = ctx.parameter("name").orElseThrow(() -> new ExecutionException("Sub must have name"));
        final var query = ctx.<String, Node>staticQuery("subroutines");
        query.export(name, ctx.node());
        return CommandResult.VOID;
    }
}
