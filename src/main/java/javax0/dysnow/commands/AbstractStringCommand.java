package javax0.dysnow.commands;

import javax0.dysnow.api.Command;
import javax0.dysnow.api.CommandContext;
import javax0.dysnow.api.CommandResult;
import javax0.dysnow.api.ExecutionException;
import javax0.dysnow.engine.SimpleCommandResult;

import java.util.List;

public abstract class AbstractStringCommand implements Command<String>{

    public abstract String evaluate(String ...s);

    @Override
    public CommandResult<String> evaluate(CommandContext ctx, List<CommandResult<?>> results) throws ExecutionException {
        return new SimpleCommandResult<>(evaluate(results.stream().toArray(String[]::new)),String.class);
    }

    private static class Manager implements Command.ArgumentManager {

        @Override
        public void validateArguments(List<CommandResult<?>> results) throws ExecutionException {
            for( final var res : results ){
                if( res.type() != String.class ){
                    throw new ExecutionException("Command can run only on String arguments");
                }
            }
        }
    }


}
