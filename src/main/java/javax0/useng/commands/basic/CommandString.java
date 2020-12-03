package javax0.useng.commands.basic;

import javax0.useng.api.NamedCommand;
import javax0.useng.commands.AbstractTextCommand;

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
