package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;
import javax0.useng.commandtools.Xml;
import org.w3c.dom.Node;

import java.util.Optional;

public class If implements NamedCommand<Object> {

    public CommandResult<Object> execute(CommandContext ctx) {
        Node conditionNode = Xml.child(ctx.node(), ctx.nameSpace().orElse(null), "condition")
            .orElseThrow(() -> new ExecutionException("If needs a condition"));
        Node thenNode = Xml.child(ctx.node(), ctx.nameSpace().orElse(null), "then")
            .orElseThrow(() -> new ExecutionException("If needs a then"));
        Node elseNode = Xml.child(ctx.node(), ctx.nameSpace().orElse(null), "else")
            .orElse(null);
        final var conditionCode = Xml.child(conditionNode, 0)
            .orElseThrow(() -> new ExecutionException("Condition needs a boolean expression in if."));

        CommandResult<Boolean> condition = ctx.process(conditionCode);
        if (condition.type() != Boolean.class) {
            throw ctx.exception("The condition of an IF has to be boolean");
        }

        if (condition.get()) {
            final var thenCode = Xml.child(thenNode, 0);
            if (thenCode.isEmpty()) {
                return CommandResult.simple(null, Object.class);
            } else {
                return ctx.process(thenCode.get());
            }
        } else {
            final Optional<Node> elseCode;
            if (elseNode == null || (elseCode = Xml.child(elseNode, 0)).isEmpty()) {
                return CommandResult.simple(null, Object.class);
            } else {
                return ctx.process(elseCode.get());
            }
        }
    }
}
