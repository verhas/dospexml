package javax0.dospexml.commands.basic;

import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.commandtools.Xml;
import org.w3c.dom.Node;

public class Call implements Command<Object> {

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
                throw ctx.exception("Subroutine " + name + " is not defined");
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
