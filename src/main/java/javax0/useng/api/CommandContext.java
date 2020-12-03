package javax0.useng.api;

import org.w3c.dom.Node;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.Function;

public interface CommandContext {
    GlobalContext<?> NO_GLOBAL_CONTEXT = null;

    <T> GlobalContext<T> globalContext();

    Processor processor();

    List<Node> nodeList();

    Node node();

    Optional<String> nameSpace();

    String commandName();

    Optional<String> attribute(String ns, String attr);

    Optional<String> textNode(String ns, String attr);

    Optional<String> parameter(String attr);

    private <T> Optional<T> tParameter(String attr, Function<String, T> converter) {
        final var p = parameter(attr);
        if (p.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(converter.apply(p.get()));
    }

    default OptionalLong longParameter(String attr) {
        final var p = tParameter(attr, Long::parseLong);
        if (p.isEmpty()) {
            return OptionalLong.empty();
        }
        return OptionalLong.of(p.get());
    }

    default OptionalInt intParameter(String attr) {
        final var p = tParameter(attr, Integer::parseInt);
        if (p.isEmpty()) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(p.get());
    }

    default OptionalDouble doubleParameter(String attr) {
        final var p = tParameter(attr, Double::parseDouble);
        if (p.isEmpty()) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(p.get());
    }

    default Optional<Boolean> booleanParameter(String attr) {
        return tParameter(attr, Boolean::parseBoolean);
    }

    <T> CommandResult<T> process(Node node);

    <K, V> Query<K, V> staticQuery(Object key);

    <K, V> Query<K, V> dynamicQuery(Object key);

    interface GlobalContext<T> {
        T get();
    }
}
