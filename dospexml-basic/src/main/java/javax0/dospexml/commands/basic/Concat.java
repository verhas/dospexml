package javax0.dospexml.commands.basic;

import javax0.dospexml.api.NamedCommand;
import javax0.dospexml.commands.AbstractStringCommand;

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
