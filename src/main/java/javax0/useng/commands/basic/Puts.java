package javax0.useng.commands.basic;

import javax0.useng.api.AllNodesProcessing;
import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.NamedCommand;
import javax0.useng.commands.AbstractVoidTextCommand;

import java.io.OutputStream;

public class Puts extends AbstractVoidTextCommand implements NamedCommand<Void>, AllNodesProcessing {
    private boolean trim;
    private final OutputStream output;

    public Puts() {
        this.output = System.out;
    }

    public Puts(OutputStream output) {
        this.output = output;
    }

    @Override
    public void evaluate(String s) {
        System.out.println(trim ? s.trim() : s);
    }

    public CommandResult<Void> execute(CommandContext ctx) {
        trim = ctx.parameter("trim").map(Boolean::parseBoolean).orElse(false);
        return super.execute(ctx);
    }
}

