package dospexml.commands.basic;

import dospexml.api.NamedCommand;
import dospexml.commands.AbstractStringCommand;

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
