package javax0.dospexml.commands.basic;

import javax0.dospexml.api.AllNodesProcessing;
import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.support.Convert;
import javax0.dospexml.support.Trim;

import java.util.List;

public class CommandInteger implements Command<Integer>, AllNodesProcessing {

    @Override
    public CommandResult<Integer> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var result = Trim.trim(results).get(0);
        if (result.type() == Integer.class) {
            return (CommandResult<Integer>) result;
        }
        return CommandResult.simple(Convert.toInt(result));
    }

    @Override
    public String name() {
        return "Integer";
    }
}