package javax0.useng.engine;

import javax0.useng.api.ExecutionException;
import javax0.useng.api.Query;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DynamicQueryMap<K, V> implements AutoCloseable, Query<K, V> {
public static DynamicQueryMap debugThis = null;
    private MapHolder<K, V> head = null;
    private final Map<K, V> globalMap = new HashMap<>();

    /**
     * Create a new {@link DynamicQueryMap} on the current level. The {@link DynamicQueryMap}s have the same level even
     * if they are created when the code is already some level deep. In that case the structure wil be created when the
     * map is first needed. This is eventually may be much later than when the level was opened.
     *
     * @param level
     */
    DynamicQueryMap(int level) {
        while (level > 0) {
            open();
            level--;
        }
        debugThis = this;
    }

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
        if (head == null) {
            throw new ExecutionException("There are too many "+DynamicQueryMap.class.getName()+" closes");
        } else {
            head = head.parent;
        }
    }

    private static class MapHolder<K, V> {
        private final Map<K, V> map = new HashMap<>();
        private final MapHolder<K, V> parent;

        private MapHolder(MapHolder parent) {
            this.parent = parent;
        }

        private int level() {
            if (parent == null) {
                return 1;
            }
            return parent.level() + 1;
        }

        @Override
        public String toString() {
            final var sb = new StringBuilder();
            if (parent != null) {
                sb.append(parent.toString());
            }
            sb.append(level()).append(": {");
            for (final var entry : map.entrySet()) {
                sb.append("\"");
                sb.append(entry.getKey());
                sb.append("\"");
                sb.append(" = \"");
                sb.append(entry.getValue());
                sb.append("\"");
            }
            sb.append("}");
            return sb.toString();
        }
    }
}
