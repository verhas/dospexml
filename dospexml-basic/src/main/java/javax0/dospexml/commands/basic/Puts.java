package javax0.dospexml.commands.basic;

import javax0.dospexml.api.AllNodesProcessing;
import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.support.Convert;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class Puts implements Command<Void>, AllNodesProcessing {
    private final PrintStream output;

    public Puts() {
        this.output = System.out;
    }

    public Puts(PrintStream output) {
        this.output = output;
    }

    @Override
    public CommandResult<Void> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final boolean trim = ctx.parameter("trim").map(Boolean::parseBoolean).orElse(false);
        final String trail;
        try {
            trail = Convert.unescape(ctx.parameter("trail").orElse(""));
        } catch (IOException e) {
            throw ctx.exception("Invalid 'trail' string.");
        }
        final var sb = new StringBuilder();
        for (final var s : results) {
            sb.append("" + s.get());
        }
        final var s = sb.toString();
        output.print((trim ? s.trim() : s) + trail);
        return CommandResult.VOID;
    }
}