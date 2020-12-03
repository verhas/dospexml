package javax0.useng.api;

public interface CommandResult<T> {
    class SimpleCommandResult<T> implements CommandResult<T> {
        private final T t;
        private final Class<?> k;

        public SimpleCommandResult(T t) {
            this.t = t;
            this.k = t == null ? Object.class : t.getClass();
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

    class Void implements CommandResult<java.lang.Void> {
        public java.lang.Void get() {
            return null;
        }

        public Class<?> type() {
            return Void.class;
        }

        public boolean isVoid() {
            return true;
        }
    }

    CommandResult<java.lang.Void> VOID = new Void();
    CommandResult<Boolean> TRUE = simple(true);
    CommandResult<Boolean> FALSE = simple(false);
    CommandResult<Boolean> ZERO = simple(0);
    CommandResult<Boolean> ONE = simple(1);
    CommandResult<Boolean> NULL = simple(null);

    static <T> CommandResult simple(T t) {
        return new SimpleCommandResult(t);
    }

    static <T> CommandResult simple(T t, Class<T> k) {
        return new SimpleCommandResult(t, k);
    }

    /**
     * Get the result.
     *
     * @return
     */
    T get();

    /**
     * @return type of the result
     */
    Class<?> type();

    /**
     * If the command execution did not result any value
     *
     * @return
     */
    boolean isVoid();
}
