package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Compare {

    public static class Equals implements NamedCommand<Boolean> {
        public CommandResult<Boolean> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
            for (int i = 0; i < results.size() - 1; i++) {
                final var a = results.get(i).get();
                final var b = results.get(i + 1).get();
                if (a instanceof Number && b instanceof Number && !(a instanceof Double && b instanceof Double)) {
                    if (((Number) a).longValue() != ((Number) b).longValue()) {
                        return CommandResult.FALSE;
                    }
                }
                if (!a.equals(b)) {
                    return CommandResult.FALSE;
                }
            }
            return CommandResult.TRUE;
        }

        @Override
        public String name() {
            return "Equals";
        }
    }

    private static CommandResult<Boolean> compare(List<CommandResult<?>> results,
                                                  String commandName,
                                                  BiPredicate<Long, Long> numberPredicate,
                                                  Predicate<Integer> comparablePredicate) {
        for (int i = 0; i < results.size() - 1; i++) {
            final var a = results.get(i).get();
            final var b = results.get(i + 1).get();
            if (a instanceof Number && b instanceof Number && !(a instanceof Double && b instanceof Double)) {
                if (!numberPredicate.test(((Number) a).longValue(), ((Number) b).longValue())) {
                    return CommandResult.FALSE;
                }
            } else {
                if (!(a instanceof Comparable && b instanceof Comparable)) {
                    throw new ExecutionException(commandName + " can compare only on numeric and comparable objects");
                }
                final var ac = (Comparable) a;
                final var bc = (Comparable) b;
                if (!comparablePredicate.test(ac.compareTo(bc))) {
                    return CommandResult.FALSE;
                }
            }
        }
        return CommandResult.TRUE;
    }

    public static class Less implements NamedCommand<Boolean> {
        public CommandResult<Boolean> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
            return compare(results, name(), (a, b) -> a < b, (i) -> i < 0);
        }
    }

    public static class LessOrEqual implements NamedCommand<Boolean> {
        public CommandResult<Boolean> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
            return compare(results, name(), (a, b) -> a <= b, (i) -> i <= 0);
        }
    }

    public static class Greater implements NamedCommand<Boolean> {
        public CommandResult<Boolean> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
            return compare(results, name(), (a, b) -> a > b, (i) -> i > 0);
        }
    }

    public static class GreaterOrEqual implements NamedCommand<Boolean> {
        public CommandResult<Boolean> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
            return compare(results, name(), (a, b) -> a >= b, (i) -> i >= 0);
        }
    }
}
