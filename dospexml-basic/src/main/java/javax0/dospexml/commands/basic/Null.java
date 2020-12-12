package javax0.dospexml.commands.basic;

import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;

public class Null implements Command<Object> {

    @Override
    public CommandResult<Object> execute(CommandContext ctx) {
        final var type = ctx.parameter("class").orElse("java.lang.Object");
        final Class<?> klass;
        try {
            klass = Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw ctx.exception("Type name " + type + " is not a valid class name");
        }
        return CommandResult.simple(null, klass);
    }
}
