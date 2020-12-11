package javax0.dospexml.commands;

import javax0.dospexml.api.AllNodesProcessing;
import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.api.ExecutionException;
import javax0.dospexml.support.Convert;

import java.util.List;

public abstract class AbstractStringCommand implements Command<String>, AllNodesProcessing {

    public abstract String evaluate(String... s);

    @Override
    public CommandResult<String> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        return CommandResult.simple(evaluate(results.stream().map(Convert::toString).toArray(String[]::new)), String.class);
    }
}
