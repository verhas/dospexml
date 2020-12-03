package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;
import javax0.useng.commandtools.Xml;
import org.w3c.dom.Node;

public class While implements NamedCommand<Object> {

    public CommandResult<Object> execute(CommandContext ctx) {
        Node conditionNode = Xml.child(ctx.node(), ctx.nameSpace().orElse(null), "condition")
            .orElseThrow(() -> new ExecutionException("While needs a condition"));
        Node thenNode = Xml.child(ctx.node(), 1).orElse(null);

        final var conditionCode = Xml.child(conditionNode, 0)
            .orElseThrow(() -> new ExecutionException("Condition needs a boolean expression in if."));

        CommandResult<Object> result = CommandResult.simple(null, Object.class);
        for (; ; ) {
            CommandResult<Boolean> condition = ctx.process(conditionCode);
            if (condition.type() != Boolean.class) {
                throw new ExecutionException("The condition of an While has to be boolean");
            }

            if (condition.get()) {
                if (thenNode != null) {
                    result = ctx.process(thenNode);
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