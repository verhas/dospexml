package javax0.dospexml.support;

@FunctionalInterface
public interface TriConsumer {
    void apply(String expected, String actual, String message);
}
