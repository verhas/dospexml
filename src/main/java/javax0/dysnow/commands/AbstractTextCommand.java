package javax0.dysnow.commands;

import javax0.dysnow.api.Command;
import javax0.dysnow.api.CommandContext;
import javax0.dysnow.api.CommandResult;
import javax0.dysnow.api.ExecutionException;
import javax0.dysnow.engine.SimpleCommandResult;
import javax0.dysnow.engine.TextArgumentManager;

import java.util.List;

public abstract class AbstractTextCommand implements Command<String> {
    public ArgumentManager argumentManager() {
        return TextArgumentManager.INSTANCE;
    }

    public abstract String evaluate(String s);

    @Override
    public CommandResult<String> evaluate(CommandContext ctx, List<CommandResult<?>> results) throws ExecutionException {
        final var s = evaluate((String)results.get(0).get());
        return new SimpleCommandResult<String>(s,String.class);
    }
}
