package javax0.useng.api;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * Query is a hierarchical Map like structure. It is not a map, as there are methods that are not implemented and the
 * same time there are some methods, which are specific to the data structure you cannot find in a map.
 * <p>
 * You can imagine a Query as a stack of Map. When you look for a (key,value) entry then you consult the map that is on
 * the top of the stack, then the next and so on until you find the entry or you run out of maps.
 * <p>
 * When you put an entry into the Query then it is stored in the map, which is on the top of the stack.
 * <p>
 * When you 'export' an entry into the Query then it is stored in the map, which is one level below the top of the
 * stack.
 * <p>
 * The actual implementations eventually will implement other functionalities that control the creation and elimination
 * of the maps as the stack grows and decreases. This interface defines only the functionality that is concerned with
 * the querying of the store.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
public interface Query<K, V> {
    /**
     * @param key the key we are looking for
     * @return true of the query contains an entry for the key
     */
    boolean containsKey(K key);

    /**
     * Get the value for a given key.
     *
     * @param key the key we are looking for
     * @return the value associated with the key
     */
    V get(K key);

    /**
     * Export the value associated with the key.
     * <p>
     * When you export a value then it will be one leve higher that the current level in the stack. It is usually used
     * to execute commands like {@code Variable} that declares a variable. If it used {@link #put(Object, Object) put()}
     * then the variable would be available only inside the variable declaration. What we want is make it available in
     * the surrounding environment that contains the {@code <Variable ...>} command.
     * <p>
     *
     * @param key   the value will be associated with this key
     * @param value the value to be stored
     * @return the value that the underlying map {@code put} returns. It is usually the old value that was stored.
     */
    V export(K key, V value);

    /**
     * Put a value into the map that is on the top of the map.
     *
     * @param key   the value will be associated with this key
     * @param value the value to be stored
     * @return the value that the underlying map {@code put} returns. It is usually the old value that was stored.
     */
    V put(K key, V value);

    /**
     * Put a value into the map that is on the bottom of the map.
     *
     * @param key   the value will be associated with this key
     * @param value the value to be stored
     * @return the value that the underlying map {@code put} returns. It is usually the old value that was stored.
     */
    V global(K key, V value);

    /**
     * Remove an entry associated with the key from the map that is on the top of the stack.
     *
     * @param key the value will be associated with this key
     * @return the value that the underlying map {@code remove} returns. It is usually the old value that was stored.
     */
    V remove(K key);

    /**
     * Remove an entry associated with the key from the map that is on the bottom of the stack.
     *
     * @param key the value will be associated with this key
     * @return the value that the underlying map {@code remove} returns. It is usually the old value that was stored.
     */
    V removeGlobal(K key);

    /**
     * Returns the current key set of the query. Note that this key set is not available and maintained by the query.
     * Asking for the key set is a computation every time this method is invoked.
     *
     * @return the current set of keys.
     */
    Set<K> keySet();

    /**
     * Same as {@link java.util.Map#computeIfAbsent(Object, Function)} operating on the query.
     *
     * @param key             key with which the specified value is to be associated
     * @param mappingFunction the mapping function to compute a value
     * @return the current (existing or computed) value associated with the specified key, or null if the computed value
     * is null
     */
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

    /**
     * Same as {@link java.util.Map#computeIfAbsent(Object, Function)} operating on the query but it stores a new value
     * one level below the top of the stack.
     *
     * @param key             key with which the specified value is to be associated
     * @param mappingFunction the mapping function to compute a value
     * @return the current (existing or computed) value associated with the specified key, or null if the computed value
     * is null
     */
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
