package javax0.dospexml.commands.basic;

import javax0.dospexml.api.AllNodesProcessing;
import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.support.Convert;
import javax0.dospexml.support.Trim;

import java.util.List;

public class CommandLong implements Command<Long>, AllNodesProcessing {

    @Override
    public CommandResult<Long> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var result = Trim.trim(results).get(0);
        if (result.type() == Long.class) {
            return (CommandResult<Long>) result;
        }
        return CommandResult.simple(Convert.toLong(result));
    }

    @Override
    public String name() {
        return "Long";
    }
}
