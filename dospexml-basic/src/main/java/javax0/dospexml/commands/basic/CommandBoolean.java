package javax0.dospexml.commands.basic;

import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.api.NamedCommand;
import javax0.dospexml.commands.TextArgumentManager;

import java.util.List;

public class CommandBoolean implements NamedCommand<Boolean> {

    public ArgumentManager argumentManager() {
        return TextArgumentManager.INSTANCE;
    }

    @Override
    public CommandResult<Boolean> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var result = results.get(0);
        if (result.type() == Boolean.class) {
            return (CommandResult<Boolean>) result;
        }
        if( result.get() instanceof Number ){
            return CommandResult.simple(((Number) result.get()).intValue() != 0);
        }
        final var d = Boolean.parseBoolean((String) results.get(0).get());
        return CommandResult.simple(d);
    }

    @Override
    public String name() {
        return "Boolean";
    }
}