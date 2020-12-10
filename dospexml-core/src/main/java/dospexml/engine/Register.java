package dospexml.engine;

import dospexml.commands.basic.Add;
import dospexml.commands.basic.Block;
import dospexml.commands.basic.Call;
import dospexml.commands.basic.CommandBigDecimal;
import dospexml.commands.basic.CommandBoolean;
import dospexml.commands.basic.CommandDouble;
import dospexml.commands.basic.CommandInteger;
import dospexml.commands.basic.CommandLong;
import dospexml.commands.basic.CommandString;
import dospexml.commands.basic.Compare;
import dospexml.commands.basic.Concat;
import dospexml.commands.basic.Get;
import dospexml.commands.basic.If;
import dospexml.commands.basic.Mult;
import dospexml.commands.basic.Not;
import dospexml.commands.basic.Null;
import dospexml.commands.basic.Puts;
import dospexml.commands.basic.Script;
import dospexml.commands.basic.Let;
import dospexml.commands.basic.Sub;
import dospexml.commands.basic.Throw;
import dospexml.commands.basic.Variable;
import dospexml.commands.basic.While;
import dospexml.commands.intrinsic.Require;

import java.io.PrintStream;

public class Register {
    private final Processor processor;

    private Register(Processor processor) {
        this.processor = processor;
    }

    public static Register withProcessor(Processor processor) {
        return new Register(processor);
    }

    public Register registerBasicCommands(final String nameSpace) {
        return registerBasicCommands(nameSpace, System.out);
    }

    public Register registerBasicCommands(final String nameSpace, final PrintStream output) {
        processor.commandRegister().register(nameSpace, new Script<>(), new Puts(output), new Variable(), new Let(),
            new Get(), new Concat(), new CommandDouble(), new CommandLong(), new CommandString(), new CommandBoolean(),
            new CommandInteger(), new Null(), new Block(), new If(), new While(),
            new Compare.Equals(),
            new Compare.Less(),
            new Compare.LessOrEqual(),
            new Compare.Greater(),
            new Compare.GreaterOrEqual(),
            new Not(), new Add(), new Mult(), new CommandBigDecimal(), new Throw(),
            new Sub(), new Call()
        );
        return this;
    }

    public Register registerIntrinsicCommands(final String nameSpace) {
        processor.commandRegister().register(nameSpace, new Require());
        return this;
    }
}
