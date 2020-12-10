package dospexml.commands.basic;

import dospexml.api.CommandContext;
import dospexml.api.CommandResult;
import dospexml.api.NamedCommand;

public class Null implements NamedCommand<Object> {

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
