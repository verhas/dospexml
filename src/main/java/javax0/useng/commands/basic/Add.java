package javax0.useng.commands.basic;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.ExecutionException;
import javax0.useng.api.NamedCommand;

import java.math.BigDecimal;
import java.util.List;

public class Add implements NamedCommand<Object> {

    @Override
    public CommandResult<Object> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        boolean hasDouble = false;
        boolean hasLong = false;
        for (final var result : results) {
            if (result.type() == BigDecimal.class) {
                return addBigDecimals(results);
            }
            if (result.type() == Double.class) {
                hasDouble = true;
            } else if (result.type() == Long.class) {
                hasLong = true;
            } else if (result.type() != Integer.class) {
                throw new ExecutionException("I do not know how to add " + result.type());
            }
        }
        if (hasDouble) {
            return addDoubles(results);
        }
        if (hasLong) {
            return addLongs(results);
        }
        return addInts(results);
    }

    private CommandResult<Object> addBigDecimals(List<CommandResult<?>> results) {
        final BigDecimal accumulator = BigDecimal.ZERO;
        for (final var result : results) {
            final BigDecimal operand;
            if (result.type() == BigDecimal.class) {
                operand = (BigDecimal) result.get();
            } else if (result.type() == Double.class) {
                operand = BigDecimal.valueOf((Double) result.get());
            } else {
                operand = BigDecimal.valueOf((Long) result.get());
            }
            accumulator.add(operand);
        }
        return CommandResult.simple(accumulator);
    }


    private CommandResult<Object> addDoubles(List<CommandResult<?>> results) {
        Double accumulator = 0.0;
        for (final var result : results) {
            accumulator += (Double) result.get();
        }
        return CommandResult.simple(accumulator);
    }

    private CommandResult<Object> addLongs(List<CommandResult<?>> results) {
        long accumulator = 0L;
        for (final var result : results) {
            accumulator += ((Number) result.get()).longValue();
        }
        return CommandResult.simple(accumulator);
    }

    private CommandResult<Object> addInts(List<CommandResult<?>> results) {
        int accumulator = 0;
        for (final var result : results) {
            accumulator += (Integer) result.get();
        }
        return CommandResult.simple(accumulator);
    }

}