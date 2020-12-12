package javax0.dospexml.commands.basic;

import javax0.dospexml.api.AllNodesProcessing;
import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;

import java.util.List;

public class CommandString implements Command<String>, AllNodesProcessing {
    @Override
    public CommandResult<String> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var sb = new StringBuilder();
        for (final var s : results) {
            sb.append("" + s.get());
        }
        return CommandResult.simple(sb.toString(), String.class);
    }

    @Override
    public String name() {
        return "String";
    }
}
