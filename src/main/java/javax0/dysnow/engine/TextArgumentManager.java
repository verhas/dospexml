package javax0.dysnow.engine;

import javax0.dysnow.api.Command;
import javax0.dysnow.api.CommandResult;
import javax0.dysnow.api.ExecutionException;

import java.util.List;

public class TextArgumentManager implements Command.ArgumentManager {
    public static final Command.ArgumentManager INSTANCE = new TextArgumentManager();

    @Override
    public int min() {
        return 1;
    }

    @Override
    public int max() {
        return 1;
    }

    @Override
    public boolean needsTextSegments() {
        return true;
    }

    @Override
    public void validateArguments(List<CommandResult<?>> results) throws ExecutionException {
        if (!(results.get(0) instanceof Command.TextNode)) {
            throw new ExecutionException("puts needs exactly one text node");
        }
    }
}
