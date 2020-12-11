package javax0.dospexml.support;

import javax0.dospexml.api.CommandResult;
import javax0.dospexml.api.ExecutionException;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.math.BigDecimal;

public class Convert {
    private Convert() {
    }

    public static double toDouble(CommandResult<?> result) {
        var object = result.get();
        if (object == null) {
            throw new ExecutionException("null cannot be converted to double");
        }
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }
        return Double.parseDouble(object.toString());
    }

    public static long toLong(CommandResult<?> result) {
        var object = result.get();
        if (object == null) {
            throw new ExecutionException("null cannot be converted to long");
        }
        if (object instanceof Number) {
            return ((Number) object).longValue();
        }
        return Long.parseLong(object.toString());
    }

    public static int toInt(CommandResult<?> result) {
        var object = result.get();
        if (object == null) {
            throw new ExecutionException("null cannot be converted to int");
        }
        if (object instanceof Number) {
            return ((Number) object).intValue();
        }
        return Integer.parseInt(object.toString());
    }

    public static BigDecimal toBigDecimal(CommandResult<?> result) {
        var object = result.get();
        if (object == null) {
            throw new ExecutionException("null cannot be converted to BigDecimal");
        }
        if (object instanceof BigDecimal) {
            return (BigDecimal) object;
        }
        return new BigDecimal(object.toString());
    }

    public static String toString(CommandResult<?> result) {
        return ""+result.get();
    }

    public static String unescape(String literal) throws IOException {
        StreamTokenizer parser = new StreamTokenizer(new StringReader("\"" + literal + "\""));
        parser.nextToken();
        return parser.sval;
    }
}
