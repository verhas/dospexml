package dospexml.commands.basic;

import dospexml.api.CommandContext;
import dospexml.api.CommandResult;
import dospexml.api.NamedCommand;
import javax0.dospexml.support.Convert;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Mult implements NamedCommand<Number> {

    @Override
    public CommandResult<Number> evaluate(CommandContext ctx, List<CommandResult<?>> results) {
        boolean hasDouble = false;
        boolean hasBigDecimal = false;
        boolean hasLong = false;
        for (final var result : results) {
            if (result.type() == BigDecimal.class) {
                hasBigDecimal = true;
                break;// this is the highest priority, we convert all to BigDecimal in this case
            } else if (result.type() == Double.class) {
                hasDouble = true;
            } else if (result.type() == Long.class) {
                hasLong = true;
            } else if (result.type() != Integer.class) {
                throw ctx.exception("I do not know how to multiply " + result.type());
            }
        }
        if (hasBigDecimal) {
            return multBigDecimals(results);
        }
        if (hasDouble) {
            return
                multDoubles(results);
        }
        if (hasLong) {
            return multLongs(results);
        }
        return addInts(results);
    }

    private static <T extends Number> CommandResult<T> addAll(List<CommandResult<?>> results,
                                                              T accumulator,
                                                              Function<CommandResult<?>, T> converter,
                                                              BiFunction<T, T, T> sum) {
        for (final var result : results) {
            accumulator = sum.apply(accumulator, converter.apply(result));
        }
        return CommandResult.simple(accumulator);
    }

    private static <T extends Number> CommandResult<T> multBigDecimals(List<CommandResult<?>> results) {
        return (CommandResult<T>) addAll(results, BigDecimal.ONE, Convert::toBigDecimal, (a, d) -> a.multiply(d));
    }


    private static <T extends Number> CommandResult<T> multDoubles(List<CommandResult<?>> results) {
        return (CommandResult<T>) addAll(results, 1.0, Convert::toDouble, (a, d) -> a * d);
    }

    private static <T extends Number> CommandResult<T> multLongs(List<CommandResult<?>> results) {
        return (CommandResult<T>) addAll(results, 1L, Convert::toLong, (a, d) -> a * d);
    }

    private static <T extends Number> CommandResult<T> addInts(List<CommandResult<?>> results) {
        return (CommandResult<T>) addAll(results, 1, Convert::toInt, (a, d) -> a * d);
    }

}