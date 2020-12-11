package javax0.dospexml.commands.basic;

import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.api.ExecutionException;
import javax0.dospexml.api.NamedCommand;

public class Variable implements NamedCommand<Variable.VariableHolder> {
    public CommandResult<VariableHolder> execute(CommandContext ctx) {
        final var name = ctx.parameter("name").orElseThrow(() -> new ExecutionException("Variable must have name"));
        final var localityString = ctx.parameter("locality").orElse("default");
        final var declare = ctx.parameter("declare").map(Boolean::parseBoolean).orElse(false);
        final var query = "dynamic".equals(localityString) ? ctx.<String, VariableHolder>dynamicQuery("variables") :
            ctx.<String, VariableHolder>staticQuery("variables");
        final VariableHolder variableHolder;
        if (declare) {
            variableHolder = new VariableHolder();
            query.export(name, variableHolder);
        } else {
            variableHolder = query.exportIfAbsent(name, (s) -> new VariableHolder());
        }
        return CommandResult.simple(variableHolder);
    }

    public static class VariableHolder {
        Object value;

        @Override
        public String toString() {
            return value == null ? "null:null" : value.getClass().getName() + ":" + value.toString();
        }
    }
}
