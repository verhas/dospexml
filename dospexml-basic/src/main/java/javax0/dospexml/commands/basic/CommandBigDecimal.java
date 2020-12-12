package javax0.dospexml.commands.basic;

import javax0.dospexml.api.AllNodesProcessing;
import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.support.Convert;
import javax0.dospexml.support.Trim;

import java.math.BigDecimal;
import java.util.List;

public class CommandBigDecimal implements Command<BigDecimal>, AllNodesProcessing {

    @Override
    public CommandResult<BigDecimal> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var trim = Trim.trim(results);
        final var result = trim.get(0).get();
        if (result instanceof BigDecimal) {
            return (CommandResult<BigDecimal>) trim.get(0);
        }
        return CommandResult.simple(Convert.toBigDecimal(trim.get(0)));
    }

    @Override
    public String name() {
        return "BigDecimal";
    }
}
