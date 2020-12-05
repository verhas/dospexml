package javax0.useng.commands.basic;

import javax0.useng.api.AllNodesProcessing;
import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.NamedCommand;
import javax0.useng.commands.AbstractVoidTextCommand;
import javax0.useng.support.Convert;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.annotation.RetentionPolicy;

public class Puts extends AbstractVoidTextCommand implements NamedCommand<Void>, AllNodesProcessing {
    private boolean trim;
    private String trail;
    private final PrintStream output;

    public Puts() {
        this.output = System.out;
    }

    public Puts(PrintStream output) {
        this.output = output;
    }

    @Override
    public void evaluate(String s) {
        output.print((trim ? s.trim() : s) + trail);
    }

    public CommandResult<Void> execute(CommandContext ctx) {
        boolean trimSave = trim;
        String trailSave = trail;
        try {
            trim = ctx.parameter("trim").map(Boolean::parseBoolean).orElse(false);
            trail = Convert.unescape(ctx.parameter("trail").orElse(""));
            return super.execute(ctx);
        } catch (IOException e) {
            throw ctx.exception("Invalid 'trail' string.");
        } finally {
            trim = trimSave;
            trail = trailSave;
        }
    }
}

