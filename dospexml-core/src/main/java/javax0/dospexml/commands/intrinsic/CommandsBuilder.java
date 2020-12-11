package javax0.dospexml.commands.intrinsic;

import javax0.dospexml.api.CommandPackageBuilder;
import javax0.dospexml.api.Processor;

public class CommandsBuilder implements CommandPackageBuilder {
    private Processor processor = null;
    private String nameSpace = "";
    @Override
    public CommandPackageBuilder with(Processor p) {
        processor = p;
        return this;
    }

    @Override
    public CommandPackageBuilder nameSpace(String ns) {
        nameSpace = ns;
        return this;
    }

    @Override
    public void register() {
        processor.commandRegister().register(nameSpace, new Require());
    }
}
