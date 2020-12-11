package javax0.dospexml.commands.basic;

import javax0.dospexml.api.NamedCommand;
import javax0.dospexml.commands.AbstractTextCommand;

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
