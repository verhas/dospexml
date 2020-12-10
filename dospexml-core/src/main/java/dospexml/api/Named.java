package dospexml.api;

public interface Named {
    default String name() {
        return this.getClass().getSimpleName();
    }
}
