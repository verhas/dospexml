package javax0.dysnow.commands;

import javax0.dysnow.api.Command;
import javax0.dysnow.api.CommandContext;
import javax0.dysnow.api.CommandResult;
import javax0.dysnow.api.ExecutionException;
import javax0.dysnow.engine.SimpleCommandResult;
import javax0.dysnow.engine.TextArgumentManager;

import java.util.List;

public abstract class AbstractVoidTextCommand implements Command<Void> {
    public ArgumentManager argumentManager() {
        return TextArgumentManager.INSTANCE;
    }

    public abstract void evaluate(String s);

    @Override
    public CommandResult<Void> evaluate(CommandContext ctx, List<CommandResult<?>> results) throws ExecutionException {
        evaluate((String)results.get(0).get());
        return CommandResult.VOID;
    }
}
