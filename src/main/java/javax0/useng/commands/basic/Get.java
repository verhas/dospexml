package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;

public class Get implements NamedCommand<Object> {
    public CommandResult<Object> execute(CommandContext ctx) {
        final var name = ctx.parameter("name").orElseThrow(() -> new ExecutionException("Variable must have name"));
        final var localityString = ctx.parameter("locality").orElse("default");
        final var query = "dynamic".equals(localityString) ? ctx.<String, Variable.VariableHolder>dynamicQuery("variables") : ctx.<String, Variable.VariableHolder>staticQuery("variables");
        final var variableHolder = query.computeIfAbsent(name, (s) -> new Variable.VariableHolder());
        return CommandResult.simple(variableHolder.value);
    }
}
