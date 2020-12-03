package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;

public class Variable implements NamedCommand<Variable.VariableHolder> {
    public CommandResult<VariableHolder> execute(CommandContext ctx) {
        final var name = ctx.parameter("name").orElseThrow(() -> new ExecutionException("Variable must have name"));
        final var localityString = ctx.parameter("locality").orElse("default");
        final var query = switch (localityString) {
            case "dynamic" -> ctx.<String, VariableHolder>dynamicQuery("variables");
            default -> ctx.<String, VariableHolder>staticQuery("variables");
        };
        final var variableHolder = query.exportIfAbsent(name, (s) -> new VariableHolder());
        return CommandResult.simple(variableHolder);
    }

    public static class VariableHolder {
        Object value;
    }
}
