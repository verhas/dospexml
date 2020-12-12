package javax0.dospexml.commands.basic;

import javax0.dospexml.api.CommandPackageBuilder;
import javax0.dospexml.api.Processor;

import java.io.PrintStream;

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

    private PrintStream output = System.out;

    @Override
    public <T> CommandPackageBuilder using(String key, T value) {
        if (key == null || !key.equals("puts")) {
            throw new IllegalArgumentException("Basic command set can only be configured using 'puts' key.");
        }
        if (value instanceof PrintStream) {
            output = (PrintStream) value;
        } else {
            throw new IllegalArgumentException("Basic command set configuration key 'puts' need a PrintStream.");
        }
        return this;
    }


    @Override
    public void register() {
        processor.commandRegister().register(nameSpace,
            new Add(),
            new Block(),
            new Call(),
            new CommandBigDecimal(),
            new CommandBoolean(),
            new CommandDouble(),
            new CommandInteger(),
            new CommandLong(),
            new CommandString(),
            new Compare.Equals(),
            new Compare.Less(),
            new Compare.LessOrEqual(),
            new Compare.Greater(),
            new Compare.GreaterOrEqual(),
            new Concat(),
            new Get(),
            new If(),
            new Mult(),
            new Not(),
            new Null(),
            new Puts(output),
            new Script<>(),
            new Let(),
            new Sub(),
            new Throw(),
            new Variable(),
            new While());
    }
}
