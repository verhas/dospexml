package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.NamedCommand;
import javax0.useng.commands.TextArgumentManager;

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