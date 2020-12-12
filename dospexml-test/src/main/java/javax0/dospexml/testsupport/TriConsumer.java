package javax0.dospexml.testsupport;

@FunctionalInterface
public interface TriConsumer {
    void apply(String expected, String actual, String message);
}
