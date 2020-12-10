package dospexml.commands;

import dospexml.api.Command;
import dospexml.api.CommandContext;
import dospexml.api.CommandResult;

import java.util.List;

public abstract class AbstractTextCommand implements Command<String> {
    public ArgumentManager argumentManager() {
        return TextArgumentManager.INSTANCE;
    }

    public abstract String evaluate(String s);

    @Override
    public CommandResult<String> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var sb = new StringBuilder();
        for (final var s : results) {
            sb.append("" + s.get());
        }
        final var s = evaluate(sb.toString());
        return CommandResult.simple(s, String.class);
    }
}
