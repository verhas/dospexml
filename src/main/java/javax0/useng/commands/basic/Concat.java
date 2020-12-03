package javax0.useng.commands.basic;

import javax0.useng.api.NamedCommand;
import javax0.useng.commands.AbstractStringCommand;

public class Concat extends AbstractStringCommand implements NamedCommand<String> {
    @Override
    public String evaluate(String... strings) {
        final var sb = new StringBuilder();
        for (final var string : strings) {
            sb.append(string);
        }
        return sb.toString();
    }
}
