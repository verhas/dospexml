package javax0.dysnow.commands;

import javax0.dysnow.api.CommandContext;
import javax0.dysnow.api.CommandResult;
import javax0.dysnow.api.ExecutionException;
import javax0.dysnow.api.Named;
import javax0.dysnow.api.NamedCommand;
import javax0.dysnow.engine.SimpleCommandResult;
import javax0.dysnow.engine.TextArgumentManager;

import java.util.List;

public class CommandLong implements NamedCommand<Long> {

    public ArgumentManager argumentManager() {
        return TextArgumentManager.INSTANCE;
    }

    @Override
    public CommandResult<Long> evaluate(CommandContext ctx, List<CommandResult<?>> results) throws ExecutionException {
        final var l = Long.parseLong((String)results.get(0).get());
        return new SimpleCommandResult<>(l,Long.class);
    }

    @Override
    public String name() {
        return "number";
    }
}
