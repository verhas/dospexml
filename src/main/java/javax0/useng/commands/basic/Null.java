package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;

public class Null implements NamedCommand<Object> {

    @Override
    public CommandResult<Object> execute(CommandContext ctx) {
        final var type = ctx.parameter("class").orElse("java.lang.Object");
        final Class<?> klass;
        try {
            klass = Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw new ExecutionException("Type name " + type + " is not a valid class name");
        }
        return CommandResult.simple(null, klass);
    }
}
