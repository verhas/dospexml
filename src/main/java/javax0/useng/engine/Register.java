package javax0.useng.engine;

import javax0.useng.commands.basic.Add;
import javax0.useng.commands.basic.Block;
import javax0.useng.commands.basic.CommandBigDecimal;
import javax0.useng.commands.basic.CommandBoolean;
import javax0.useng.commands.basic.CommandDouble;
import javax0.useng.commands.basic.CommandInteger;
import javax0.useng.commands.basic.CommandLong;
import javax0.useng.commands.basic.CommandString;
import javax0.useng.commands.basic.Compare;
import javax0.useng.commands.basic.Concat;
import javax0.useng.commands.basic.Get;
import javax0.useng.commands.basic.If;
import javax0.useng.commands.basic.Not;
import javax0.useng.commands.basic.Null;
import javax0.useng.commands.basic.Puts;
import javax0.useng.commands.basic.Script;
import javax0.useng.commands.basic.Let;
import javax0.useng.commands.basic.Throw;
import javax0.useng.commands.basic.Variable;
import javax0.useng.commands.basic.While;
import javax0.useng.commands.intrinsic.Require;

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
            new CommandInteger(), new Null(), new Block(), new If(), new While(), new Compare.Equals(),
            new Compare.Less(),
            new Compare.LessOrEqual(),
            new Compare.Greater(),
            new Compare.GreaterOrEqual(),
            new Not(), new Add(), new CommandBigDecimal(), new Throw()
        );
        return this;
    }

    public Register registerIntrinsicCommands(final String nameSpace) {
        processor.commandRegister().register(nameSpace, new Require());
        return this;
    }
}
