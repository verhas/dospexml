package javax0.dospexml.commands.basic;

import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.api.ExecutionException;
import javax0.dospexml.commandtools.Xml;
import org.w3c.dom.Node;

public class While implements Command<Object> {

    public CommandResult<Object> execute(CommandContext ctx) {
        final Node conditionNode = Xml.child(ctx.node(), ctx.nameSpace().orElse(null), "condition")
            .orElseThrow(() -> new ExecutionException("While needs a condition"));
        final var conditionCode = Xml.child(conditionNode, 0)
            .orElseThrow(() -> new ExecutionException("Condition needs a boolean expression in if."));

        final Node block = Xml.child(ctx.node(), 1).orElse(null);

        CommandResult<Object> result = CommandResult.simple(null, Object.class);
        for (; ; ) {
            CommandResult<Boolean> condition = ctx.process(conditionCode);
            if (condition.type() != Boolean.class) {
                throw ctx.exception("The condition of an While has to be boolean");
            }

            if (condition.get()) {
                if (block != null) {
                    result = ctx.process(block);
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return result;
    }
}