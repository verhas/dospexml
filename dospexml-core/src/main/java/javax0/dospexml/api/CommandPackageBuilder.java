package javax0.dospexml.api;

public interface CommandPackageBuilder {
    CommandPackageBuilder with(Processor p);

    CommandPackageBuilder nameSpace(String ns);

    default <T> CommandPackageBuilder using(String key, T value) {
        throw new IllegalArgumentException(this.getClass().getPackageName() + " does not contain configurable commands");
    }

    void register();
}
