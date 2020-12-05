package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;
import javax0.useng.commandtools.Xml;

public class Let implements NamedCommand<Void> {

    public CommandResult<Void> execute(CommandContext ctx) {
        final var nodes = ctx.nodeList();
        CommandResult<?> result;
        try (final var __ = ctx.processor().open()) {
            final var valueNode = Xml.child(ctx.node(), 0);
            if (valueNode.isEmpty()) {
                throw ctx.exception("Set needs a value.");
            }
            result = ctx.process(valueNode.get());
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
