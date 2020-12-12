package javax0.dospexml.commands.basic;

import javax0.dospexml.api.AllNodesProcessing;
import javax0.dospexml.api.Command;
import javax0.dospexml.api.CommandContext;
import javax0.dospexml.api.CommandResult;

import java.util.List;

public class Throw implements Command<Void>, AllNodesProcessing {
    @Override
    public CommandResult<Void> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        final var klass = ctx.parameter("class").map(Throw::forName).orElse(null);
        final var message = ctx.parameter("message").orElse(null);
        if (klass != null) {
            if (Throwable.class.isAssignableFrom(klass)) {
                sthrow(newInstance(ctx, klass, message));
            } else {
                sthrow(ctx.exception("Throw wanted to throw " + klass.getName() + " but it is not Throwable"));
            }
        } else {
            if (results.size() > 0) {
                throw ctx.exception("" + results.get(0).get());
            } else {
                throw ctx.exception("");
            }
        }
        throw sthrow(null);
    }

    private static <E extends Throwable> RuntimeException sthrow(Throwable t) throws E {
        throw (E) t;
    }

    private Throwable newInstance(CommandContext ctx, Class klass, String message) {
        try {
            return (Throwable) klass.getDeclaredConstructor(Object.class).newInstance(message);
        } catch (Exception e) {
            try {
                return (Throwable) klass.getDeclaredConstructor().newInstance();
            } catch (Exception e1) {
                return ctx.exception("Throw wanted to throw " + klass.getName() + " as exception but it caused " + e1);
            }
        }
    }

    private static Class forName(final String klassName) {
        try {
            return Class.forName(klassName);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
