package javax0.useng.support;

import javax0.useng.api.CommandResult;

public class Convert {
    private Convert() {
    }

    public static double toDouble(CommandResult<?> result) {
        var object = result.get();
        if (object instanceof Double) {
            return (double) object;
        }
        if (object instanceof Float) {
            return (double) object;
        }
        if (object instanceof Long) {
            return Double.valueOf((long) object);
        }
        return 0;
    }

}
