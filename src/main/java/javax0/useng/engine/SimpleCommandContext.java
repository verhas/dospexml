package javax0.useng.engine;

import javax0.useng.api.CommandContext;
import javax0.useng.api.CommandResult;
import javax0.useng.api.Processor;
import javax0.useng.api.Query;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleCommandContext implements CommandContext {
    final javax0.useng.engine.Processor processor;
    private final List<Node> nodeList;
    private final Node node;
    private final String nsuri;
    private final String localName;
    private final NamedNodeMap attributes;
    private final GlobalContext globalContext;

    public SimpleCommandContext(javax0.useng.engine.Processor processor,
                                Node node,
                                GlobalContext globalContext,
                                boolean needsTextSegment) {
        this.processor = processor;
        this.nodeList = new ArrayList<>();
        final var childNodes = node.getChildNodes();
        final var numberOfNodes = childNodes.getLength();
        for (int i = 0; i < numberOfNodes; i++) {
            if (childNodes.item(i).getNodeType() != Node.TEXT_NODE || needsTextSegment) {
                this.nodeList.add(childNodes.item(i));
            }
        }
        this.nsuri = node.getNamespaceURI();
        this.localName = node.getLocalName();
        this.attributes = node.getAttributes();
        this.node = node;
        this.globalContext = globalContext;
    }

    @Override
    public GlobalContext globalContext() {
        return globalContext;
    }

    @Override
    public Processor processor() {
        return processor;
    }

    @Override
    public List<Node> nodeList() {
        return nodeList;
    }

    @Override
    public Node node() {
        return node;
    }

    @Override
    public Optional<String> nameSpace() {
        return Optional.ofNullable(nsuri);
    }

    @Override
    public String commandName() {
        return localName;
    }

    @Override
    public Optional<String> attribute(String ns, String attr) {
        final var attrNode = attributes.getNamedItemNS(ns, attr);
        return Optional.ofNullable(attrNode).map(n -> n.getTextContent());
    }

    @Override
    public Optional<String> textNode(String ns, String nodeName) {
        for (final var node : nodeList) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (ns.equals(node.getNamespaceURI()) && nodeName.equals(node.getLocalName())) {
                    return Optional.ofNullable(node.getTextContent());
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> parameter(String name) {
        return attribute(nsuri, name).or(() -> attribute(null, name)).or(() -> textNode(nsuri, name)).map(String::trim);
    }

    @Override
    public CommandResult<?> process(Node node) {
        return processor.process(node);
    }

    @Override
    public <K, V> Query<K, V> staticQuery(Object key) {
        return processor.staticQuery(key, this.node);
    }

    @Override
    public <K, V> Query<K, V> dynamicQuery(Object key) {
        return processor.dynamicQuery(key);
    }

}
