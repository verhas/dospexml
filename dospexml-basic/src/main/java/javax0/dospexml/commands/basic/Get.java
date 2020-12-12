package javax0.dospexml.commands.basic;

import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.api.ExecutionException;

public class Get implements Command<Object> {
    public CommandResult<Object> execute(CommandContext ctx) {
        final var name = ctx.parameter("name").orElseThrow(() -> new ExecutionException("Variable must have name"));
        final var localityString = ctx.parameter("locality").orElse("default");
        final var query = "dynamic".equals(localityString) ? ctx.<String, Variable.VariableHolder>dynamicQuery("variables") : ctx.<String, Variable.VariableHolder>staticQuery("variables");
        final var variableHolder = query.computeIfAbsent(name, (s) -> new Variable.VariableHolder());
        return CommandResult.simple(variableHolder.value);
    }
}
