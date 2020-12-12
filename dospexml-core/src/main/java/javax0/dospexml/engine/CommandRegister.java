package javax0.dospexml.engine;

import javax0.dospexml.api.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandRegister implements AutoCloseable {
    final List<CommandMap> stack = new ArrayList<>();

    CommandRegister open() {
        stack.add(new CommandMap());
        return this;
    }

    public void close() {
        stack.remove(stack.size() - 1);
    }

    public void register(String uri, Command<?>... commands) {
        for (final var command : commands) {
            register(uri, command.name(), command);
        }
    }

    public <T> void register(String uri, String tag, Command<T> command) {
        int i = stack.size();
        stack.get(i - 1).put(uri, tag, command);
    }

    public Optional<Command<?>> get(String uri, String tag) {
        int i = stack.size() - 1;
        while (i >= 0) {
            final Optional<Command<?>> command;
            if ((command = stack.get(i).get(uri, tag)).isPresent()) {
                return command;
            }
            i--;
        }
        return Optional.empty();
    }
}
