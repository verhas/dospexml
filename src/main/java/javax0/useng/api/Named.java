package javax0.useng.api;

public interface Named {
    default String name() {
        return this.getClass().getSimpleName();
    }
}
