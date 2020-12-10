package dospexml.commands.basic;

import dospexml.api.CommandContext;
import dospexml.api.CommandResult;
import dospexml.api.NamedCommand;
import dospexml.commands.TextArgumentManager;
import javax0.dospexml.support.Convert;

import java.math.BigDecimal;
import java.util.List;

public class CommandBigDecimal implements NamedCommand<BigDecimal> {
    public ArgumentManager argumentManager() {
        return TextArgumentManager.INSTANCE;
    }

    @Override
    public CommandResult<BigDecimal> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var result = results.get(0).get();
        if (result instanceof BigDecimal) {
            return (CommandResult<BigDecimal>) results.get(0);
        }
        return CommandResult.simple(Convert.toBigDecimal(results.get(0)));
    }

    @Override
    public String name() {
        return "BigDecimal";
    }
}
