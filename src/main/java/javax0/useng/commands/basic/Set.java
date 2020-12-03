package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;
import javax0.useng.commandtools.Xml;

public class Set implements NamedCommand<Void> {

    public CommandResult<Void> execute(CommandContext ctx) {
        final var nodes = ctx.nodeList();
        CommandResult<?> result;
        try (final var __ = ctx.processor().open()) {
            final var valueNode = Xml.child(ctx.node(), ctx.nameSpace().orElse(null), "value");
            if (valueNode.isEmpty()) {
                throw new ExecutionException("Set needs a value.");
            }
            final var value = Xml.child(valueNode.get(), 0).orElse(Xml.children(valueNode.get()).iterator().next());
            result = ctx.process(value);
        }
        final var name = ctx.parameter("name").orElseThrow(() -> new ExecutionException("Variable must have name"));
        final var localityString = ctx.parameter("locality").orElse("default");
        final var query = switch (localityString) {
            case "dynamic" -> ctx.<String, Variable.VariableHolder>dynamicQuery("variables");
            default -> ctx.<String, Variable.VariableHolder>staticQuery("variables");
        };
        final var variableHolder = query.exportIfAbsent(name, (s) -> new Variable.VariableHolder());
        variableHolder.value = result.get();
        return CommandResult.VOID;
    }
}
