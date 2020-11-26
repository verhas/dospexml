package javax0.dysnow.commands;

import javax0.dysnow.api.Named;

public class CommandString extends AbstractTextCommand implements Named {
    @Override
    public String evaluate(String s) {
        return s;
    }

    @Override
    public String name() {
        return "string";
    }
}
