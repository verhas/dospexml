package javax0.useng.commands.basic;

import javax0.useng.api.AllNodesProcessing;
import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;

import java.util.List;

public class Throw implements NamedCommand<Void> , AllNodesProcessing {
    @Override
    public CommandResult<Void> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        throw ctx.exception(""+results.get(0).get());
    }
}
