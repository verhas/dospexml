package dospexml.commands.basic;

import dospexml.api.AllNodesProcessing;
import dospexml.api.CommandContext;
import dospexml.api.CommandResult;
import dospexml.api.NamedCommand;
import dospexml.commands.AbstractVoidTextCommand;
import javax0.dospexml.support.Convert;

import java.io.IOException;
import java.io.PrintStream;

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

