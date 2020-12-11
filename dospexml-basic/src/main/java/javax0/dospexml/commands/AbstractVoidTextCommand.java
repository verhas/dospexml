package javax0.dospexml.commands;

import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;

import java.util.List;

public abstract class AbstractVoidTextCommand implements Command<Void> {
    public ArgumentManager argumentManager() {
        return TextArgumentManager.INSTANCE;
    }

    public abstract void evaluate(String s);

    @Override
    public CommandResult<Void> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var sb = new StringBuilder();
        for (final var s : results) {
            sb.append("" + s.get());
        }
        evaluate(sb.toString());
        return CommandResult.VOID;
    }
}
