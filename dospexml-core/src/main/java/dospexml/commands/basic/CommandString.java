package dospexml.commands.basic;

import dospexml.api.NamedCommand;
import dospexml.commands.AbstractTextCommand;

public class CommandString extends AbstractTextCommand implements NamedCommand<String> {
    @Override
    public String evaluate(String s) {
        return s;
    }

    @Override
    public String name() {
        return "String";
    }
}
