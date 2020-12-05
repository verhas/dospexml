package javax0.useng.commands;

import javax0.useng.api.AllNodesProcessing;
import javax0.useng.api.Command;
import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.support.Convert;

import java.util.List;

public abstract class AbstractStringCommand implements Command<String>, AllNodesProcessing {

    public abstract String evaluate(String... s);

    @Override
    public CommandResult<String> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        return CommandResult.simple(evaluate(results.stream().map(Convert::toString).toArray(String[]::new)), String.class);
    }

    private static class Manager implements Command.ArgumentManager {

        @Override
        public void validateArguments(List<CommandResult<?>> results) {
            for (final var res : results) {
                if (res.type() != String.class) {
                    throw new ExecutionException("Command can run only on String arguments");
                }
            }
        }
    }


}
