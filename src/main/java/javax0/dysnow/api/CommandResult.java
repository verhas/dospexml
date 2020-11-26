package javax0.dysnow.api;

public interface CommandResult<T> {
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

    static final CommandResult<java.lang.Void> VOID = new Void();

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
