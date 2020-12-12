package javax0.dospexml.commands.basic;

import javax0.dospexml.api.AllNodesProcessing;
import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.support.Convert;
import javax0.dospexml.support.Trim;

import java.util.List;

public class CommandDouble implements Command<Double>, AllNodesProcessing {

    @Override
    public CommandResult<Double> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var result = Trim.trim(results).get(0);
        if (result.type() == Double.class) {
            return (CommandResult<Double>) result;
        }
        return CommandResult.simple(Convert.toDouble(result));
    }

    @Override
    public String name() {
        return "Double";
    }
}