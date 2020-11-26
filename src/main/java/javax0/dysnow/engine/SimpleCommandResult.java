package javax0.dysnow.engine;

import javax0.dysnow.api.CommandResult;

public class SimpleCommandResult<T> implements CommandResult<T> {
    private final T t;
    private final Class<?> k;

    public SimpleCommandResult(T t) {
        this.t = t;
        this.k = t.getClass();
    }

    public SimpleCommandResult(T t, Class<?> k) {
        this.t = t;
        this.k = k;
    }

    @Override
    public T get() {
        return t;
    }

    @Override
    public Class<?> type() {
        return k;
    }

    @Override
    public boolean isVoid() {
        return false;
    }
}
