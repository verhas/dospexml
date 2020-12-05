package javax0.useng.engine;

import javax0.useng.api.Query;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DynamicQueryMap<K, V> implements AutoCloseable, Query<K, V> {

    private MapHolder<K, V> head = null;
    private final Map<K, V> globalMap = new HashMap<>();

    @Override
    public boolean containsKey(K key) {
        for (var n = head; n != null; n = n.parent) {
            if (n.map.containsKey(key)) {
                return true;
            }
        }
        return globalMap.containsKey(key);
    }

    @Override
    public V get(K key) {
        for (var n = head; n != null; n = n.parent) {
            final V value;
            if ((value = n.map.get(key)) != null) {
                return value;
            }
        }
        return globalMap.get(key);
    }

    @Override
    public V export(K key, V value) {
        if (head.parent == null) {
            return globalMap.put(key, value);
        } else {
            return head.parent.map.put(key, value);
        }
    }

    @Override
    public V global(K key, V value) {
        return globalMap.put(key, value);
    }

    @Override
    public V removeGlobal(K key) {
        return globalMap.remove(key);
    }

    @Override
    public V put(K key, V value) {
        return head.map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return head.map.remove(key);
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (var n = head; n != null; n = n.parent) {
            keys.addAll(n.map.keySet());
        }
        keys.addAll(globalMap.keySet());
        return keys;
    }

    DynamicQueryMap open() {
        head = new MapHolder<>(head);
        return this;
    }

    public void close() {
        /* Closing on the top level is not an issue, because it may happen that a dynamic map is created in a lower level,
        used only in the lover level, but the traversal will find it later in higher level as well.
         */
        if (head != null) {
            head = head.parent;
        }
    }

    private static class MapHolder<K, V> {
        private final Map<K, V> map = new HashMap<>();
        private final MapHolder<K, V> parent;

        private MapHolder(MapHolder parent) {
            this.parent = parent;
        }
    }
}
