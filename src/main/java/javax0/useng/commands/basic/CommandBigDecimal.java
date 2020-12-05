package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;
import javax0.useng.commands.TextArgumentManager;
import javax0.useng.support.Convert;

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
