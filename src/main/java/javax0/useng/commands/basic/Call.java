package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.NamedCommand;
import javax0.useng.commandtools.Xml;
import org.w3c.dom.Node;

public class Call implements NamedCommand<Object> {

    @Override
    public CommandResult<Object> execute(CommandContext ctx) {
        try (final var __ = ctx.processor().open()) {
            final var name = ctx.parameter("name")
                .orElseThrow(() -> ctx.exception("Call must have name of a sub to call"));
            CommandResult<?> result = CommandResult.VOID;
            for (final var node : ctx.nodeList()) {
                result = ctx.process(node);
            }
            final var query = ctx.<String, Node>staticQuery("subroutines");
            Node subroutine = query.get(name);
            if (subroutine == null) {
                ctx.exception("Subroutine " + name + " is not defined");
            }
            for (final var node : Xml.children(subroutine)) {
                if (Xml.isNonTextNode(node)) {
                    result = ctx.process(node);
                }
            }
            return (CommandResult<Object>) result;
        }
    }
}
