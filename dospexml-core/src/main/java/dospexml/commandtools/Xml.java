package dospexml.commandtools;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Iterator;
import java.util.Optional;

public class Xml {
    private Xml() {
    }

    /**
     * Get the child that has the namespace and tag specified id. TEXT_NODEs are ignored.
     *
     * @param it  the current node
     * @param ns  the name space. Ignored if it is null
     * @param tag the tag of the child we need
     * @return the node we found
     */
    public static Optional<Node> child(Node it, String ns, String tag) {
        for (final var node : children(it)) {
            if (node.getNodeType() != Node.TEXT_NODE &&
                (ns == null || ns.equals(node.getNamespaceURI())) &&
                tag.equals(node.getLocalName())
            ) {
                return Optional.of(node);
            }
        }
        return Optional.empty();
    }

    /**
     * Get the 'index'-th (start with zero) child node. TEXT_NODEs are ignored-
     *
     * @param it    the current node
     * @param index the index of the node we want to retrieve
     * @return the node we found
     */
    public static Optional<Node> child(Node it, int index) {
        int counter = 0;
        if (index >= it.getChildNodes().getLength()) {
            return Optional.empty();
        }
        for (final var node : children(it)) {
            if (Xml.isNonTextNode(node) && counter++ >= index) {
                return Optional.of(node);
            }
        }
        return Optional.empty();
    }

    public static boolean isNonTextNode(Node node) {
        return node.getNodeType() != Node.TEXT_NODE &&
            node.getNodeType() != Node.PROCESSING_INSTRUCTION_NODE &&
            node.getNodeType() != Node.COMMENT_NODE &&
            node.getNodeType() != Node.CDATA_SECTION_NODE &&
            node.getNodeType() != Node.ATTRIBUTE_NODE;
    }

    public static Iterable<Node> children(final Node it) {
        return new Iterable<Node>() {
            @Override
            public Iterator<Node> iterator() {
                return new Iterator<Node>() {
                    int i = 0;
                    final NodeList nodes = it.getChildNodes();

                    @Override
                    public boolean hasNext() {
                        return i < nodes.getLength();
                    }

                    @Override
                    public Node next() {
                        return nodes.item(i++);
                    }
                };
            }
        };
    }
}
