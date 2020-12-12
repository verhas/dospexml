package javax0.dospexml.commands.basic;

import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;
import javax0.dospexml.commandtools.Xml;

public class Let implements Command<Void> {

    public CommandResult<Void> execute(CommandContext ctx) {
        try (final var __ = ctx.processor().open()) {
            final var name = ctx.parameter("name").orElse(null);
            final int valueNodeIndex;
            final Variable.VariableHolder holder;
            if (name == null) {
                valueNodeIndex = 1;
                final var variableNode = Xml.child(ctx.node(), 0);
                final var holderObj = ctx.process(variableNode.get());
                if (holderObj.get() instanceof Variable.VariableHolder) {
                    holder = (Variable.VariableHolder) holderObj.get();
                } else {
                    throw ctx.exception("Let should either have a name attribute or a Variable as first child.");
                }
            } else {
                valueNodeIndex = 0;
                final var localityString = ctx.parameter("locality").orElse("default");
                final var query = "dynamic".equals(localityString) ?
                    ctx.<String, Variable.VariableHolder>dynamicQuery("variables") :
                    ctx.<String, Variable.VariableHolder>staticQuery("variables");
                final var declare = ctx.parameter("declare").map(Boolean::parseBoolean).orElse(false);
                if (declare) {
                    holder = new Variable.VariableHolder();
                    query.export(name, holder);
                } else {
                    holder = query.exportIfAbsent(name, (s) -> new Variable.VariableHolder());
                }
            }

            CommandResult<?> result;
            try (final var ___ = ctx.processor().open()) {
                final var valueNode = Xml.child(ctx.node(), valueNodeIndex);
                if (valueNode.isEmpty()) {
                    throw ctx.exception("Set needs a value.");
                }
                result = ctx.process(valueNode.get());
            }

            holder.value = result.get();
            return CommandResult.VOID;
        }
    }
}
