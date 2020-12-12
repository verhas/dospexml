package javax0.dospexml.engine;

import javax0.dospexml.api.ExecutionException;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StaticQueryMap<K, V> {
    private final Map<Node, Map<K, V>> map = new HashMap<>();

    public Query query(Node node) {
        return new Query(node);
    }

    public class Query implements javax0.dospexml.api.Query<K, V> {
        private final Node node;
        private final Map<K, V> local;

        public Query(Node node) {
            {
                this.node = node;
            }
            {
                Map<K, V> l;
                if ((l = map.get(node)) == null) {
                    l = new HashMap<>();
                    map.put(node, l);
                }
                local = l;
            }
        }

        @Override
        public boolean containsKey(K key) {
            for (var n = node; n != null; n = n.getParentNode()) {
                if (map.getOrDefault(n, Map.of()).containsKey(key)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public V get(K key) {
            for (var n = node; n != null; n = n.getParentNode()) {
                if (map.getOrDefault(n, Map.of()).containsKey(key)) {
                    return map.get(n).get(key);
                }
            }
            return null;
        }


        @Override
        public V export(K key, V value) {
            final var parent = node.getParentNode();
            if (parent == null) {
                throw new ExecutionException("Cannot export from the root level");
            }
            return query(parent).put(key, value);
        }

        @Override
        public V global(K key, V value) {
            var root = node;
            for (; root.getParentNode() != null; root = root.getParentNode())
                ;
            return query(root).put(key, value);
        }

        @Override
        public V removeGlobal(K key) {
            var root = node;
            for (; root.getParentNode() != null; root = root.getParentNode())
                ;
            return query(root).remove(key);
        }

        @Override
        public V put(K key, V value) {
            return local.put(key, value);
        }


        @Override
        public V remove(K key) {
            return local.remove(key);
        }


        @Override
        public Set<K> keySet() {
            Set<K> keys = new HashSet<>();
            for (var n = node; n != null; n = n.getParentNode()) {
                keys.addAll(map.getOrDefault(node, Map.of()).keySet());
            }
            return keys;
        }
    }

}
