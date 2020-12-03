package javax0.useng.api;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public interface Query<K, V> {
    boolean containsKey(K key);

    V get(K key);

    V export(K key, V value);

    V put(K key, V value);

    V global(K key, V value);

    V remove(K key);

    V removeGlobal(K key);

    Set<K> keySet();

    default V computeIfAbsent(K key,
                              Function<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        V v;
        if ((v = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                put(key, newValue);
                return newValue;
            }
        }

        return v;
    }

    default V exportIfAbsent(K key,
                             Function<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        V v;
        if ((v = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                export(key, newValue);
                return newValue;
            }
        }

        return v;
    }

}
