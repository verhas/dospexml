package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;
import org.w3c.dom.Node;

public class Sub implements NamedCommand<Void> {
    public CommandResult<Void> execute(CommandContext ctx) {
        final var name = ctx.parameter("name").orElseThrow(() -> new ExecutionException("Sub must have name"));
        final var query = ctx.<String, Node>staticQuery("subroutines");
        query.export(name, ctx.node());
        return CommandResult.VOID;
    }
}
