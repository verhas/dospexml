package javax0.useng.support;

@FunctionalInterface
public interface TriConsumer {
    void apply(String expected, String actual, String message);
}
