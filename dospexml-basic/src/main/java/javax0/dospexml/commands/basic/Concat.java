package javax0.dospexml.commands.basic;

import javax0.dospexml.api.AllNodesProcessing;
import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.support.Convert;

import java.util.List;

public class Concat implements Command<String>, AllNodesProcessing {

    @Override
    public CommandResult<String> evaluate(CommandContext ctx, List<CommandResult<?>> results) {

        final var sb = new StringBuilder();
        for (final var result : results) {
            final var string = Convert.toString(result);
            sb.append(string);
        }
        return CommandResult.simple(sb.toString(), String.class);
    }
}
